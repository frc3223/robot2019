package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Climber extends Subsystem{
    DoubleSolenoid rightFrontSolenoid;
    DoubleSolenoid leftFrontSolenoid;
    DoubleSolenoid backSolenoid;
    WPI_VictorSPX driveMotor;

    Compressor c;

    DigitalInput limitSwitchR;
    DigitalInput limitSwitchL;

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("Stilts");

    NetworkTableEntry leftLimit;
    NetworkTableEntry rightLimit;

    public Climber() {
        inst = NetworkTableInstance.getDefault();
        table = inst.getTable("Stilts");
        leftLimit = table.getEntry("Left Limit Switch");
        rightLimit = table.getEntry("Right Limit Switch");
        limitSwitchL = new DigitalInput(RobotMap.LIMIT_SWITCH_LEFT);
        limitSwitchR = new DigitalInput(RobotMap.LIMIT_SWITCH_RIGHT);
        rightFrontSolenoid= new DoubleSolenoid(
            RobotMap.CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL1, 
            RobotMap.CLIMBER_RIGHT_FRONT_CYLINDER_CHANNEL2);
        leftFrontSolenoid= new DoubleSolenoid(
            RobotMap.CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL1, 
            RobotMap.CLIMBER_LEFT_FRONT_CYLINDER_CHANNEL2);
        backSolenoid= new DoubleSolenoid(
            RobotMap.CLIMBER_BACK_CYLINDER_CHANNEL1, 
            RobotMap.CLIMBER_BACK_CYLINDER_CHANNEL2);
        driveMotor= new WPI_VictorSPX(RobotMap.CLIMBER_DRIVE_MOTOR);

        c = new Compressor(RobotMap.compressorIndex);
        c.setClosedLoopControl(true);
    }
    // post if its okay to move forward & solenoid pressure
    // accelerometer
    // controls
    // ask about encoders??

    @Override
    protected void initDefaultCommand() {

    }

    public void liftRobot(){
        backSolenoid.set(Value.kForward);
        rightFrontSolenoid.set(Value.kForward);
        leftFrontSolenoid.set(Value.kForward);
    } 

    public void moveForward(){
        driveMotor.set(0.5);
    }

    public void stopMotor(){
        driveMotor.set(0);
    }

    public void moveBackward(){driveMotor.set(-0.5);}

    public void liftFront(){
        rightFrontSolenoid.set(Value.kReverse);
        leftFrontSolenoid.set(Value.kReverse);
        //if isleft && isright up, then post to networktables
    }
    
    public void liftBack(){
        backSolenoid.set(Value.kReverse);

    }

    public boolean isLeftUp(){
        if(limitSwitchL.get()){
            leftLimit.setBoolean(true);
            return true;
        }else {
            leftLimit.setBoolean(false);
            return false;
        }
    }

    public boolean isRightUp(){
        if (limitSwitchR.get()){
            rightLimit.setBoolean(true);
            return true;
        }else{
            rightLimit.setBoolean(false);
            return false;
        }
    }

    public boolean areBothUp(){
        if(isLeftUp() && isRightUp()){
            return true;
        }else return false;
    }



}