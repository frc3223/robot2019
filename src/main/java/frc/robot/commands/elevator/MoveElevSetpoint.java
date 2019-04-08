package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.subsystems.Elevator;

public class MoveElevSetpoint extends Command {

    public Elevator elevator;
    public OI oi;
    public boolean doneUp;

    public MoveElevSetpoint(Elevator elevator, OI oi) {
        this.elevator = elevator;
        this.oi = oi;
        requires(elevator);
        doneUp = false;
    }

    @Override
    public void initialize() {
        if(this.elevator.isDisabled()) {
            this.elevator.setDisabled(false);
        }
    }

    @Override
    public void execute() {
        //System.out.println("current pos is:" + elevator.getCurrentPosition());
        //System.out.println("current setpoint is:" + elevator.getTargetPosition());
        double rawAxisOutput = this.oi.getElevatorRawAxisOutput();
        if(rawAxisOutput > -0.1 && rawAxisOutput < 0.1) {
            rawAxisOutput = 0.0;
        }
        double metersPerSec = 1.6;
        if(rawAxisOutput > 0) {
            metersPerSec = 0.9;
        }
        double pos = this.elevator.getTargetPosition();
        pos += (rawAxisOutput * metersPerSec) * 0.02;
        this.elevator.setTargetPosition(pos);
        if(oi.manipulatorController.getRawButton(5) && !doneUp){
            this.elevator.goUpPosition();
            doneUp = true;
        }else if(!oi.manipulatorController.getRawButton(5)){
            doneUp = false;
        }else if(oi.manipulatorController.getRawButtonPressed(RobotMap.MANIPULATOR_CONTROLLER_JOYSTICK_PRESS)){
            this.elevator.stopAtCurrentPos();
        }
    }

    public boolean isFinished() {
        return false;
    }

    public void end(){

    }
}
