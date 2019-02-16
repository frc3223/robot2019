/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  DoubleSolenoid intakeSolenoid;
  WPI_VictorSPX intakeMotorController;

  public Intake() {
    intakeSolenoid = new DoubleSolenoid(RobotMap.PNEUMATICS_MODULE_TWO,RobotMap.SOLENOIDS_INTAKE_FORWARDS, RobotMap.SOLENOIDS_INTAKE_BACKWARDS);
    intakeMotorController = new WPI_VictorSPX(RobotMap.INTAKE_VICTOR);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void intakeOut() {
    intakeSolenoid.set(DoubleSolenoid.Value.kForward);
  }
  public void intakeIn() {
    intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
  }
  public void intakeMotorOn() {
    intakeMotorController.set(0.75);
  }
  public void intakeMotorOff() {
    intakeMotorController.set(-0.75);
  }
}
