package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Climber extends Subsystem{
    DoubleSolenoid rightFrontSolenoid;
    DoubleSolenoid leftFrontSolenoid;
    DoubleSolenoid backSolenoid;
    WPI_VictorSPX driveMotor;


    public Climber() {
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
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void liftRobot(){
        backSolenoid.set(Value.kForward);
        rightFrontSolenoid.set(Value.kForward);
        leftFrontSolenoid.set(Value.kForward);
    } 

    public void moveForward(){
        driveMotor.set(1);
    }

    public void stopMotor(){
        driveMotor.set(0);
    }

    public void liftFront(){
        rightFrontSolenoid.set(Value.kReverse);
        leftFrontSolenoid.set(Value.kReverse);
    }
    
    public void liftBack(){
        backSolenoid.set(Value.kReverse);
    }

}