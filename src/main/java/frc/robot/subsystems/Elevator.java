/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.StateSpaceController;
import frc.robot.commands.elevator.ElevatorJoystick;
import frc.robot.commands.elevator.MoveElevSetpoint;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static enum ElevatorLevels {
    BOTTOM, MIDDLE, TOP
  }

  public double ticksPerRev = 42;
  public double sprocketRadius = 0.0142875;// meters for a 16 tooth sprocket
  public int Ng = 10;
  public ElevatorLevels elevatorLevel = ElevatorLevels.BOTTOM;
  public Notifier notifier;
  public StateSpaceController stateSpaceController;
  public CANSparkMax[] motors;
  public DataLogger logger;

  private boolean isDisabled = false;
  private double targetPosition = 0;
  private double targetVelocity = 0;
  private double maxPosition = 0;
  private double minPosition = -1.6;

  public double[] elevatorPositions = new double[] { 0, // lowlevel hatch height - actually 48.26cm/19iches - subtract from everything
      -0.20, // supposed lowlevel cargo height 27 inches
      -0.7232, // supposed midlevel hatch height 47.5 inches
      -0.9271, // supposed midlevel cargo height 55.5 in
      -1.4351, // supposed highlevel hatch height 75.5
      -1.6// highlevel cargo height 84
  };

  public OI oi;
  NetworkTable table;

  public Elevator(OI oi) {
    this.oi = oi;
    motors = new CANSparkMax[] { new CANSparkMax(RobotMap.DRIVETRAIN_ELEVATOR_CAN, MotorType.kBrushless) };
    motors[0].getEncoder().setPositionConversionFactor(2 * Math.PI * sprocketRadius * 2 / Ng);
    motors[0].getEncoder().setVelocityConversionFactor(2 * Math.PI * sprocketRadius * 2 / Ng);
    initStateSpace();
    this.setDisabled(true);
    notifier = new Notifier(new Runnable() {
      @Override
      public void run() {
        calculate();
      }
    });
    this.reset();
    this.logger = new DataLogger("Elevator");
    notifier.startPeriodic(0.02);

    table = NetworkTableInstance.getDefault().getTable("Elevator");
  }

  public void goUpPosition() {
    double currentTar = getTargetPosition();
    if (currentTar == elevatorPositions[elevatorPositions.length-1]) {
      return;
    }
    for (int a = 0; a < elevatorPositions.length; a++) {
      if (elevatorPositions[a] < currentTar) {
        setTargetPosition(elevatorPositions[a]);
        break;
      }
    }
  }

  public void goDownPosition(){
    double currentTar = getTargetPosition();
    if (currentTar == elevatorPositions[0]) {
      return;
    }
    for (int a = elevatorPositions.length-1; a >= 0; a--){
      if (elevatorPositions[a] > currentTar) {
        setTargetPosition(elevatorPositions[a]);
        break;
      }
    }
  }

  public void stopAtCurrentPos(){
    double pos = getCurrentPosition();
    setTargetPosition(pos); 
  }

  public double getCurrentPosition(){
    return this.motors[0].getEncoder().getPosition();
  }

  /* This method will call all internal logging methods. */
  public void logEverything() {
    logMotorVoltage(this.logger);
    logMotorCurrent(this.logger);
    logMotorTemperature(this.logger);
    logMotorPosition(this.logger);
  }

  /* The move functions will move the elevator to the level specified */
  /* in the function name. As an example, moveBottom() will move the */
  /* elevator to the bottom level. */
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
    for (int j = 0; j < this.motors.length; j++) {
      int temp = j;
      logger.add("Voltage (Motor " + (temp + 1) + ")", () -> {
        return this.motors[temp].getAppliedOutput();
      });
    }
  }

  public void logMotorTemperature(DataLogger logger) {
    for (int i = 0; i < this.motors.length; i++) {
      int temp = i;
      logger.add("Temperature (Motor " + (temp + 1) + ")", () -> {
        return this.motors[temp].getMotorTemperature();
      });
    }
  }

  public void logMotorCurrent(DataLogger logger) {
    for (int j = 0; j < this.motors.length; j++) {
      int temp = j;
      logger.add("Current (Motor " + (temp + 1) + ")", () -> {
        return this.motors[temp].getOutputCurrent();
      });
    }
  }

  public void logMotorPosition(DataLogger logger) {
    logger.add("Position", () -> {
      return this.getPosition();
    });
  }

  // For when the driver wants to manually control the elevator level
  public void moveElevator(double voltPercent) {
    for (int i = 0; i < this.motors.length; i++) {
      this.motors[i].set(voltPercent);
    }
  }

  /*
   * This is the method called in order to keep the elevator in a stable position.
   */

  public void maintainLevel() {
    double keepElevatorInPlace = 0.1;
    moveElevator(keepElevatorInPlace);
  }

  public synchronized void setDisabled(boolean isDisabled) {
    if (!isDisabled) {
      setTargetPosition(getPosition());
      stateSpaceController.x_prev.set(0, 0, getPosition());
    }
    this.isDisabled = isDisabled;
  }

  public void reset() {
    for (int j = 0; j < this.motors.length; j++) {
      this.motors[j].getEncoder().setPosition(0);
    }
  }

  public synchronized boolean isDisabled() {
    return this.isDisabled;
  }

  public synchronized void setTargetPosition(double pos) {
    if (pos <= this.minPosition) {
      pos = this.minPosition;
    } else if (pos >= this.maxPosition) {
      pos = this.maxPosition;
    }
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
    return rawEncoderTicks;
  }

  public double getPosition() {
    double ticks = motors[0].getEncoder().getPosition();
    return ticks;
  }

  public void calculate() {
    if (stateSpaceController == null) {
      System.out.println("stateSpaceController is null");
    }
    if (!isDisabled()) {
      stateSpaceController.update();
      stateSpaceController.setInput((r) -> {
        // Target position (meter)
        r.set(0, 0, getTargetPosition());
        // Target velocity (meters/second)
        r.set(1, 0, getTargetVelocity());
      });
      double voltage = stateSpaceController.u.get(0, 0);
      for (int i = 0; i < motors.length; i++) {
        double availableVolt = motors[i].getBusVoltage();
        double percentVolt = voltage / availableVolt;
        motors[i].set(percentVolt);
      }
      stateSpaceController.setOutput((y) -> {
        // Position in meters
        y.set(0, 0, getPosition());
      });
      stateSpaceController.predict();
    }
  }

  public void initStateSpace() {
    stateSpaceController = new StateSpaceController();
    stateSpaceController.init(2, 1, 1);
    double[][] a = new double[][] { { 1.0, 2.8000216798070815e-05 }, { 0.0, 6.1952331876713e-311 } };
    double[][] a_inv = new double[][] { { 1.0, 0.0 }, { 0.0, 1.0 } };
    double[][] b = new double[][] { { 0.002826816062863344 }, { 0.14153895922034437 } };
    double[][] c = new double[][] { { 1, 0 } };
    double[][] k = new double[][] { { 61.58698057020303, 0.0017244488079042597 } };
    double[][] kff = new double[][] { { 12.525506544650831, 6.271533487190302 } };
    double[][] L = new double[][] { { 0.999999555555966 }, { 0.0 } };
    // Ks is -0.029043735507692382
    // Kv is 7.065192548457451
    // Ka is 0.00019782692307692307

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
    setDefaultCommand(new MoveElevSetpoint(this, this.oi));
  }

  public double getMotorVoltage() {
    return this.motors[0].get() * this.motors[0].getBusVoltage();
  }

  public double getMotorCurrent() {
    return this.motors[0].getOutputCurrent();
  }

  @Override
  public void periodic() {
    if (RobotState.isEnabled()) {
      this.logger.log();
    }

    NetworkTableEntry positionEntry = table.getEntry("Position");
    positionEntry.setNumber(this.getPosition());
    NetworkTableEntry enabledEntry = table.getEntry("SS_Enabled");
    enabledEntry.setBoolean(!this.isDisabled());
    NetworkTableEntry targetPositionEntry = table.getEntry("TargetPosition");
    targetPositionEntry.setNumber(this.getTargetPosition());
    NetworkTableEntry voltageEntry = table.getEntry("Voltage");
    voltageEntry.setNumber(this.getMotorVoltage());
    NetworkTableEntry currentEntry = table.getEntry("Current");
    currentEntry.setNumber(this.getMotorCurrent());
  }

}
