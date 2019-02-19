/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;
import frc.robot.RobotMap;

public class ClimberMove extends Command {
  Climber subsystem;
  OI oi;
  public ClimberMove(Climber subsystem, OI oi) {
    // Use requires() here to declare subsystem dependencies
    this.subsystem = subsystem;
    this.oi = oi;
    requires(subsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean forward = this.oi.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_FORWARD);
    boolean backward = this.oi.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_BACKWARD);
    if(forward){
      this.subsystem.move(1);
    }else if(backward){
      this.subsystem.move(-1);
    }else{
      this.subsystem.stopMotor();
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
    this.subsystem.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
