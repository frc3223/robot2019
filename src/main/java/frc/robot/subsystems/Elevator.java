/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.StateSpaceController;
import frc.robot.commands.ElevatorJoystick;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static enum ElevatorLevels {
      BOTTOM, MIDDLE, TOP
  }

  public double ticksPerRev = 42 * 5;
  public double sprocketRadius = 0.5625;
  public ElevatorLevels elevatorLevel = ElevatorLevels.BOTTOM;
  public Notifier notifier;
  public StateSpaceController stateSpaceController;
  public CANSparkMax[] motors;

  private boolean isDisabled = false;
  private double targetPosition = 0;
  private double targetVelocity = 0;
  public OI oi;
  PowerDistributionPanel pdp;
  public Elevator(OI oi) {
      pdp = new PowerDistributionPanel(0);
      this.oi = oi;
      motors = new CANSparkMax[] {
        new CANSparkMax(RobotMap.DRIVETRAIN_ELEVATOR_VICTOR, MotorType.kBrushless)
      };
      initStateSpace();
      notifier = new Notifier(new Runnable(){
        @Override
        public void run() {
            calculate();
        }
      });
      notifier.startPeriodic(0.02);
  }

/*  The move functions will move the elevator to the level specified  */
/*  in the function name. As an example, moveBottom() will move the   */
/*  elevator to the bottom level.                                     */
  public void moveBottom() {

  }

  public void moveMiddle() {

  }

  public void moveTop() {

  }

  public double getBusVoltage() {
      return this.motors[0].getBusVoltage();
  }
 
  public void logMotorVoltage(DataLogger logger) {
      for(int j = 0; j < this.motors.length; j++) {
        int temp = j;
        logger.add("Voltage (Motor " + (temp + 1) + ")", () -> {
            return this.motors[temp].getAppliedOutput();
        });
      }
  }

  public void logMotorCurrent(DataLogger logger) {
    for(int j = 0; j < this.motors.length; j++) {
      int temp = j;
      logger.add("Current (Motor " + (temp + 1) + ")", () -> {
          return this.motors[temp].getOutputCurrent();
      });
    }
}

  //For when the driver wants to manually control the elevator level
  public void moveElevator(double voltPercent) {
    for(int i = 0; i < this.motors.length; i++) {
        this.motors[i].set(voltPercent);
    }
  }

/*  This is the method called in order to keep the elevator in a stable position.  */

  public void maintainLevel() {
    double keepElevatorInPlace = 0.1;
    moveElevator(keepElevatorInPlace);
  }

  public synchronized void setDisabled(boolean isDisabled) {
    if(!isDisabled){
      setTargetPosition(getPosition());
    }
    this.isDisabled = isDisabled;
  }

  public synchronized boolean isDisabled() {
    return this.isDisabled;
  }

  public synchronized void setTargetPosition(double pos) {
    this.targetPosition = pos;
  }

  public synchronized double getTargetPosition() {
      return this.targetPosition;
  }

  public synchronized void setTargetVelocity(double vel) {
    this.targetVelocity = vel;
  }

  public synchronized double getTargetVelocity() {
      return this.targetVelocity;
  }

  public double getVelocity() {
    double rawEncoderTicks = this.motors[0].getEncoder().getVelocity();
    return (rawEncoderTicks / this.ticksPerRev) * (2 * Math.PI * this.sprocketRadius) * 10;
  }

  public double getPosition() {
      double ticks = motors[0].getEncoder().getPosition();
      double val = (ticks / this.ticksPerRev) * (2 * Math.PI * this.sprocketRadius);
      return val;
  }

  public void calculate() {
    if(stateSpaceController == null){
        System.out.println("stateSpaceController is null");
    }
    if(!isDisabled()){
        stateSpaceController.update();
        stateSpaceController.setInput((r) -> {
            //Target position (meter)
            r.set(0, 0, getTargetPosition());
            //Target velocity (meters/second)
            r.set(1, 0, getTargetVelocity());
        });
        double voltage = stateSpaceController.u.get(0, 0);
        for(int i = 0; i < motors.length; i++){
            double availableVolt = motors[i].getBusVoltage();
            double percentVolt = voltage / availableVolt;
            motors[i].set(percentVolt);
        }
        stateSpaceController.setOutput((y) -> {
            //Position in meters
            y.set(0, 0, getPosition());
        });
        stateSpaceController.predict();
    }
  }

  public void initStateSpace() {
    stateSpaceController = new StateSpaceController();
    stateSpaceController.init(2, 1, 1);
    double[][] a = new double[][] {
        { 1, 1.25e-3 },
        { 0, 1.15e-7 }
    };
    double[][] a_inv = new double[][] {
        { 1, -1.09e+4 },
        { 0, 8.70e+6 }
    };
    double[][] b = new double[][] {
        { 0.024 },
        { 1.27 }
    };
    double[][] c = new double[][] {
        { 1, 0 }
    };
    double[][] k = new double[][] {
        { 5.08, 0.006 }
    };
    double[][] kff = new double[][] {
        { 0.69, 0.77 }
    };
    double[][] L = new double[][] {
        { 1 },
        { 6.61e-15 }
    };

    StateSpaceController.assign(stateSpaceController.A, a);
    StateSpaceController.assign(stateSpaceController.A_inv, a_inv);
    StateSpaceController.assign(stateSpaceController.B, b);
    StateSpaceController.assign(stateSpaceController.C, c);
    StateSpaceController.assign(stateSpaceController.K, k);
    StateSpaceController.assign(stateSpaceController.Kff, kff);
    StateSpaceController.assign(stateSpaceController.L, L);
    stateSpaceController.u_min.set(0, 0, -12);
    stateSpaceController.u_max.set(0, 0, 12);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ElevatorJoystick(this, this.oi ));
  }

  public void measureVoltage(double volts) {

  }

}
