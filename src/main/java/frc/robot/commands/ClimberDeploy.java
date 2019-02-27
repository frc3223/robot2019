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

public class ClimberDeploy extends Command {
  Climber subsystem;
  OI oi;
  int increment;

  public ClimberDeploy(Climber subsystem, OI oi) {
    // Use requires() here to declare subsystem dependencies
    this.subsystem = subsystem;
    this.oi = oi;
    requires(subsystem);
    increment = 3;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean RBdone = false;
    boolean LBdone = false;
    boolean RFdone = false;
    boolean LFdone = false;
    double stallVoltage = 0.1;
    double liftingVoltage = 0.5;
    if(subsystem.rightBackPosition() < increment){
      subsystem.moveRightBack(liftingVoltage);
    }
    if(subsystem.rightBackPosition() >= increment){
      subsystem.moveRightBack(stallVoltage);
      RBdone = true;
    }
    if(subsystem.leftBackPosition() < increment){
      subsystem.moveLeftBack(liftingVoltage);
    }
    if(subsystem.leftBackPosition() >= increment){
      subsystem.moveLeftBack(stallVoltage);
      LBdone = true;
    }
    if(subsystem.rightFrontPosition() < increment){
      subsystem.moveRightFront(liftingVoltage);
    }
    if(subsystem.rightFrontPosition() >= increment){
      subsystem.moveRightFront(stallVoltage);
      RFdone = true;
    }
    if(subsystem.leftFrontPosition() < increment){
      subsystem.moveLeftFront(liftingVoltage);
    }
    if(subsystem.leftFrontPosition() >= increment){
      subsystem.moveLeftFront(stallVoltage);
      LFdone = true;
    }
    if(RBdone && LBdone && RFdone && LFdone){
      increment += increment;
      if (increment > 21){
        end();
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
