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
    CANSparkMax backMotor;

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
        this.leftFrontMotor = new CANSparkMax(RobotMap.STILTS_LEFT_FRONT_CAN,MotorType.kBrushless);
        this.backMotor = new CANSparkMax(RobotMap.STILTS_BACK_CAN,MotorType.kBrushless);
        driveMotor= new WPI_VictorSPX(RobotMap.CLIMBER_DRIVE_MOTOR);

        inst = NetworkTableInstance.getDefault();
        table = inst.getTable("Climber");
        this.leftFrontMotor.setInverted(false);
        this.rightFrontMotor.setInverted(false);
        this.backMotor.setInverted(false);
        
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimberTest(this,this.oi));
    }

    public void liftRobot(){
    } 
    public void maintainStiltLevel() {
        double stiltStall= 0.1;
        this.leftFrontMotor.set(stiltStall);
        this.rightFrontMotor.set(stiltStall);
        this.backMotor.set(stiltStall);
      }
    public void moveDown(int distance){
        this.backMotor.set(0.5);
        this.rightFrontMotor.set(0.5);
        this.leftFrontMotor.set(0.5);

    }
    public void moveUp(){
        this.backMotor.set(-0.5);
        this.rightFrontMotor.set(-0.5);
        this.leftFrontMotor.set(-0.5);
    }
    public void liftFront(){
        this.rightFrontMotor.set(-0.5);
        this.leftFrontMotor.set(-0.5);
    }
    public void liftBack(){
        this.backMotor.set(-0.5);
    }

    public void moveBack(double speed){
        backMotor.set(speed);
    }
    public void moveRightFront(double voltage){
        this.rightFrontMotor.set(voltage);
    }
    public void moveLeftFront(double voltage){
        this.leftFrontMotor.set(voltage);
    }

    public double convert(double ticks){
        return ticks;
    }

    public void reset() {
        backMotor.getEncoder().setPosition(0);
        rightFrontMotor.getEncoder().setPosition(0);
        leftFrontMotor.getEncoder().setPosition(0);
    }

    public double conversionFactor() {
        return backMotor.getEncoder().getPositionConversionFactor();
    }
    
    public double backPosition(){
        return convert(this.backMotor.getEncoder().getPosition());
    }
    public double backTicks() {
        return this.backMotor.getEncoder().getPosition();

    }
    public double rightFrontPosition(){
        return convert(this.rightFrontMotor.getEncoder().getPosition());
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
        NetworkTableEntry backMotor;
        backMotor = table.getEntry("Back_Position");
        backMotor.setNumber(backPosition());

        NetworkTableEntry rightFrontMotor;
        rightFrontMotor = table.getEntry("RightFront_Position");
        rightFrontMotor.setNumber(rightFrontPosition());

        NetworkTableEntry leftFrontMotor;
        leftFrontMotor = table.getEntry("LeftFront_Position");
        leftFrontMotor.setNumber(leftFrontPosition());

        NetworkTableEntry backCurrent;
        backCurrent = table.getEntry("Back_Current");
        backCurrent.setNumber(this.backMotor.getOutputCurrent());

        NetworkTableEntry rightFrontCurrent;
        rightFrontCurrent = table.getEntry("RightFront_Current");
        rightFrontCurrent.setNumber(this.rightFrontMotor.getOutputCurrent());

        NetworkTableEntry leftFrontCurrent;
        leftFrontCurrent = table.getEntry("LeftFront_Current");
        leftFrontCurrent.setNumber(this.leftFrontMotor.getOutputCurrent());

        NetworkTableEntry backVoltage;
        backVoltage = table.getEntry("Back_Voltage");
        backVoltage.setNumber(this.backMotor.get()*this.backMotor.getBusVoltage());

        NetworkTableEntry rightFrontVoltage;
        rightFrontVoltage = table.getEntry("RightFront_Voltage");
        rightFrontVoltage.setNumber(this.rightFrontMotor.get()*this.rightFrontMotor.getBusVoltage());


        NetworkTableEntry leftFrontVoltage;
        leftFrontVoltage = table.getEntry("LeftFront_Voltage");
        leftFrontVoltage.setNumber(this.leftFrontMotor.get()*this.leftFrontMotor.getBusVoltage());
    }
}