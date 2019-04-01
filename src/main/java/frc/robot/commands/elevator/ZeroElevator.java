package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.OI;
import frc.robot.subsystems.Elevator;

public class ZeroElevator extends TimedCommand {

    public Elevator elevator;
    public OI oi;

    public ZeroElevator(Elevator elevator, OI oi) {
        super(1);
        this.elevator = elevator;
        this.oi = oi;
        requires(elevator);
    }

    @Override
    protected void end() {
        elevator.reset();
    }

    @Override
    public void execute() {
        this.elevator.moveElevator(0.1);
    }
}
