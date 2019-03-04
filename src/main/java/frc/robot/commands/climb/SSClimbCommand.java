package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.TrapezoidalProfiler;
import frc.robot.subsystems.Climber;

public class SSClimbCommand extends Command {
    Climber subsystem;
    OI oi;
    TrapezoidalProfiler profiler;

    public SSClimbCommand(Climber subsystem, OI oi) {
        this.subsystem = subsystem;
        this.oi = oi;
        requires(subsystem);
        subsystem.reset();
        profiler = new TrapezoidalProfiler(6, 36, 0, 0.1, 0);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        profiler.calculate_new_velocity(profiler.getCurrentPosition(), 0.02);
        this.subsystem.setTargetPosition(profiler.getCurrentPosition());
        this.subsystem.calculateSSC();

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
