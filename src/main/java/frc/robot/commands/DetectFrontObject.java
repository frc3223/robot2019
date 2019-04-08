/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;

public class DetectFrontObject extends Command {
  boolean rumbled;
  Timer time;
  public DetectFrontObject() {
    time = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    rumbled = false;
    System.out.println("front object initalized");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.m_oi.isCargoSensorTarget() || Robot.m_oi.isHatchSensor()){
      if(!rumbled){
        time.start();
        System.out.println("front object - should rumble");
        Robot.m_oi.driverController.setRumble(Joystick.RumbleType.kRightRumble,1.0);
        Robot.m_oi.manipulatorController.setRumble(Joystick.RumbleType.kRightRumble,1.0);
        rumbled = true;  
        }
    }else{
      rumbled = false;
    }
    if(time.get() > 1.0){
      System.out.println("front object - should stop rumbling");
      Robot.m_oi.driverController.setRumble(Joystick.RumbleType.kRightRumble,0.0);
      Robot.m_oi.manipulatorController.setRumble(Joystick.RumbleType.kRightRumble,0.0);
      time.stop();
      time.reset();
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
