/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class LimeLight extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("limelight");
  

  public LimeLight() {
    inst = NetworkTableInstance.getDefault();
    table = inst.getTable("limelight");

  }



  public int getValidTarget() {
    NetworkTableEntry tv;
    tv = table.getEntry("tv");
    return tv.getNumber(0).intValue();
  }

  public int getShortBB() {
    NetworkTableEntry tshort;
    tshort = table.getEntry("tshort");
    return tshort.getNumber(0).intValue();
  }

  public int getLongBB() {
    NetworkTableEntry tlong;
    tlong = table.getEntry("tlong");
    return tlong.getNumber(0).intValue();
  }

  public double getHorizontalOffset() {
    NetworkTableEntry tx;
    tx = table.getEntry("tx");
    return tx.getNumber(0).doubleValue();
  }

  public double getVerticalOffset() {
    NetworkTableEntry ty;
    ty = table.getEntry("ty");
    return ty.getNumber(0).doubleValue();
  }

  public int getLEDStatus() {
    NetworkTableEntry ledMode;
    ledMode = table.getEntry("ledMode");
    return ledMode.getNumber(0).intValue();
  }
  
  public void turnLEDOff() {
    NetworkTableEntry ledMode;
    ledMode = table.getEntry("ledMode");
    ledMode.setNumber(RobotMap.LIME_LIGHT_LIGHT_OFF);
  }

  public void turnLEDOn() {
    NetworkTableEntry ledMode;
    ledMode = table.getEntry("ledMode");
    ledMode.setNumber(RobotMap.LIME_LIGHT_LIGHT_ON);
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new ActivateLimeLight());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());


  }
}
