/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.subsystems.Carriage;
import frc.robot.Robot;

public class CarriagePushPull extends Command {
  public CarriagePushPull() {
    requires(Robot.m_carriage);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double leftTrigger = Robot.m_oi.manipulatorController.getRawAxis(RobotMap.MANIPULATOR_CONTROLLER_CARGO_PULLIN);
    double rightTrigger = Robot.m_oi.manipulatorController.getRawAxis(RobotMap.MANIPULATOR_CONTROLLER_CARGO_PUSHOUT);
    if(leftTrigger < 0 && rightTrigger == 0){ //Left pressed
      Robot.m_carriage.carriagePullIn();
    }else if(rightTrigger < 0 && leftTrigger == 0){ //Right pressed
      Robot.m_carriage.carriagePushOut();
    }else if(leftTrigger < 0 && rightTrigger < 0){ //Both pressed
      Robot.m_carriage.carriageStop();
    }else if(leftTrigger == 0 && rightTrigger == 0){ //Neither pressed
      Robot.m_carriage.carriageStop();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_carriage.carriageStop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
