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
import java.lang.Math;

public class DriveArcade extends Command {
  public DriveArcade() {
    requires(Robot.m_drivetrain);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    double moveSpeed = Robot.m_oi.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_MOVE_AXIS);
    double rotateSpeed = Robot.m_oi.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_ROTATE_AXIS);
    double slowMoveSpeed = Robot.m_oi.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_RIGHT_MOVE_AXIS);
    double slowRotateSpeed = Robot.m_oi.driverController.getRawAxis(RobotMap.DRIVER_CONTROLLER_RIGHT_ROTATE_AXIS);
    if((Math.abs(moveSpeed) >= 0.1 || Math.abs(rotateSpeed) >= 0.1) && (Math.abs(slowMoveSpeed) >= 0.1 || Math.abs(slowRotateSpeed) >= 0.1)){
      System.out.println("slow moving " + moveSpeed + " " + rotateSpeed);
      Robot.m_drivetrain.arcadeDrive(slowMoveSpeed*0.5, slowRotateSpeed*0.8);
    }else if(Math.abs(moveSpeed) >= 0.1 || Math.abs(rotateSpeed) >= 0.1){ //Left joystick used
      System.out.println("moving " + moveSpeed + " " + rotateSpeed);
      Robot.m_drivetrain.arcadeDrive(moveSpeed*1, rotateSpeed*1);
    }else if(Math.abs(slowMoveSpeed) >= 0.1 || Math.abs(slowRotateSpeed) >= 0.1){ //Right joystick used
      System.out.println("slow moving2 " + moveSpeed + " " + rotateSpeed);
     Robot.m_drivetrain.arcadeDrive(slowMoveSpeed*0.5, slowRotateSpeed*0.8);
   }
   else{
    Robot.m_drivetrain.stop();
   }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.m_drivetrain.stop();
  }
}
