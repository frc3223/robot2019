/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.StateSpaceController;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static enum ElevatorLevels {
      BOTTOM, MIDDLE, TOP
  }

  public StateSpaceController stateSpaceController;
  public ElevatorLevels elevatorLevel = ElevatorLevels.BOTTOM;
  public Notifier notifier;
  public WPI_TalonSRX[] talons;
  public boolean isDisabled = false;
  public Elevator() {
      talons = new WPI_TalonSRX[] {
        new WPI_TalonSRX(RobotMap.DRIVETRAIN_ELEVATOR_TALON)
      };
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

  //For when the driver wants to manually control the elevator level
  public void moveElevator() {

  }

  public void maintainLevel() {

  }

  public void setDisabled(boolean input) {
    this.isDisabled = input;
  }

  public void calculate() {
    if(!this.isDisabled){
        stateSpaceController.update();
        stateSpaceController.setInput((r) -> {
            //Target position (meter)
            r.set(0, 0, 1);
            //Target velocity (meters/second)
            r.set(1, 0, 0);
        });
        double voltage = stateSpaceController.u.get(0, 0);
        for(int i = 0; i < talons.length; i++){
            double availableVolt = talons[i].getBusVoltage();
            double percentVolt = voltage / availableVolt;
            talons[i].set(percentVolt);
        }
        stateSpaceController.setOutput((y) -> {
            //Position in meters
            y.set(0, 0, stateSpaceController.y_est.get(0, 0));
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
  }
}
