package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.TrapezoidalProfiler;
import frc.robot.subsystems.Climber;

public class SSClimbCommand extends Command {
    enum State {
        Raising,
        FrontRetracting,
        BackRetracting
    }

    Climber subsystem;
    OI oi;
    State state;

    public SSClimbCommand(Climber subsystem, OI oi) {
        this.subsystem = subsystem;
        this.oi = oi;
        requires(subsystem);
        subsystem.reset();
    }

    @Override
    protected void initialize() {
        state = State.Raising;

    }

    @Override
    protected void execute() {
        int pov = this.oi.driverController.getPOV();
        boolean shouldRaise = pov == 0;
        boolean shouldLower = pov == 180;
        if(state == State.Raising) {
            if(shouldRaise) {
                subsystem.raiseTargetPosition();
            }else if(shouldLower){
                subsystem.lowerTargetPosition();
            }

            boolean shouldGotoFrontRetracting = this.oi.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_FRONT);
            if(shouldGotoFrontRetracting) {
                state = State.FrontRetracting;
            }
        }else if(state == State.FrontRetracting) {
            if(shouldRaise) {
                subsystem.raiseFrontTargetPosition();
            }else if(shouldLower){
                subsystem.lowerFrontTargetPosition();
            }

            boolean shouldGotoBackRetracting = this.oi.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_BACK);
            if(shouldGotoBackRetracting) {
                state = State.BackRetracting;
            }
        }else if(state == State.BackRetracting) {
            if(shouldRaise) {
                subsystem.raiseBackTargetPosition();
            }else if(shouldLower){
                subsystem.lowerBackTargetPosition();
            }
        }
        this.subsystem.calculateSSC();

        boolean forward = this.oi.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_FORWARD);
        boolean backward = this.oi.driverController.getRawButton(RobotMap.DRIVER_CONTROLLER_LIFT_BACKWARD);
        if(forward){
            this.subsystem.move(0.5);
        }else if(backward){
            this.subsystem.move(-0.5);
        }else{
            this.subsystem.stopMotor();
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
