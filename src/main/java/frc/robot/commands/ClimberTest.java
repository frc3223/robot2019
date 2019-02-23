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

public class ClimberTest extends Command {
  Climber subsystem;
  OI oi;
  int increment;

  public ClimberTest(Climber subsystem, OI oi) {
    // Use requires() here to declare subsystem dependencies
    this.subsystem = subsystem;
    this.oi = oi;
    requires(subsystem);
    increment = 3;
    subsystem.reset();
    System.out.println("tacos");
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean pressed = this.oi.driverController.getRawButton(6);
    double liftingVoltage = 0.1;
    if(pressed){
      subsystem.moveLeftBack(liftingVoltage);
      subsystem.moveRightFront(liftingVoltage);
      subsystem.moveLeftFront(liftingVoltage);
      subsystem.moveRightBack(liftingVoltage);
    }else{
        subsystem.moveLeftBack(0);
      subsystem.moveRightFront(0);
      subsystem.moveLeftFront(0);
      subsystem.moveRightBack(0);
    }
    System.out.println("Position: " + subsystem.rightBackPosition());
    System.out.println("Ticks   : " + subsystem.rightBackTicks());
    System.out.println(" CC     : " + subsystem.conversionFactor());
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
