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
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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

    CANSparkMax rightFrontMotor;
    CANSparkMax leftFrontMotor;
    CANSparkMax rightBackMotor;
    CANSparkMax leftBackMotor;

    NetworkTableEntry leftLimit;
    NetworkTableEntry rightLimit;
    int Ng;
    double winchRadius = 1; // inch
    int ticksPerRev = 42;

    public Climber(OI oi) {
        inst = NetworkTableInstance.getDefault();
        this.oi = oi;
        table = inst.getTable("Stilts");
        this.rightFrontMotor = new CANSparkMax(RobotMap.STILTS_RIGHT_FRONT_CAN,MotorType.kBrushless);
        this.leftFrontMotor = new CANSparkMax(RobotMap.STILTS_LEFT_FRONT_CAN,MotorType.kBrushless);
        this.rightBackMotor = new CANSparkMax(RobotMap.STILTS_RIGHT_BACK_CAN,MotorType.kBrushless);
        this.leftBackMotor = new CANSparkMax(RobotMap.STILTS_LEFT_BACK_CAN,MotorType.kBrushless);
        driveMotor= new WPI_VictorSPX(RobotMap.CLIMBER_DRIVE_MOTOR);
        Ng = 10;
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

    public void moveDown(int distance){
        rightBackMotor.set(1);
        rightFrontMotor.set(1);
        leftFrontMotor.set(1);
        leftBackMotor.set(1);
    }
    public void moveUp(){
        rightBackMotor.set(-1);
        rightFrontMotor.set(-1);
        leftFrontMotor.set(-1);
        leftBackMotor.set(-1);
    }
    public void moveRightBack(double speed){
        rightBackMotor.set(speed);
    }
    public void moveLeftBack(double speed){
        leftBackMotor.set(speed);
    }
    public void moveRightFront(double speed){
        rightFrontMotor.set(speed);
    }
    public void moveLeftFront(double speed){
        leftFrontMotor.set(speed);
    }

    public double convert(double ticks){
        return (ticks/ticksPerRev)*(Ng)*(2*Math.PI*winchRadius);
    }
    public double rightBackPosition(){
        return convert(this.rightBackMotor.getEncoder().getPosition());
    }
    public double rightFrontPosition(){
        return convert(this.rightFrontMotor.getEncoder().getPosition());
    }
    public double leftBackPosition(){
        return convert(this.leftBackMotor.getEncoder().getPosition());
    }
    public double leftFrontPosition(){
        return convert(this.leftFrontMotor.getEncoder().getPosition());
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