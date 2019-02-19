/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * doubleing around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
    public static int solChannel = 1;
    public static int solChannel2 = 2;
    public static int joyIndex = 0;
    public static int compressorIndex = 3;
    
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  //Motor Controllers
  public static final int DRIVETRAIN_LEFT_FRONT_TALON = 1;
  public static final int DRIVETRAIN_LEFT_BACK_VICTOR = 2;
  public static final int DRIVETRAIN_RIGHT_FRONT_TALON = 16;
  public static final int DRIVETRAIN_RIGHT_BACK_VICTOR = 15;

  public static final int DRIVETRAIN_ELEVATOR_VICTOR = 13;
  public static final int TEST_SPARKMAX = 30;


  //Manipulator
  public static final int CARRIAGE_VICTOR = 12;
  public static final int INTAKE_VICTOR = 5;

  //limit switch values
  public static final int LIMIT_SWITCH_LEFT = 1;
  public static final int LIMIT_SWITCH_RIGHT = 2;
  
  public static final int OI_DRIVER_CONTROLLER = 0;

  // Xbox controller buttons
  public static final int DRIVER_CONTROLLER_MOVE_AXIS = 1; //left joystick up down
  public static final int DRIVER_CONTROLLER_ROTATE_AXIS = 0; //left joystick left right
  public static final int DRIVER_CONTROLLER_RIGHT_MOVE_AXIS = 5; //right joystick up-down
  public static final int DRIVER_CONTROLLER_RIGHT_ROTATE_AXIS = 4; //right joystick L-R
  public static final int MANIPULATOR_CONTROLLER_ELEVATOR = 1; //left joystick up-down
  public static final int MANIPULATOR_CONTROLLER_GALAGA_OUT = 3; // Galaga grabs hatch with X
  public static final int MANIPULATOR_CONTROLLER_GALAGA_IN = 2; // Galaga drops hatch with B
  public static final int MANIPULATOR_CONTROLLER_SLIDE_OUT = 4; // Slide out with Y
  public static final int MANIPULATOR_CONTROLLER_SLIDE_IN = 1; // Slide in with A
  public static final int MANIPULATOR_CONTROLLER_CARGO_PULLIN = 2; //cargo pull in trigger Left
  public static final int MANIPULATOR_CONTROLLER_CARGO_PUSHOUT = 3; //cargo push out trigger Right
  public static final int MANIPULATOR_CONTROLLER_CARGO_INTAKE = 5; //cargo intake on left button
  public static final int MANIPULATOR_CONTROLLER_CARGO_DEPLOY = 2; //cargo deploy via left trigger
  public static final int MANIPULATOR_CONTROLLER_HATCH_INTAKE = 6; // grabbing hatches via right button
  public static final int MANIPULATOR_CONTROLLER_HATCH_DEPLOY = 3; //deploying hatches via right trigger
  public static final int MANIPULATOR_CONTROLLER_INTAKE_IN = 6; // Intake in with Left Bumper
  public static final int MANIPULATOR_CONTROLLER_INTAKE_OUT = 5; // Intake out with Right Bumper
  public static final int DRIVER_CONTROLLER_LIFT_FRONT_UP = 3; //raising the front stilts via X button
  public static final int DRIVER_CONTROLLER_LIFT_BACK_UP = 2; ////raising the back stilts via B button
  public static final int DRIVER_CONTROLLER_LIFT_ALL_DOWN = 1; //lowering the stilts via A button
  public static final int DRIVER_CONTROLLER_LIFT_FORWARD = 6; //moving fowards on the stilts with right trigger
  public static final int DRIVER_CONTROLLER_LIFT_BACKWARD = 5; //moving backwards on the stilts with left trigger
  //  Dunno why we would want the elevator to rotate, but I'll put it in anyway.
  public static final int DRIVER_CONTROLLER_RIGHT_MOVE_ROTATE = 4;
  public static final int LIME_LIGHT_ON_BUTTON = 8; //A button in manipulator control

  //Climber things
  //public static final int CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL1 = 0;
  //public static final int CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL2 = 1;
  //public static final int CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL1 = 2;
  //public static final int CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL2 = 3;
  public static final int CLIMBER_SIDES_FRONT_CYLINDER_UP = 4;
  public static final int CLIMBER_SIDES_FRONT_CYLINDER_DOWN = 5;
  public static final int CLIMBER_BACK_CYLINDER_UP = 6;
  public static final int CLIMBER_BACK_CYLINDER_DOWN = 7;
  public static final int CLIMBER_DRIVE_MOTOR = 6;

 //Limelight modes
  public static final int LIME_LIGHT_LIGHT_PIPELINE = 0;
  public static final int LIME_LIGHT_LIGHT_OFF = 1;
  public static final int LIME_LIGHT_LIGHT_BLINK = 2;
  public static final int LIME_LIGHT_LIGHT_ON = 3;


  //Pneumatics modes
  public static final int PNEUMATICS_MODULE = 21;
  public static final int SOLENOIDS_SLIDE_FORWARD = 1;
  public static final int SOLENOIDS_SLIDE_BACKWARDS = 2;
  public static final int SOLENOIDS_GALAGA_FORWARD = 0;
  public static final int SOLENOIDS_GALAGA_BACKWARDS = 3;

  public static final int SOLENOIDS_INTAKE_FORWARDS = 9;
  public static final int SOLENOIDS_INTAKE_BACKWARDS = 11;
}

 



