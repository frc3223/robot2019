/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.TrapezoidalProfiler;
import frc.robot.subsystems.Climber;

public class SSClimberTest2 extends Command {
  Climber subsystem;
  OI oi;
  TrapezoidalProfiler profiler;
  int increment;

  public SSClimberTest2(Climber subsystem, OI oi) {
    this.subsystem = subsystem;
    this.oi = oi;
    requires(subsystem);
    increment = 3;
    subsystem.reset();
    profiler = new TrapezoidalProfiler(6, 36, -3, 0.1, 0);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
      profiler.calculate_new_velocity(profiler.getCurrentPosition(), 0.02);
      this.subsystem.setTargetPosition(profiler.getCurrentPosition());
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
