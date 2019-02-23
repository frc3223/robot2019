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
import frc.robot.commands.ClimberTest;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber extends Subsystem{
    WPI_VictorSPX driveMotor;
    //Compressor c;

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
    double winchRadius = 0.5625; // inch
    int ticksPerRev = 42;

    public Climber(OI oi) {
        Ng = 10;
        inst = NetworkTableInstance.getDefault();
        this.oi = oi;
        table = inst.getTable("Stilts");
        this.rightFrontMotor = new CANSparkMax(RobotMap.STILTS_RIGHT_FRONT_CAN,MotorType.kBrushless);
        this.rightFrontMotor.getEncoder().setPositionConversionFactor(winchRadius * 2 * Math.PI / Ng);
        this.leftFrontMotor = new CANSparkMax(RobotMap.STILTS_LEFT_FRONT_CAN,MotorType.kBrushless);
        this.leftFrontMotor.getEncoder().setPositionConversionFactor(winchRadius * 2 * Math.PI / Ng);
        this.rightBackMotor = new CANSparkMax(RobotMap.STILTS_RIGHT_BACK_CAN,MotorType.kBrushless);
        this.rightBackMotor.getEncoder().setPositionConversionFactor(winchRadius * 2 * Math.PI / Ng);
        this.leftBackMotor = new CANSparkMax(RobotMap.STILTS_LEFT_BACK_CAN,MotorType.kBrushless);
        this.leftBackMotor.getEncoder().setPositionConversionFactor(winchRadius * 2 * Math.PI / Ng);
        driveMotor= new WPI_VictorSPX(RobotMap.CLIMBER_DRIVE_MOTOR);
        
        //c = new Compressor(RobotMap.PNEUMATICS_MODULE);
        //c.setClosedLoopControl(true);
        
    }
    // post if its okay to move forward & solenoid pressure
    // accelerometer
    // controls
    // ask about encoders??

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimberTest(this,this.oi));
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
        return ticks;
    }

    public void reset() {
        rightBackMotor.getEncoder().setPosition(0);
        rightFrontMotor.getEncoder().setPosition(0);
        leftFrontMotor.getEncoder().setPosition(0);
        leftBackMotor.getEncoder().setPosition(0);
    }

    public double conversionFactor() {
        return rightBackMotor.getEncoder().getPositionConversionFactor();
    }
    
    public double rightBackPosition(){
        return convert(this.rightBackMotor.getEncoder().getPosition());
    }
    public double rightBackTicks() {
        return this.rightBackMotor.getEncoder().getPosition();
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
}