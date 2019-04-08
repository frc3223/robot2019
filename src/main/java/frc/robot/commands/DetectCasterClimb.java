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

public class DetectCasterClimb extends Command {
  boolean climbedYet;
  boolean rumbled;
  Timer time;
  boolean done;
  public DetectCasterClimb() {
    //requires(Robot.m_photoSensor);
    time = new Timer();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    climbedYet = false;
    System.out.println("caster climb initalized");
    rumbled = false;
    done = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.m_oi.isCasterTarget()){
      if(climbedYet){
        Robot.m_oi.driverController.setRumble(Joystick.RumbleType.kLeftRumble,1.0);
        Robot.m_oi.manipulatorController.setRumble(Joystick.RumbleType.kLeftRumble, 1.0);
        time.start();
        //System.out.println("caster - should rumble");
        climbedYet = false;
      }
    }else{
      //climbing
      //System.out.println("caster - no longer sees ground");
      climbedYet = true;
    }
    if(time.get() > 2.0 && !rumbled){
      Robot.m_oi.driverController.setRumble(Joystick.RumbleType.kLeftRumble,0.0);
      Robot.m_oi.manipulatorController.setRumble(Joystick.RumbleType.kLeftRumble, 0.0);
      //System.out.println("caster - rumbling should stop");
      rumbled = true;
      time.stop();
      done = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return done;
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
