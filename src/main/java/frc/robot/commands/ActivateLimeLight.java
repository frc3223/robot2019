/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;


public class ActivateLimeLight extends Command {
  double currentHeight;
  double currentWidth;
  boolean aligned;
  public ActivateLimeLight() {
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
  protected void execute() {
    //Number tv = Robot.m_limelight.getValidTargets();

    //boolean pressed = Robot.m_oi.driverController.getRawButtonPressed(RobotMap.LIME_LIGHT_ON_BUTTON);
    //if(pressed) {
      
      //System.out.println("pressed");
      //Number LEDstatus = Robot.m_limelight.getLEDStatus();
      //System.out.println("LEDStatus: " + LEDstatus);
      //if(LEDstatus.intValue() == 0 || LEDstatus.intValue() == 1) {
        Robot.m_limelight.turnLEDOn();
        
      //}
      //else if(LEDstatus.intValue() == 3) {
      //  Robot.m_limelight.turnLEDOff();
      //}
      
      
    //}

    



   
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
