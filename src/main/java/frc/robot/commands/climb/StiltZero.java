/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.Climber;
import frc.robot.RobotMap;
import frc.robot.OI;

public class StiltZero extends TimedCommand {
  Climber subsystem;
    OI oi;
  public StiltZero(Climber subsystem) {
    super(1);
    this.subsystem = subsystem;
    requires(subsystem);

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
    subsystem.moveUp(0.1);

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    subsystem.reset();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
