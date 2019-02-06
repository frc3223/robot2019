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
  public static final int DRIVETRAIN_ELEVATOR_TALON = 7;
  
  public static final int OI_DRIVER_CONTROLLER = 0;

  // Xbox controller buttons
  public static final int DRIVER_CONTROLLER_MOVE_AXIS = 1; //left joystick up down
  public static final int DRIVER_CONTROLLER_ROTATE_AXIS = 0; //left joystick left right
  public static final int DRIVER_CONTROLLER_RIGHT_MOVE_AXIS = 5; //right joystick up-down
  public static final int MANIPULATOR_CONTROLLER_ELEVATOR_UP = 5; //elevator up on right joystick
  public static final int MANIPULATOR_CONTROLLER_ELEVATOR_DOWN = 4; //elevator down on right joystick
  public static final int MANIPULATOR_CONTROLLER_CARGO_INTAKE = 5; //cargo intake on left button
  public static final int MANIPULATOR_CONTROLLER_CARGO_DEPLOY = 2; //cargo deploy via left trigger
  public static final int MANIPULATOR_CONTROLLER_HATCH_INTAKE = 6; // grabbing hatches via right button
  public static final int MANIPULATOR_CONTROLLER_HATCH_DEPLOY = 3; //deploying hatches via right trigger
  public static final int DRIVER_CONTROLLER_LIFT_FRONT_UP = 3; //raising the front stilts via X button
  public static final int DRIVER_CONTROLLER_LIFT_BACK_UP = 2; ////raising the back stilts via B button
  public static final int DRIVER_CONTROLLER_LIFT_ALL_DOWN = 1; //lowering the stilts via A button
  public static final int DRIVER_CONTROLLER_LIFT_FORWARD = 3; //moving fowards on the stilts with right trigger
  public static final int DRIVER_CONTROLLER_LIFT_BACKWARD = 2; //moving backwards on the stilts with left trigger
  /*  Dunno why we would want the elevator to rotate, but I'll put it in anyway.             */
  public static final int DRIVER_CONTROLLER_RIGHT_MOVE_ROTATE = 4;
  public static final int LIME_LIGHT_ON_BUTTON = 1; //A button in manipulator control

  //Climber things
  public static final int CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL1 = 1;
  public static final int CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL2 = 1;
  public static final int CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL1 = 1;
  public static final int CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL2 = 1;
  public static final int CLIMBER_BACK_CYLINDER_CHANNEL1 = 1;
  public static final int CLIMBER_BACK_CYLINDER_CHANNEL2 = 1;
  public static final int CLIMBER_DRIVE_MOTOR = 8;

 //Limelight modes
  public static final int LIME_LIGHT_LIGHT_PIPELINE = 0;
  public static final int LIME_LIGHT_LIGHT_OFF = 1;
  public static final int LIME_LIGHT_LIGHT_BLINK = 2;
  public static final int LIME_LIGHT_LIGHT_ON = 3;

}

 



