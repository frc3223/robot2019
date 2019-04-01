/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Galaga;
import frc.robot.OI;
import frc.robot.RobotMap;

public class GalagaTest extends Command {
  Galaga subsystem;
  OI oi;
  public GalagaTest(Galaga galaga,OI oi) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  this.subsystem = galaga;
  this.oi = oi;
  this.requires(galaga);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean xPressed = this.oi.manipulatorController.getRawButton(RobotMap.MANIPULATOR_CONTROLLER_GALAGA_OUT);
    if(xPressed){
      this.subsystem.galagaOut();
      System.out.println("the X button was pressed and the galaga should have opened.");
    }
    boolean aPressed = this.oi.manipulatorController.getRawButton(RobotMap.MANIPULATOR_CONTROLLER_SLIDE_IN);
    if(aPressed){
      this.subsystem.slideIn();
      System.out.println("the A button was pressed and the slide should have slid backwards.");
    }
    boolean yPressed = this.oi.manipulatorController.getRawButton(RobotMap.MANIPULATOR_CONTROLLER_SLIDE_OUT);
    if(yPressed){
      this.subsystem.slideOut();
      System.out.println("the Y button was pressed and the slide should have slid forwards.");
    }
    boolean bPressed = this.oi.manipulatorController.getRawButton(RobotMap.MANIPULATOR_CONTROLLER_GALAGA_IN);
    if(bPressed){
      this.subsystem.galagaIn();
      System.out.println("the B button was pressed and the galaga should have closed.");
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
