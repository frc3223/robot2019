/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.TrapezoidalProfiler;
import frc.robot.subsystems.Climber;

public class DeployAllStilts extends Command {
  TrapezoidalProfiler profiler;
  Climber subsystem;

  public DeployAllStilts() {
    profiler = new TrapezoidalProfiler(10, 30, -20, 0.1, 0);
    subsystem = new Climber(Robot.m_oi);
    requires(subsystem);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Deploy All Stilts initalized");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    profiler.calculate_new_velocity(profiler.getCurrentPosition(), 0.02);
    this.subsystem.setTargetPosition(profiler.getCurrentPosition());

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println("finished deploying stilts");
    return profiler.isFinished(profiler.getCurrentPosition());
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
