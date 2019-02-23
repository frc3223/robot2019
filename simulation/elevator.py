import math 
import wpilib
import numpy
import hal
from hal_impl.sim_hooks import SimHooks as BaseSimHooks
import hal_impl.functions
from frc3223_azurite.conversions import (
    g, 
    lbs_to_kg, 
    lbs_to_N,
    inch_to_meter,
    radps_to_rpm,
)


class ElevatorSimulation:
    def __init__(self, periodic, motor_system, **kwargs):
        self.dt_s = kwargs.pop('dt_s', 0.0001)
        self.nominal_voltage = kwargs.pop('nominal_voltage', 12.)
        self.starting_position_m = kwargs.pop('starting_position_m', 0.)
        self.stage1_mass_kg = kwargs.pop('stage1_mass_kg', lbs_to_kg(5.0))
        self.stage2_mass_kg = kwargs.pop('stage2_mass_kg', lbs_to_kg(5.0))
        self.stage3_mass_kg = kwargs.pop('stage3_mass_kg', lbs_to_kg(20.0))
        self.robot_mass_kg = kwargs.pop('robot_mass_kg', lbs_to_kg(154))
        self.stage1_counterweighting_n = kwargs.pop('stage1_counterweighting_n', lbs_to_N(0.0))
        self.stage2_counterweighting_n = kwargs.pop('stage2_counterweighting_n', lbs_to_N(0.0))
        self.max_height_m = kwargs.pop('max_height_m', 2.0)
        # assuming kinetic and max static are same
        self.friction_force = kwargs.pop('friction_force_N', 0)
        self.sprocket_radius_m = kwargs.pop('sprocket_radius_m', inch_to_meter(2.0))
        self.hardstop_spring_constant=kwargs.pop('hardstop_spring_constant', lbs_to_N(200) / 0.01)
        self.gearbox_efficiency = kwargs.pop('gearbox_efficiency', 0.65)
        self.battery_resistance_ohms = kwargs.pop('battery_resistance_ohms', 0.015)
        self.pulls_down = kwargs.pop('pulls_down', True)
        self.cascading = kwargs.pop('cascading', True)
        self.battery_voltage = kwargs.pop('battery_voltage', 12.7)
        self.fuse_resistance_ohms = kwargs.pop('fuse_resistance_ohms', 0.002)
        self.pid_sample_rate= kwargs.pop('pid_sample_rate_s', 0.05)
        self.pid_periodic = kwargs.pop('pid_periodic', lambda state: 1)
        self.periodic_period = kwargs.pop('periodic_period', 0.02) # default on 20 ms intervals
        self.init = kwargs.pop('init', lambda *args, **kwargs: None)
        assert 0 <= self.gearbox_efficiency <= 1
        assert len(kwargs) == 0, 'Unknown parameters: ' + ', '.join(kwargs.keys())
        self.periodic = periodic
        self.motor_system = motor_system

        self.ts = None
        self.a_s = None
        self.vs = None
        self.xs = None
        self.voltages = None
        self.voltageps = None
        self.currents = None

    def stages_gravity(self):
        if self.cascading:
            return (
                self.stage1_mass_kg * g + 
                self.stage2_mass_kg * g * 2 + 
                self.stage3_mass_kg * g * 4
            )
        else:
            return (
                self.stage1_mass_kg * g + 
                self.stage2_mass_kg * g + 
                self.stage3_mass_kg * g 
            )

    def counterweighting(self):
        if self.cascading:
            return self.stage1_counterweighting_n + 2 * self.stage2_counterweighting_n
        else:
            return self.stage1_counterweighting_n + self.stage2_counterweighting_n

    def calc_lift_acceleration(self, state, current):
        torque = self.gearbox_efficiency * self.motor_system.torque_at_motor_current(current)
        motor_force = torque / self.sprocket_radius_m
        if not self.pulls_down and motor_force < 0:
            motor_force = 0
        gravity_force = self.stages_gravity()
        cw = self.counterweighting()
        
        force = motor_force - gravity_force + cw

        if state.x < 0:
            force += (0-state.x) * self.hardstop_spring_constant
        elif state.x > self.max_height_m:
            force -= (state.x - self.max_height_m) * self.hardstop_spring_constant
        else:
            # include friction here
            if state.velocity_mps > 0:
                force -= self.friction_force
            elif state.velocity_mps < 0:
                force += self.friction_force
            elif abs(force) < self.friction_force:
                force = 0
            elif force > 0:
                force -= self.friction_force
            elif force < 0:
                force += self.friction_force

        a = force / (gravity_force / g)
        return a

    def calc_climb_acceleration(self, state, current):
        torque = self.gearbox_efficiency * self.motor_system.torque_at_motor_current(current)
        motor_force = torque / self.sprocket_radius_m
        gv1 = self.stages_gravity()
        gv2 = self.robot_mass_kg * g
        cw = self.counterweighting()
        
        force = motor_force + gv1 - gv2 - cw

        if state.x < 0:
            force += (0-state.x) * self.hardstop_spring_constant
        elif state.x > self.max_height_m:
            force -= (state.x - self.max_height_m) * self.hardstop_spring_constant

        #print('force:', force, ' motor force: ', motor_force, 'current: ', current)
        #print ('force:' , force)
        a = force / self.robot_mass_kg
        return a

    def current_at_motor(self, state):
        vrot = self.v / self.sprocket_radius_m
        backemf = self.motor_system.motor_back_emf(vrot)
        voltage = state.voltage_p * self.battery_voltage - backemf
        motor_count = self.motor_system.motor_count
        r1 = self.battery_resistance_ohms
        r2 = self.fuse_resistance_ohms
        rm = self.motor_system.motor.resistance()
        r = r1 * motor_count + r2 + rm

        return voltage / r

    def voltage_at_motor(self, motor_current):
        r1 = self.battery_resistance_ohms
        r2 = self.fuse_resistance_ohms
        vb = self.battery_voltage
        motor_count = self.motor_system.motor_count

        return vb - r1 * motor_count * motor_current - r2 * motor_current

    def init_log_buffers(self, max_size):
        def make_buffer():
            return numpy.empty(shape=(max_size,),dtype='float')
        self.ts = make_buffer()
        self.a_s = make_buffer()
        self.vs = make_buffer()
        self.xs = make_buffer()
        self.voltages = make_buffer()
        self.voltageps = make_buffer()
        self.currents = make_buffer()

    def log(self, state):
        self.ts[self.i] = state.time_from_start_s
        self.a_s[self.i] = state.acceleration_mps2
        self.vs[self.i] = state.velocity_mps
        self.xs[self.i] = state.x
        self.voltageps[self.i] = state.voltage_p
        self.voltages[self.i] = state.voltage
        self.currents[self.i] = state.motor_current
        self.i += 1

    def run_lift_sim(self, timeout=10., sample_rate=None):
        if sample_rate is None:
            sample_rate = self.dt_s
        with RobotState() as state:
            dt = self.dt_s # time delta (s)
            t = 0. # time (s)
            t_last_periodic = 0. # time of last periodic call (s)
            t_last_measurement = 0. # time of last data log (s)
            t_last_pid = 0. # time of last pid calculation (s)
            self.v = 0. # velocity (m/s)
            x = self.starting_position_m # position (m)
            self.init_log_buffers(int(timeout / sample_rate) + 1)
            self.i = 0
            current = self.current_at_motor(state)
            voltage = self.voltage_at_motor(current)
            state._update(t, 0.0, self.v, x, current, voltage)
            self.init(state)
            a = self.calc_lift_acceleration(state, current) # acceleration (rad/s^2)
            state._update(t, a, self.v, x, current, voltage)
            self.log(state)

            while t < timeout and not state.stop:
                self.v += a * dt
                x += self.v * dt
                t += dt
                current = self.current_at_motor(state)
                voltage = self.voltage_at_motor(current)
                a = self.calc_lift_acceleration(state, current)
                state._update(t, a, self.v, x, current, voltage)
                if t - t_last_periodic > self.periodic_period:
                    t_last_periodic = t
                    self.periodic(state)
                if t - t_last_measurement > sample_rate:
                    self.log(state)
                    t_last_measurement = t
                if t - t_last_pid > self.pid_sample_rate:
                    self.pid_periodic(state)
                    t_last_pid = t

            self.trim_buffers()

    def run_climb_sim(self, timeout=10., sample_rate=None):
        if sample_rate is None:
            sample_rate = self.dt_s
        with RobotState() as state:
            dt = self.dt_s # time delta (s)
            t = 0. # time (s)
            t_last_periodic = 0. # time of last periodic call (s)
            t_last_measurement = 0. # time of last data log (s)
            t_last_pid = 0. # time of last pid calculation (s)
            self.v = 0. # velocity (m/s)
            x = self.starting_position_m # position (m)
            self.init_log_buffers(int(timeout / sample_rate) + 1)
            self.i = 0
            current = self.current_at_motor(state)
            voltage = self.voltage_at_motor(current)
            state._update(t, 0.0, self.v, x, current, voltage)
            self.init(state)
            a = self.calc_climb_acceleration(state, current) # acceleration (rad/s^2)
            state._update(t, a, self.v, x, current, voltage)
            self.log(state)

            while t < timeout and not state.stop:
                self.v += a * dt
                x += self.v * dt
                t += dt
                current = self.current_at_motor(state)
                voltage = self.voltage_at_motor(current)
                a = self.calc_climb_acceleration(state, current)
                state._update(t, a, self.v, x, current, voltage)
                if t - t_last_periodic > self.periodic_period:
                    t_last_periodic = t
                    self.periodic(state)
                if t - t_last_measurement > sample_rate:
                    self.log(state)
                    t_last_measurement = t
                if t - t_last_pid > self.pid_sample_rate:
                    self.pid_periodic(state)
                    t_last_pid = t

            self.trim_buffers()

    def trim_buffers(self):
        buffers = ['ts', 'a_s', 'vs', 'xs', 'voltages', 'voltageps', 'currents']
        for buf in buffers:
            arr = getattr(self,buf)
            setattr(self, buf, arr[:self.i])

    def write_csv(self, nom):
        import csv
        with open(nom, 'w') as f:
            writer = csv.writer(f)

            writer.writerow([
                'time', 'accel (m/s2)','velocity (m/s)', 
                'position (m)', 'current (A)', 'battery voltage (V)', 
                'motor percent voltage'])

            for i in range(len(self.ts)):
                writer.writerow([
                    self.ts[i], self.a_s[i], self.vs[i],
                    self.xs[i], self.currents[i], self.voltages[i],
                    self.voltageps[i]
                    ])


class RobotState:
    def __init__(self):
        self.pid = None
        self.motor_current = 0.0
        self._update(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        self._voltage_p = 0.0
        self.motor = None
        self.stop = False

    def __enter__(self):
        hal_impl.functions.hooks = SimHooks(self)
        hal_impl.functions.reset_hal()
        self.motor = SimVictor(self)
        return self

    def __exit__(self, type, value, traceback):
        self.motor.free()
        self.motor = None

    def _update(self, t, a, v, x, current, voltage):
        self.x = x
        self.acceleration_mps2 = a
        self.velocity_mps = v
        self.time_from_start_s = t
        self.motor_current = current
        self.voltage = voltage
        if self.pid:
            self.pid.setpointTimer.time_seconds = t

    @property
    def velocity_rpm(self):
        return radps_to_rpm(self.velocity_radps)

    def set_voltage_p(self, voltage):
        '''
        :type voltage: float
        '''
        self._voltage_p = max(-1.0, min(1.0, voltage))
    
    def get_voltage_p(self):
        return self._voltage_p

    voltage_p = property(get_voltage_p, set_voltage_p)


class SimVictor(wpilib.Victor):
    def __init__(self, state):
        '''
        :type state: RobotState
        '''
        super().__init__(1)
        self.state = state

    def set(self, voltage_p):
        '''
        :type voltage_p: float
        '''
        super().set(voltage_p)
        self.state.voltage_p = voltage_p

class SimHooks(BaseSimHooks):
    def __init__(self, state):
        '''
        :type state: RobotState
        '''
        super().__init__()
        self.state = state

    def getTime(self):
        return self.state.time_from_start_s

    def getFPGATime(self):
        from hal_impl.data import hal_data
        # post commit a518 this doesn't need to be implemented
        return int((self.getTime() - hal_data['time']['program_start']) * 1000000)



if __name__ == '__main__':
    from frc3223_azurite.motors import *

    def init(state):
        state.voltage_p = 0.0
        
    def periodic(state):
        state.voltage_p = 1.0

    sim = ElevatorSimulation(
        starting_position_m=inch_to_meter(0),
        max_height_m=inch_to_meter(72),
        sprocket_radius_m=inch_to_meter(2.12),
        robot_mass_kg=lbs_to_kg(154),
        stage1_mass_kg=lbs_to_kg(16.0),
        stage2_mass_kg=lbs_to_kg(0.0),
        stage3_mass_kg=lbs_to_kg(0.0),
        stage1_counterweighting_n=lbs_to_N(.0),
        stage2_counterweighting_n=lbs_to_N(0.0),
        pulls_down = False,
        init=init,
        periodic=periodic,
        pid_sample_rate_s=0.001,
        gearbox_efficiency=0.85,
        motor_system=MotorSystem(motor=bag, motor_count=4, gearing_ratio=50),
        dt_s=0.0001,
    )

    sim.run_lift_sim(timeout=4)

    sim.write_csv("test.csv")
