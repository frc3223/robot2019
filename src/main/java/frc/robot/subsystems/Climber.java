package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.OI;
import frc.robot.StiltStateSpaceController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.commands.climb.SSClimbCommand;

import java.util.function.Consumer;

public class Climber extends Subsystem{
    WPI_VictorSPX driveMotor;

    OI oi;

    CANSparkMax rightFrontMotor;
    CANSparkMax leftFrontMotor;
    CANSparkMax backMotor;

    StiltStateSpaceController backSSC;
    StiltStateSpaceController rightFrontSSC;
    StiltStateSpaceController leftFrontSSC;

    public double rightFrontTargetPositionInches = 0;
    double leftFrontTargetPositionInches = 0;
    double backTargetPositionInches = 0;

    int Ng;
    double winchRadius = 0.5625; // inch
    int ticksPerRev = 42;

    NetworkTableInstance inst;
    NetworkTable table;
    DataLogger logger;

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

        this.leftFrontMotor.getEncoder().setPositionConversionFactor(2 * Math.PI * winchRadius / Ng);
        this.rightFrontMotor.getEncoder().setPositionConversionFactor(2 * Math.PI * winchRadius / Ng);
        this.backMotor.getEncoder().setPositionConversionFactor(2 * Math.PI * winchRadius / Ng);

        this.logger = new DataLogger("Climb");
        this.initLogger(this.logger);


        this.backSSC = new StiltStateSpaceController(-0.8, 0);
        this.leftFrontSSC = new StiltStateSpaceController(0, 0);
        this.rightFrontSSC = new StiltStateSpaceController(0, 0);
    }

    public void initLogger(DataLogger logger) {
        logger.add("LF_current", () -> this.leftFrontMotor.getOutputCurrent());
        logger.add("RF_current", () -> this.rightFrontMotor.getOutputCurrent());
        logger.add("B_current", () -> this.backMotor.getOutputCurrent());
        logger.add("LF_position", () -> this.leftFrontPosition());
        logger.add("RF_position", () -> this.rightFrontPosition());
        logger.add("B_position", () -> this.backPosition());
        logger.add("LF_voltage", () -> this.leftFrontMotorVoltage());
        logger.add("RF_voltage", () -> this.rightFrontMotorVoltage());
        logger.add("B_voltage", () -> this.backMotorVoltage());
        logger.add("LF_targetPos", () -> this.leftFrontTargetPositionInches);
        logger.add("RF_targetPos", () -> this.rightFrontTargetPositionInches);
        logger.add("B_targetPos", () -> this.backTargetPositionInches);
        logger.add("LF_estPos", () -> metersToInches(this.leftFrontSSC.getEstimatedPosition()));
        logger.add("RF_estPos", () -> metersToInches(this.rightFrontSSC.getEstimatedPosition()));
        logger.add("B_estPos", () -> metersToInches(this.backSSC.getEstimatedPosition()));
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SSClimbCommand(this,this.oi));
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
        this.rightFrontMotor.set(-0.5);
        this.leftFrontMotor.set(0.5);

    }
    public void moveUp(double speed){
        this.backMotor.set(-speed);
        this.rightFrontMotor.set(speed);
        this.leftFrontMotor.set(-speed);
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
        this.leftFrontMotor.set(-voltage);
    }

    public double convert(double ticks){
        return ticks;
    }

    public void reset() {
        backMotor.getEncoder().setPosition(0);
        rightFrontMotor.getEncoder().setPosition(0);
        leftFrontMotor.getEncoder().setPosition(0);
    }

    public double backPosition(){
        return convert(this.backMotor.getEncoder().getPosition());
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

    // posInches = -3 -> lift robot 3 inches
    public void setTargetPosition(double posInches) {
        if(posInches > 0) {
            posInches = 0;
        }
        if(posInches < -20)
        {
            posInches = -20;
        }
        this.leftFrontTargetPositionInches = -posInches;
        this.rightFrontTargetPositionInches = posInches;
        System.out.println(" all pos " + posInches);
        if(posInches < -0.1) {
            posInches += 0.5; // back stilt is 1/2 inch higher off the ground
        }
        this.backTargetPositionInches = -posInches;
    }

    /**
     * as in raise stilts
     */
    public void raiseTargetPosition() {
        double pos = this.rightFrontTargetPositionInches;
        double inchPerSec = 12;
        pos += inchPerSec * 0.02;
        setTargetPosition(pos);
    }

    /**
     * as in lower stilts
     */
    public void lowerTargetPosition() {
        double pos = this.rightFrontTargetPositionInches;
        double inchPerSec = 14;
        pos -= inchPerSec * 0.02;
        setTargetPosition(pos);
    }

    public void setFrontTargetPosition(double posInches) {
        if(posInches > 0) {
            posInches = 0;
        }
        if(posInches < -20)
        {
            posInches = -20;
        }
        this.leftFrontTargetPositionInches = -posInches;
        this.rightFrontTargetPositionInches = posInches;
        System.out.println("front pos " + posInches);
    }

    /**
     * as in raise stilts
     */
    public void raiseFrontTargetPosition() {
        double pos = this.rightFrontTargetPositionInches;
        double inchPerSec = 12;
        pos += inchPerSec * 0.02;
        setFrontTargetPosition(pos);
    }

    /**
     * as in lower stilts
     */
    public void lowerFrontTargetPosition() {
        double pos = this.rightFrontTargetPositionInches;
        double inchPerSec = 14;
        pos -= inchPerSec * 0.02;
        setFrontTargetPosition(pos);
    }

    public void setBackTargetPosition(double posInches) {
        if(posInches < 0) {
            posInches = 0;
        }
        if(posInches > 20)
        {
            posInches = 20;
        }
        /*if(posInches < -0.1) {
            posInches -= 0.5; // back stilt is 1/2 inch higher off the ground
        }*/
        this.backTargetPositionInches = posInches;
        System.out.println("Back pos " + posInches);
    }

    /**
     * as in raise stilts
     */
    public void raiseBackTargetPosition() {
        double pos = this.backTargetPositionInches;
        double inchPerSec = 12;
        pos -= inchPerSec * 0.02;
        System.out.println("raise back");
        setBackTargetPosition(pos);
    }

    /**
     * as in lower stilts
     */
    public void lowerBackTargetPosition() {
        double pos = this.backTargetPositionInches;
        double inchPerSec = 14;
        pos += inchPerSec * 0.02;
        System.out.println("lower back");
        setBackTargetPosition(pos);
    }

    private double inchesToMeters(double posInches) {
        return posInches * 0.0254;
    }

    private double metersToInches(double posMeters) {
        return posMeters / 0.0254;
    }

    public void calculateSSC() {
        this.backSSC.calculate(
            inchesToMeters(this.backTargetPositionInches),
            0,
            inchesToMeters(this.backPosition()), () -> this.backMotor.getBusVoltage(),
            vp -> this.backMotor.set(vp)
        );
        this.rightFrontSSC.calculate(
            inchesToMeters(this.rightFrontTargetPositionInches),
            0,
            inchesToMeters(this.rightFrontPosition()), () -> this.rightFrontMotor.getBusVoltage(),
            vp -> this.rightFrontMotor.set(vp)
        );
        this.leftFrontSSC.calculate(
            inchesToMeters(this.leftFrontTargetPositionInches),
            0,
            inchesToMeters(this.leftFrontPosition()), () -> this.leftFrontMotor.getBusVoltage(),
            vp -> this.leftFrontMotor.set(vp)
        );

    }

    public double backMotorVoltage() {
        return this.backMotor.get()*this.backMotor.getBusVoltage();
    }

    public double rightFrontMotorVoltage() {
        return this.rightFrontMotor.get() * this.rightFrontMotor.getBusVoltage();
    }

    public double leftFrontMotorVoltage() {
        return this.leftFrontMotor.get() * this.leftFrontMotor.getBusVoltage();
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
        backVoltage.setNumber(this.backMotorVoltage());

        NetworkTableEntry rightFrontVoltage;
        rightFrontVoltage = table.getEntry("RightFront_Voltage");
        rightFrontVoltage.setNumber(this.rightFrontMotorVoltage());


        NetworkTableEntry leftFrontVoltage;
        leftFrontVoltage = table.getEntry("LeftFront_Voltage");
        leftFrontVoltage.setNumber(this.leftFrontMotorVoltage());

        if(RobotState.isEnabled()) {
            this.logger.log();
        }
    }
}