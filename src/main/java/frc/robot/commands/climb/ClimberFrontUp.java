/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;

public class ClimberFrontUp extends Command {
  Climber subsystem;
  OI oi;
  public ClimberFrontUp(Climber subsystem, OI oi) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.subsystem = subsystem;
    this.oi = oi;
    requires(subsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Raising front stilts");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.subsystem.setFrontTargetPosition(0);
    //this.subsystem.liftFront();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if((Math.abs(this.subsystem.leftFrontPosition())) <= 1 && (Math.abs(this.subsystem.rightFrontPosition())) <= 1){
      System.out.println("Finished raising front stilts");
      return true;
    }else{
    return false;
    }
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
