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

public class SSClimberTest extends Command {
  Climber subsystem;
  OI oi;
  int increment;

  public SSClimberTest(Climber subsystem, OI oi) {
    this.subsystem = subsystem;
    this.oi = oi;
    requires(subsystem);
    increment = 3;
    subsystem.reset();
  }

  @Override
  protected void initialize() {
    subsystem.setTargetPosition(-3);
      
  }

  @Override
  protected void execute() {
      this.subsystem.calculateSSC();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
