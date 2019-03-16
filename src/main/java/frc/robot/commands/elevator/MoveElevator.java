package frc.robot.commands.elevator;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.TrapezoidalProfiler;
import frc.robot.subsystems.Elevator;

public class MoveElevator extends Command {

    public Elevator elevator;
    public OI oi;
    public TrapezoidalProfiler profiler;

    public MoveElevator(Elevator elevator, OI oi, double targetPos) {
        this.requires(elevator);
        this.elevator = elevator;
        this.oi = oi;
        this.profiler = new TrapezoidalProfiler(1.4,10.0,targetPos,0.001,0);
    }

    @Override
    protected boolean isFinished() {
        return this.profiler.isFinished(this.profiler.getCurrentPosition());
    }

    @Override
    public void initialize() {
        if(this.elevator.isDisabled()) {
            this.elevator.setDisabled(false);
        }
    }

    @Override
    public void execute() {
        this.profiler.calculate_new_velocity(profiler.getCurrentPosition(), 0.02);
        this.elevator.setTargetPosition(profiler.getCurrentPosition());
        this.elevator.setTargetVelocity(this.profiler.getCurrentTargetVelocity());
    }
}
