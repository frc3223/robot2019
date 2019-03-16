/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LimeLightAlign extends Command {
  double correctHeight = 5.75; //inches
  double correctWidth = 15.5;  //inches
  double correctRatio = correctWidth / correctHeight;
  boolean aligned;
  boolean stopAnyways = false;
  double timeAligned = 0.0;
  boolean moveForward = false;
  public LimeLightAlign() {
    requires(Robot.m_limelight);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    aligned = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute(){
    if(aligned == false){
      System.out.println("starting LimeLightAlign Execute");
      int tv = Robot.m_limelight.getValidTarget();
      if(tv == 1) { // If the target is valid

        System.out.println("valid target found");
        double tlong = Robot.m_limelight.getLongBB();
        double tshort = Robot.m_limelight.getShortBB();
        double targetRatio = tlong / tshort;
        System.out.println("tlong= " + tlong + ", tshort= " + tlong);
        System.out.println("targetRatio = " + targetRatio + ", correctRatio= " + correctRatio);

        if(targetRatio <= correctRatio + 0.4 && targetRatio >= correctRatio - 0.4) { //Correct ratio and correct target ratio
          System.out.println("bounding box x is: " + Robot.m_limelight.getLongBB());
          System.out.println("bounding box y is: " + Robot.m_limelight.getShortBB());
          double horizOff = Robot.m_limelight.getHorizontalOffset();

          if(horizOff < -2) {// if the offset is too far right
            System.out.println("needs to move left");
            Robot.m_drivetrain.moveLeft();

          }else if(horizOff > 2) { // if the offset is too far left
            System.out.println("needs to move right");
            Robot.m_drivetrain.moveRight();

          }else if(horizOff >= -2 && horizOff <= 2) {// if off set is just right
            System.out.println("Move forward / Aligned");
            Robot.m_drivetrain.stop();
            aligned = true;
            end();
           
            
            /*if(!moveForward){
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
            }*/
          }
              
        }else{
          stopAnyways = true;
        }
      }else{
        stopAnyways = true;
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean result = aligned || stopAnyways;
    aligned = false;
    stopAnyways = false;
    return result;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    //Robot.m_limelight.turnLEDOff();
    Robot.m_drivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    stopAnyways = true; 
  }
}