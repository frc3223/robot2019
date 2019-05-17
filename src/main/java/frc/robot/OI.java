/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    int driverPort;
    int manipulatorPort;

    public Joystick driverController;
    public Joystick manipulatorController;

    public JoystickButton galaga_in_button;
    public JoystickButton galaga_out_button;
    public JoystickButton slide_in_button;
    public JoystickButton slide_out_button;
    public JoystickButton intake_in_button;
    public JoystickButton intake_out_button;
    public JoystickButton lift_forward_button;
    public JoystickButton lift_backward_button;
    public JoystickButton auto_grab_sequence_button;
    public JoystickButton auto_deploy_sequence_button;
    public JoystickButton turn_limelight_off;
    public DigitalInput frontSensor;
    public DigitalInput casterSensor;
    public DigitalInput cargoSensor;
    public DigitalInput hatchSensor;
    public DigitalInput leftStiltLimit;
    public DigitalInput rightStiltLimit;
    public JoystickButton auto_climb;

    public OI() {
        getDriverPort();
        driverController = new Joystick(driverPort);
        manipulatorController = new Joystick(manipulatorPort);
        galaga_in_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_GALAGA_IN);
        galaga_out_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_GALAGA_OUT);
        slide_in_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_SLIDE_IN);
        slide_out_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_SLIDE_OUT);
        intake_in_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_INTAKE_IN);
        intake_out_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_INTAKE_OUT);
        lift_forward_button = new JoystickButton(driverController, RobotMap.DRIVER_CONTROLLER_LIFT_FORWARD);
        lift_backward_button = new JoystickButton(driverController, RobotMap.DRIVER_CONTROLLER_LIFT_BACKWARD);
        auto_grab_sequence_button = new JoystickButton(manipulatorController,RobotMap.HATCH_GRAB_AUTO);
        auto_deploy_sequence_button = new JoystickButton(manipulatorController,RobotMap.HATCH_DEPLOY_AUTO);
        turn_limelight_off = new JoystickButton(manipulatorController,RobotMap.LIME_LIGHT_OFF_BUTTON);
        auto_climb = new JoystickButton(driverController, RobotMap.DRIVER_CONTROLLER_AUTO_CLIMB);
        frontSensor = new DigitalInput(RobotMap.FRONT_PHOTO_SENSOR);
        casterSensor = new DigitalInput(RobotMap.CASTER_PHOTO_SENSOR);
        cargoSensor = new DigitalInput(RobotMap.CARGO_SENSOR);
        hatchSensor = new DigitalInput(RobotMap.HATCH_SENSOR);
        leftStiltLimit = new DigitalInput(RobotMap.LEFT_STILT);
        rightStiltLimit = new DigitalInput(RobotMap.RIGHT_STILT);

    }

    public void getDriverPort() {
        Joystick controller = new Joystick(0);
        String controllerName = controller.getName();

        driverPort = 0;
        manipulatorPort = 1;
    }


    public boolean shouldRaiseStilts() {
        return this.driverController.getPOV() == 0; // up
    }

    public boolean shouldLowerStilts() {
        return this.driverController.getPOV() == 180; // down
    }

    public boolean shouldControlAllStilts() {
        return this.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_ALL);
    }

    public boolean shouldControlFrontStilts() {
        return this.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_FRONT);
    }
    public boolean shouldControlBackStilts() {
        return this.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_BACK);
    }
    public boolean shouldDriveStiltsForward() {
        return this.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_FORWARD);
    }
    public boolean shouldDriveStiltsBackward() {
        return this.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_BACKWARD);
    }

    public double getElevatorRawAxisOutput() {
        return this.manipulatorController.getRawAxis(RobotMap.MANIPULATOR_CONTROLLER_ELEVATOR);
    }
    public double getManipulatorDriveMove() {
        return this.manipulatorController.getRawAxis(RobotMap.MANIPULATOR_CONTROLLER_DRIVE_MOVE);
    }
    public double getManipulatorDriveRotate() {
        return this.manipulatorController.getRawAxis(RobotMap.MANIPULATOR_CONTROLLER_DRIVE_ROTATE);
    }

    //public double forwardSpeed() {
        //return this.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_STRAIGHT_FORWARD_AXIS);
    //}

    //public double backwardSpeed() {
        //return -this.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_STRAIGHT_BACKWARD_AXIS);
    //}

    public boolean isFrontTarget(){
        return frontSensor.get();
      }
    
      public boolean isCasterTarget(){
        return casterSensor.get();
      }

      public boolean isCargoSensorTarget(){
        return !cargoSensor.get();
      }

      public boolean isHatchSensor(){
          return !hatchSensor.get();
      }

      public boolean getLeftStiltSensor(){
          return leftStiltLimit.get();
      }

      public boolean getRightStiltSensor(){
          return rightStiltLimit.get();
      }
}
