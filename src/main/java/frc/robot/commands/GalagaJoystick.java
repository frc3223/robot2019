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
import frc.robot.subsystems.Galaga;

public class GalagaJoystick extends Command {
  Galaga subsystem;
  OI oi;

  public GalagaJoystick(Galaga subsystem, OI oi) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
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
    boolean xbuttonpressed = oi.manipulatorController.getRawButton(RobotMap.MANIPULATOR_CONTROLLER_GALAGA_OUT);
    if(xbuttonpressed) {
      this.subsystem.galagaOut();
    }

    boolean bbuttonpressed = oi.manipulatorController.getRawButton(RobotMap.MANIPULATOR_CONTROLLER_GALAGA_IN);
    if(bbuttonpressed) {
      this.subsystem.galagaIn();
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
