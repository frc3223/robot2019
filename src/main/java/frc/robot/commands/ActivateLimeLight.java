/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ActivateLimeLight extends Command {
  double correctHeight = 5.75; //inches
  double correctWidth = 15.5;  //inches
  double correctRatio = correctWidth / correctHeight;
  boolean aligned;
  Timer time = new Timer();
  double timeAligned = 0.0;
  boolean moveForward = false;
  public ActivateLimeLight() {
    requires(Robot.m_limelight);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    aligned = false;
    time.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  if(aligned == false){
    Robot.m_limelight.turnLEDOn();
  }
    int tv = Robot.m_limelight.getValidTarget();
    if(tv == 1) { // If the target is valid
      double tlong = Robot.m_limelight.getLongBB();
      double tshort = Robot.m_limelight.getShortBB();
      double targetRatio = tlong / tshort;
      if(targetRatio <= correctRatio + 0.3 && targetRatio >= correctRatio - 0.3) { //Correct ratio and correct target ratio
        double tx = Robot.m_limelight.getHorizontalOffset();
        if(tx < -2) {
          //System.out.println("Move to the left");
          Robot.m_drivetrain.moveLeft();
          Robot.m_limelight.turnLEDOn();
        }else if(tx > 2) {
          //System.out.println("Move to the right");
          Robot.m_drivetrain.moveRight();
          Robot.m_limelight.turnLEDOn();
        }else if(tx >= -2 && tx <= 2) {
          System.out.println("Move forward / Aligned");
          //Robot.m_drivetrain.stop();
          aligned = true;
          if(!moveForward){
            timeAligned = time.get();
            moveForward = true;
          }else if(time.get() - timeAligned >= 2.5){
            Robot.m_drivetrain.stop();
            Robot.m_limelight.turnLEDOff();
            moveForward = false;
          }else{
            moveForward = true;
            Robot.m_limelight.turnLEDOn();
            Robot.m_drivetrain.moveForward();
          }
        }
            
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
    Robot.m_limelight.turnLEDOff();
    Robot.m_drivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
 