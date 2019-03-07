package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;

public class SSClimbCommand extends Command {
    enum StiltControlState {
        All,
        Front,
        Back
    }

    Climber subsystem;
    OI oi;
    StiltControlState state;

    public SSClimbCommand(Climber subsystem, OI oi) {
        this.subsystem = subsystem;
        this.oi = oi;
        requires(subsystem);
        subsystem.reset();
    }

    @Override
    protected void initialize() {
        state = StiltControlState.All;
    }

    @Override
    protected void execute() {
        this.raiseLowerStilts();
        this.subsystem.calculateSSC();
        this.driveStilts();
        this.doTransitions();
    }

    private void raiseLowerStilts() {
        boolean shouldRaise = this.oi.shouldRaiseStilts();
        boolean shouldLower = this.oi.shouldLowerStilts();
        if(state == StiltControlState.All) {
            if(shouldRaise) {
                subsystem.raiseTargetPosition();
            }else if(shouldLower){
                subsystem.lowerTargetPosition();
            }

        }else if(state == StiltControlState.Front) {
            if(shouldRaise) {
                subsystem.raiseFrontTargetPosition();
            }else if(shouldLower){
                subsystem.lowerFrontTargetPosition();
            }
        }else if(state == StiltControlState.Back) {
            if(shouldRaise) {
                subsystem.raiseBackTargetPosition();
            }else if(shouldLower){
                subsystem.lowerBackTargetPosition();
            }
        }
    }

    private void driveStilts() {
        boolean forward = this.oi.shouldDriveStiltsForward();
        boolean backward = this.oi.shouldDriveStiltsBackward();
        if(forward){
            this.subsystem.move(0.5);
        }else if(backward){
            this.subsystem.move(-0.5);
        }else{
            this.subsystem.stopMotor();
        }
    }
    
    private void doTransitions() {
        boolean shouldGotoControlAll= this.oi.shouldControlAllStilts();
        boolean shouldGotoControlFront = this.oi.shouldControlFrontStilts();
        boolean shouldGotoControlBack = this.oi.shouldControlBackStilts();
        if(shouldGotoControlAll) {
            state = StiltControlState.All;
        }else if(shouldGotoControlFront) {
            state = StiltControlState.Front;
        }else if (shouldGotoControlBack) {
            state = StiltControlState.Back;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
