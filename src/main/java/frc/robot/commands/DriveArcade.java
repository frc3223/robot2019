/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import java.lang.Math;

public class DriveArcade extends Command {
  public DriveArcade() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double moveSpeed = -Robot.m_oi.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_MOVE_AXIS);
    double rotateSpeed = Robot.m_oi.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_ROTATE_AXIS);
    double slowMoveSpeed = -Robot.m_oi.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_RIGHT_MOVE_AXIS);
    double slowRotateSpeed = Robot.m_oi.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_RIGHT_ROTATE_AXIS);
    if((Math.abs(moveSpeed) >= 0.1 || Math.abs(rotateSpeed) >= 0.1) && (Math.abs(slowMoveSpeed) >= 0.1 || Math.abs(slowRotateSpeed) >= 0.1)){
      Robot.m_drivetrain.arcadeDrive(slowMoveSpeed*0.75, slowRotateSpeed*0.75);
    }else if(Math.abs(moveSpeed) >= 0.1 || Math.abs(rotateSpeed) >= 0.1){ //Left joystick used
      Robot.m_drivetrain.arcadeDrive(moveSpeed*1, rotateSpeed*1);
    }else if(Math.abs(slowMoveSpeed) >= 0.1 || Math.abs(slowRotateSpeed) >= 0.1){ //Right joystick used
     Robot.m_drivetrain.arcadeDrive(slowMoveSpeed*0.75, slowRotateSpeed*0.75);
   }
   //else{
   // Robot.m_drivetrain.arcadeDrive(0, 0);
   //}
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_drivetrain.arcadeDrive(0,0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
