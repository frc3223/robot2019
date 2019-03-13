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

public class ClimberDeploy extends Command {
  Climber subsystem;
  OI oi;
  int increment;

  public ClimberDeploy(Climber subsystem, OI oi) {
    // Use requires() here to declare subsystem dependencies
    this.subsystem = subsystem;
    this.oi = oi;
    requires(subsystem);
    increment = 1;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean Bdone = false;
    boolean RFdone = false;
    boolean LFdone = false;
    double stallVoltageFront = -0.05;
    double stallVoltageBack = -0.07;
    double liftingVoltageFront = -0.3;
    double liftingVoltageBack = -0.4;
    if(subsystem.backPosition() > -increment){
      subsystem.moveBack(liftingVoltageBack);
      System.out.println("B lifting");
    }else if(subsystem.backPosition() <= -increment){
      System.out.println("B holding");
      subsystem.moveBack(stallVoltageBack);
      Bdone = true;
    }
    if(subsystem.rightFrontPosition() > -increment){
      System.out.println("RF lifting");
      subsystem.moveRightFront(liftingVoltageFront);
    }
    if(subsystem.rightFrontPosition() <= -increment){
      subsystem.moveRightFront(stallVoltageFront);
      System.out.println("RF holding");
      RFdone = true;
    }
    if(subsystem.leftFrontPosition() < increment){
      System.out.println("LF lifting");
      subsystem.moveLeftFront(liftingVoltageFront);
    }
    if(subsystem.leftFrontPosition() >= increment){
      subsystem.moveLeftFront(stallVoltageFront);
      System.out.println("LF holding");
      LFdone = true;
    }
    if(Bdone && RFdone && LFdone){
      if (increment < 18){
          increment += increment;
      }
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
