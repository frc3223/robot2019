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

  public static final int DRIVETRAIN_LEFT_FRONT_TALON = 1;

  public static final int DRIVETRAIN_LEFT_BACK_VICTOR = 2;
  public static final int DRIVETRAIN_RIGHT_FRONT_TALON = 16;
  public static final int DRIVETRAIN_RIGHT_BACK_VICTOR = 15;
  public static final int DRIVETRAIN_ELEVATOR_TALON = 7;
  public static final int OI_DRIVER_CONTROLLER = 0;
  public static final int DRIVER_CONTROLLER_MOVE_AXIS = 1;
  public static final int DRIVER_CONTROLLER_ROTATE_AXIS = 0;
  public static final int CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL1 = 1;
  public static final int CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL2 = 1;
  public static final int CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL1 = 1;
  public static final int CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL2 = 1;
  public static final int CLIMBER_BACK_CYLINDER_CHANNEL1 = 1;
  public static final int CLIMBER_BACK_CYLINDER_CHANNEL2 = 1;
  public static final int CLIMBER_DRIVE_MOTOR = 8;
 
}

 



