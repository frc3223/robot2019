/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class PhotoSensor extends Subsystem {
  DigitalInput frontSensor;
  DigitalInput casterSensor;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public PhotoSensor(){
    frontSensor = new DigitalInput(RobotMap.FRONT_PHOTO_SENSOR);
    casterSensor = new DigitalInput(RobotMap.CASTER_PHOTO_SENSOR);
    //setDefaultCommand(new )
  }

  public boolean isFrontTarget(){
    return frontSensor.get();
  }

  public boolean isCasterTarget(){
    return casterSensor.get();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
