/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Galaga extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  DoubleSolenoid slideSolenoid;
  DoubleSolenoid galagaSolenoid;

  public Galaga() {
    slideSolenoid = new DoubleSolenoid(RobotMap.SOLENOIDS_SLIDE_FORWARD, RobotMap.SOLENOIDS_SLIDE_BACKWARDS);
    galagaSolenoid = new DoubleSolenoid(RobotMap.SOLENOIDS_GALAGA_FORWARD, RobotMap.SOLENOIDS_GALAGA_BACKWARDS);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void galagaOut() {
    galagaSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void galagaIn() {
    galagaSolenoid.set(DoubleSolenoid.Value.kReverse);
  }
  public void slideOut() {
    slideSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void slideIn() {
    slideSolenoid.set(DoubleSolenoid.Value.kReverse);
  }
}
