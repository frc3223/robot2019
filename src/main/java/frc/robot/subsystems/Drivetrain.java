/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveArcade;

public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  WPI_TalonSRX leftFrontTalon = null;
  WPI_VictorSPX leftBackVictor = null;
  WPI_TalonSRX rightFrontTalon = null;
  WPI_VictorSPX rightBackVictor = null;
  PowerDistributionPanel pdp;

  DifferentialDrive differentialDrive = null;

  public Drivetrain() {
    leftFrontTalon = new WPI_TalonSRX(RobotMap.DRIVETRAIN_LEFT_FRONT_TALON);
    leftBackVictor = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_BACK_VICTOR);
    rightFrontTalon = new WPI_TalonSRX(RobotMap.DRIVETRAIN_RIGHT_FRONT_TALON);
    rightBackVictor = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_BACK_VICTOR);

    pdp = new PowerDistributionPanel(0);
    leftFrontTalon.setInverted(false);
    leftBackVictor.setInverted(false);
    rightFrontTalon.setInverted(false);
    rightBackVictor.setInverted(false);

    SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFrontTalon, leftBackVictor);
    SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFrontTalon, rightBackVictor);

    differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
  }

  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }

  public void moveLeft() {
    arcadeDrive(1, 1);
  }
  public void moveRight() {
    arcadeDrive(1, 1);
  }

  public void stop() {
    arcadeDrive(0, 0);
  }

  public void moveForward(){
    arcadeDrive(0.7 ,0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new DriveArcade());

  }

  public void logMotorCurrents(DataLogger logger) {
      logger.add("Current (Motor rf)", () -> {
        return this.rightFrontTalon.getOutputCurrent();
      });
      logger.add("Current (Motor rr)", () -> {
        return this.pdp.getCurrent(1);
      });
      logger.add("Current (Motor lf)", () -> {
        return this.leftFrontTalon.getOutputCurrent();
      });
      logger.add("Current (Motor lr)", () -> {
        return this.pdp.getCurrent(14);
      });
  }
}
