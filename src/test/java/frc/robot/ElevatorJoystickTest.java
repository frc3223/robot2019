package frc.robot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.commands.ElevatorJoystick;
import frc.robot.subsystems.DataLogger;
import frc.robot.subsystems.Elevator;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({DataLogger.class, RobotBase.class})
public class ElevatorJoystickTest {/*

    public Elevator mockElevator = PowerMockito.mock(Elevator.class);
    public ElevatorJoystick elevatorJoystick;
    public Joystick mockJoystick = PowerMockito.mock(Joystick.class);
    public OI mockOI = PowerMockito.mock(OI.class);

    @Test
    public void testEpsilonCheck() throws Exception {
        this.elevatorJoystick = new ElevatorJoystick(this.mockElevator, this.mockOI);
        PowerMockito.whenNew(OI.class).withAnyArguments().thenReturn(this.mockOI);
        PowerMockito.whenNew(Elevator.class).withAnyArguments().thenReturn(this.mockElevator);
        this.mockOI.manipulatorController = this.mockJoystick;
        //PowerMockito.whenNew(ElevatorJoystick.class).withAnyArguments().thenReturn(this.elevatorJoystick);
        
        PowerMockito.when(this.mockOI.manipulatorController.getRawAxis(RobotMap.MANIPULATOR_CONTROLLER_ELEVATOR)).thenReturn(0.09);
        elevatorJoystick.execute();
        
        assertEquals(0, this.elevatorJoystick.rawAxisOutput, 0.0001);
        
    }

    @Test
    public void testEpsilonCheckNotZero() throws Exception {
        this.elevatorJoystick = new ElevatorJoystick(this.mockElevator, this.mockOI);
        PowerMockito.whenNew(OI.class).withAnyArguments().thenReturn(this.mockOI);
        PowerMockito.whenNew(Elevator.class).withAnyArguments().thenReturn(this.mockElevator);
        this.mockOI.manipulatorController = this.mockJoystick;
        //PowerMockito.whenNew(ElevatorJoystick.class).withAnyArguments().thenReturn(this.elevatorJoystick);
        
        PowerMockito.when(this.mockOI.manipulatorController.getRawAxis(RobotMap.MANIPULATOR_CONTROLLER_ELEVATOR)).thenReturn(0.25);
        elevatorJoystick.execute();
        
        assertEquals(0.25, this.elevatorJoystick.rawAxisOutput, 0.0001);
        
    }
*/}