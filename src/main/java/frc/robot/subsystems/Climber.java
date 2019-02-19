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
import frc.robot.OI;
import frc.robot.commands.ClimberMove;

public class Climber extends Subsystem{
    DoubleSolenoid frontSolenoid;
    DoubleSolenoid backSolenoid;
    WPI_VictorSPX driveMotor;
    Compressor c;
    DigitalInput limitSwitchR;
    DigitalInput limitSwitchL;

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("Stilts");
    OI oi;

    NetworkTableEntry leftLimit;
    NetworkTableEntry rightLimit;

    public Climber(OI oi) {
        inst = NetworkTableInstance.getDefault();
        this.oi = oi;
        table = inst.getTable("Stilts");
        leftLimit = table.getEntry("Left Limit Switch");
        rightLimit = table.getEntry("Right Limit Switch");
        limitSwitchL = new DigitalInput(RobotMap.LIMIT_SWITCH_LEFT);
        limitSwitchR = new DigitalInput(RobotMap.LIMIT_SWITCH_RIGHT);
        frontSolenoid = new DoubleSolenoid(
            RobotMap.PNEUMATICS_MODULE,
            RobotMap.CLIMBER_SIDES_FRONT_CYLINDER_UP, 
            RobotMap.CLIMBER_SIDES_FRONT_CYLINDER_DOWN);
        backSolenoid = new DoubleSolenoid(
            RobotMap.PNEUMATICS_MODULE,
            RobotMap.CLIMBER_BACK_CYLINDER_UP, 
            RobotMap.CLIMBER_BACK_CYLINDER_DOWN);
        driveMotor= new WPI_VictorSPX(RobotMap.CLIMBER_DRIVE_MOTOR);

        c = new Compressor(RobotMap.PNEUMATICS_MODULE);
        c.setClosedLoopControl(true);
        
    }
    // post if its okay to move forward & solenoid pressure
    // accelerometer
    // controls
    // ask about encoders??

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimberMove(this,this.oi));
    }

    public void liftRobot(){
        backSolenoid.set(Value.kForward);
        frontSolenoid.set(Value.kForward);
    } 

    public void move(double speed){
        driveMotor.set(speed);
    }

    public void stopMotor(){
        driveMotor.set(0);
    }

    public void liftFront(){
        frontSolenoid.set(Value.kReverse);
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