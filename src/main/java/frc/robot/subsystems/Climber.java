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

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("Climber");

    public Climber(OI oi) {
        Ng = 10;
        inst = NetworkTableInstance.getDefault();
        this.oi = oi;
        this.rightFrontMotor = new CANSparkMax(RobotMap.STILTS_RIGHT_FRONT_CAN,MotorType.kBrushless);
        this.rightFrontMotor.getEncoder().setPositionConversionFactor(winchRadius * 2 * Math.PI / Ng);
        this.leftFrontMotor = new CANSparkMax(RobotMap.STILTS_LEFT_FRONT_CAN,MotorType.kBrushless);
        this.leftFrontMotor.getEncoder().setPositionConversionFactor(winchRadius * 2 * Math.PI / Ng);
        this.rightBackMotor = new CANSparkMax(RobotMap.STILTS_RIGHT_BACK_CAN,MotorType.kBrushless);
        this.rightBackMotor.getEncoder().setPositionConversionFactor(winchRadius * 2 * Math.PI / Ng);
        this.leftBackMotor = new CANSparkMax(RobotMap.STILTS_LEFT_BACK_CAN,MotorType.kBrushless);
        this.leftBackMotor.getEncoder().setPositionConversionFactor(winchRadius * 2 * Math.PI / Ng);
        driveMotor= new WPI_VictorSPX(RobotMap.CLIMBER_DRIVE_MOTOR);

        inst = NetworkTableInstance.getDefault();
        table = inst.getTable("Climber");
        this.leftBackMotor.setInverted(false);
        this.leftFrontMotor.setInverted(false);
        this.rightFrontMotor.setInverted(false);
        this.rightBackMotor.setInverted(false);
        
    }

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

    @Override
    public void periodic(){
        NetworkTableEntry rightBackMotor;
        rightBackMotor = table.getEntry("RightBack_Position");
        rightBackMotor.setNumber(rightBackPosition());

        NetworkTableEntry rightFrontMotor;
        rightFrontMotor = table.getEntry("RightFront_Position");
        rightFrontMotor.setNumber(rightFrontPosition());

        NetworkTableEntry leftBackMotor;
        leftBackMotor = table.getEntry("LeftBack_Position");
        leftBackMotor.setNumber(leftBackPosition());

        NetworkTableEntry leftFrontMotor;
        leftFrontMotor = table.getEntry("LeftFront_Position");
        leftFrontMotor.setNumber(leftFrontPosition());

        NetworkTableEntry rightBackCurrent;
        rightBackCurrent = table.getEntry("RightBack_Current");
        rightBackCurrent.setNumber(this.rightBackMotor.getOutputCurrent());

        NetworkTableEntry rightFrontCurrent;
        rightFrontCurrent = table.getEntry("RightFront_Current");
        rightFrontCurrent.setNumber(this.rightFrontMotor.getOutputCurrent());

        NetworkTableEntry leftBackCurrent;
        leftBackCurrent = table.getEntry("leftBack_Current");
        leftBackCurrent.setNumber(this.leftBackMotor.getOutputCurrent());

        NetworkTableEntry leftFrontCurrent;
        leftFrontCurrent = table.getEntry("LeftFront_Current");
        leftFrontCurrent.setNumber(this.leftFrontMotor.getOutputCurrent());

        NetworkTableEntry rightBackVoltage;
        rightBackVoltage = table.getEntry("RightBack_Voltage");
        rightBackVoltage.setNumber(this.rightBackMotor.get()*this.rightBackMotor.getBusVoltage());

        NetworkTableEntry rightFrontVoltage;
        rightFrontVoltage = table.getEntry("RightFront_Voltage");
        rightFrontVoltage.setNumber(this.rightFrontMotor.get()*this.rightFrontMotor.getBusVoltage());

        NetworkTableEntry leftBackVoltage;
        leftBackVoltage = table.getEntry("RightBack_Voltage");
        leftBackVoltage.setNumber(this.leftBackMotor.get()*this.leftBackMotor.getBusVoltage());

        NetworkTableEntry leftFrontVoltage;
        leftFrontVoltage = table.getEntry("LeftFront_Voltage");
        leftFrontVoltage.setNumber(this.leftFrontMotor.get()*this.leftFrontMotor.getBusVoltage());
    }
}