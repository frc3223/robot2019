package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.wpi.first.wpilibj.Notifier;

import frc.robot.subsystems.Elevator;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Elevator.class})
public class ElevatorTest {

    public Elevator elevator;
    public Notifier mockNotifier = PowerMockito.mock(Notifier.class);
    public WPI_TalonSRX mockTalon = PowerMockito.mock(WPI_TalonSRX.class);

    @Test
    public void testSetDisabled() throws Exception {
        PowerMockito.whenNew(WPI_TalonSRX.class).withAnyArguments().thenReturn(this.mockTalon);
        PowerMockito.whenNew(Notifier.class).withAnyArguments().thenReturn(this.mockNotifier);
        PowerMockito.when(mockTalon.getSelectedSensorPosition()).thenReturn((int)(20480/(0.1016*Math.PI)));
        this.elevator = new Elevator();
        elevator.setTargetPosition(0);
        elevator.setDisabled(false);
        assertEquals(false, elevator.isDisabled());
        assertEquals(5, elevator.getTargetPosition(), 0.0001);
    }
    @Test
    public void testSetDisabledTrue() throws Exception {
        PowerMockito.whenNew(WPI_TalonSRX.class).withAnyArguments().thenReturn(this.mockTalon);
        PowerMockito.whenNew(Notifier.class).withAnyArguments().thenReturn(this.mockNotifier);
        PowerMockito.when(mockTalon.getSelectedSensorPosition()).thenReturn((int)(20480/(0.1016*Math.PI)));
        this.elevator = new Elevator();
        elevator.setTargetPosition(0);
        elevator.setDisabled(true);
        assertEquals(true, elevator.isDisabled());
        assertEquals(0, elevator.getTargetPosition(), 0.0001);
    }
    @Test
    public void testCalculate() throws Exception {
        PowerMockito.whenNew(WPI_TalonSRX.class).withAnyArguments().thenReturn(this.mockTalon);
        PowerMockito.whenNew(Notifier.class).withAnyArguments().thenReturn(this.mockNotifier);
        this.elevator = new Elevator();
        elevator.setDisabled(false);
        elevator.setTargetPosition(1);
        PowerMockito.when(this.mockTalon.getSelectedSensorPosition()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock incovation) {
                double var = elevator.stateSpaceController.y_est.get(0,0);
                double tick = metersToEncoderPosition(var);
                //System.out.println("tick is " + tick);
                return (int) tick;
            }
        });
        for(int i = 0; i < 200; i++) {
            assertEquals(1, elevator.getTargetPosition(), 0.01);
            elevator.calculate();
            // System.out.println("x_prev is " + elevator.stateSpaceController.x_prev.get(0, 0));
            // System.out.println("u is " + elevator.stateSpaceController.u.get(0, 0));
            // System.out.println("r is " + elevator.stateSpaceController.r.get(0, 0));
        }
        assertEquals(1, elevator.stateSpaceController.x_prev.get(0, 0), 0.001);
    }
    @Test
    public void testCalculateReverse() throws Exception {
        PowerMockito.whenNew(WPI_TalonSRX.class).withAnyArguments().thenReturn(this.mockTalon);
        PowerMockito.whenNew(Notifier.class).withAnyArguments().thenReturn(this.mockNotifier);
        this.elevator = new Elevator();
        elevator.setTargetPosition(1);
        elevator.setDisabled(false);
        PowerMockito.when(this.mockTalon.getSelectedSensorPosition()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock incovation) {
                double var = elevator.stateSpaceController.y_est.get(0,0);
                double tick = metersToEncoderPosition(var);
                return (int) tick;
            }
        });
        for(int i = 0; i < 200; i++) {
            assertEquals(0, elevator.getTargetPosition(), 0.01);
            elevator.calculate();
        }
        assertEquals(0, elevator.stateSpaceController.x_prev.get(0, 0), 0.001);
    }

    public double metersToEncoderPosition(double meters) {
        return (int)((meters * elevator.ticksPerRev) / ((2 * elevator.sprocketRadius) * Math.PI));
    }
}