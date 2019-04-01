package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Elevator;

public class MoveElevSetpoint extends Command {

    public Elevator elevator;
    public OI oi;

    public MoveElevSetpoint(Elevator elevator, OI oi) {
        this.elevator = elevator;
        this.oi = oi;
        requires(elevator);
    }

    @Override
    public void initialize() {
        if(this.elevator.isDisabled()) {
            this.elevator.setDisabled(false);
        }
    }

    @Override
    public void execute() {
        double rawAxisOutput = this.oi.getElevatorRawAxisOutput();
        if(rawAxisOutput > -0.1 && rawAxisOutput < 0.1) {
            rawAxisOutput = 0.0;
        }
        double metersPerSec = 1.3;
        double pos = this.elevator.getTargetPosition();
        pos += (rawAxisOutput * metersPerSec) * 0.02;
        this.elevator.setTargetPosition(pos);
    }

    protected boolean isFinished() {
        //return isFinished();
        return false;
    }
}
