/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.GalagaTest;
import frc.robot.OI;

/**
 * Add your docs here.
 */
public class Galaga extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  DoubleSolenoid slideSolenoid;
  DoubleSolenoid galagaSolenoid;
  OI oi;

  public Galaga(OI oi) {
    slideSolenoid = null; //new DoubleSolenoid(RobotMap.PNEUMATICS_MODULE,RobotMap.SOLENOIDS_SLIDE_FORWARD, RobotMap.SOLENOIDS_SLIDE_BACKWARDS);
    galagaSolenoid = null; // new DoubleSolenoid(RobotMap.PNEUMATICS_MODULE,RobotMap.SOLENOIDS_GALAGA_FORWARD, RobotMap.SOLENOIDS_GALAGA_BACKWARDS);
    this.oi = oi;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new GalagaTest(this, this.oi));
  }

  public void galagaOut() {
    galagaSolenoid.set(Value.kForward);
  }

  public void galagaIn() {
    galagaSolenoid.set(Value.kReverse);
  }
  public void slideOut() {
    slideSolenoid.set(Value.kForward);
  }

  public void slideIn() {
    slideSolenoid.set(Value.kReverse);
  }
}
