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
    //drivetrain
  public static final int DRIVETRAIN_LEFT_FRONT_TALON = 1;
  public static final int DRIVETRAIN_LEFT_BACK_VICTOR = 2;
  public static final int DRIVETRAIN_RIGHT_FRONT_TALON = 16;
  public static final int DRIVETRAIN_RIGHT_BACK_VICTOR = 15;
    //stilts
  public static final int STILTS_RIGHT_FRONT_CAN = 3;
  public static final int STILTS_BACK_CAN = 13;
  public static final int STILTS_LEFT_FRONT_CAN = 14;
  public static final int CLIMBER_DRIVE_MOTOR = 11;
//lime light is on #8 pdp slot
  public static final int PNEUMATICS_MODULE = 21;

  public static final int DRIVETRAIN_ELEVATOR_CAN = 12;
  public static final int TEST_SPARKMAX = 30;

  //Manipulator
  public static final int CARRIAGE_VICTOR = 4;
  public static final int INTAKE_VICTOR = 5; // not used
  //limit switch values
  public static final int LIMIT_SWITCH_LEFT = 1;
  public static final int LIMIT_SWITCH_RIGHT = 2;
  
  public static final int OI_DRIVER_CONTROLLER = 0;

  // Xbox controller buttons
/*=====================================================================================================================*/
  public static final int DRIVER_CONTROLLER_MOVE_AXIS = 1; //left joystick up down
  public static final int DRIVER_CONTROLLER_ROTATE_AXIS = 0; //left joystick left right
  public static final int DRIVER_CONTROLLER_RIGHT_MOVE_AXIS = 5; //right joystick up-down
  public static final int DRIVER_CONTROLLER_RIGHT_ROTATE_AXIS = 4; //right joystick L-R
  public static final int DRIVER_CONTROLLER_STRAIGHT_FORWARD_AXIS = 3; // right trigger drive straight
  public static final int DRIVER_CONTROLLER_STRAIGHT_BACKWARD_AXIS = 2; // left trigger drive straight

  public static final int DRIVER_CONTROLLER_LIFT_ALL = 1; //raising all stilts via A button
  public static final int DRIVER_CONTROLLER_LIFT_FRONT = 3; //raising the front stilts via X button
  public static final int DRIVER_CONTROLLER_LIFT_BACK = 2; //raising the back stilts via B button
  public static final int DRIVER_CONTROLLER_LIFT_FORWARD = 6; //moving fowards on the stilts with right trigger
  public static final int DRIVER_CONTROLLER_LIFT_BACKWARD = 5; //moving backwards on the stilts with left trigger
  //  Dunno why we would want the elevator to rotate, but I'll put it in anyway.
  public static final int DRIVER_CONTROLLER_RIGHT_MOVE_ROTATE = 4;
/*=====================================================================================================================*/
  public static final int MANIPULATOR_CONTROLLER_GALAGA_OUT = 3; // Galaga grabs hatch with X
  public static final int MANIPULATOR_CONTROLLER_GALAGA_IN = 2; // Galaga drops hatch with B
  public static final int MANIPULATOR_CONTROLLER_SLIDE_OUT = 4; // Slide out with Y
  public static final int MANIPULATOR_CONTROLLER_SLIDE_IN = 1; // Slide in with A
  public static final int MANIPULATOR_CONTROLLER_CARGO_PULLIN = 3; //cargo pull in trigger Left
  public static final int MANIPULATOR_CONTROLLER_CARGO_PUSHOUT = 2; //cargo push out trigger Right
  public static final int MANIPULATOR_CONTROLLER_CARGO_INTAKE = 5; //cargo intake on left button
  public static final int MANIPULATOR_CONTROLLER_CARGO_DEPLOY = 2; //cargo deploy via left trigger
  public static final int MANIPULATOR_CONTROLLER_HATCH_INTAKE = 6; // grabbing hatches via right button
  public static final int MANIPULATOR_CONTROLLER_HATCH_DEPLOY = 3; //deploying hatches via right trigger
  public static final int MANIPULATOR_CONTROLLER_INTAKE_IN = 6; // Intake in with Left Bumper
  public static final int MANIPULATOR_CONTROLLER_INTAKE_OUT = 5; // Intake out with Right Bumper

  public static final int MANIPULATOR_CONTROLLER_ELEVATOR = 1; //left joystick up-down
  public static final int MANIPULATOR_CONTROLLER_DRIVE_MOVE = 5; // Manipulator can move robot! --up down
  public static final int MANIPULATOR_CONTROLLER_DRIVE_ROTATE = 4; //Manipulator can move robot! --side to side
  /*=====================================================================================================================*/
  public static final int LIME_LIGHT_OFF_BUTTON = 8; //A button in manipulator control
  public static final int HATCH_GRAB_AUTO = 5; // runs the auto that aligns the robot and 
  //grabs a hatch panel via the left bumber button
  public static final int HATCH_DEPLOY_AUTO = 6; // runs the auto that aligns the robot 
  //and deploys a hatch panel via the right bumper button


  //Climber things
  //public static final int CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL1 = 0;
  //public static final int CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL2 = 1;
  //public static final int CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL1 = 2;
  //public static final int CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL2 = 3;
  public static final int CLIMBER_SIDES_FRONT_CYLINDER_UP = 4;
  public static final int CLIMBER_SIDES_FRONT_CYLINDER_DOWN = 5;
  public static final int CLIMBER_BACK_CYLINDER_UP = 6;
  public static final int CLIMBER_BACK_CYLINDER_DOWN = 7;
  

 //Limelight modes
  public static final int LIME_LIGHT_LIGHT_PIPELINE = 0;
  public static final int LIME_LIGHT_LIGHT_OFF = 1;
  public static final int LIME_LIGHT_LIGHT_BLINK = 2;
  public static final int LIME_LIGHT_LIGHT_ON = 3;

  //photoSensor DIO ports
  public static final int FRONT_PHOTO_SENSOR = 9;
  public static final int CASTER_PHOTO_SENSOR = 8;

  //Pneumatics modes -- DO NOT CHANGE THIS OR MADDY WILL KILL YOU
  public static final int SOLENOIDS_SLIDE_FORWARD = 3;//same on bravo
  public static final int SOLENOIDS_SLIDE_BACKWARDS = 1;// same on bravo
  public static final int SOLENOIDS_GALAGA_FORWARD = 2;//4 on bravo, 2 on alpha
  public static final int SOLENOIDS_GALAGA_BACKWARDS = 0
  ;// 2 on bravo, 0 on alpha

  public static final int SOLENOIDS_INTAKE_FORWARDS = 9;
  public static final int SOLENOIDS_INTAKE_BACKWARDS = 11;
}

 



