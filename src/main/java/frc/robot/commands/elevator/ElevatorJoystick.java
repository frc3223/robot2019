package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.subsystems.Elevator;

public class ElevatorJoystick extends Command {

    public double rawAxisOutput;
    public Elevator elevator;
    public OI oi;

    public ElevatorJoystick(Elevator elevator, OI oi) {
        this.requires(elevator);
        this.elevator = elevator;
        this.oi = oi;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public void initialize() {
        this.elevator.setDisabled(true);
    }

    public void execute() {
        this.rawAxisOutput = this.oi.manipulatorController.getRawAxis(RobotMap.MANIPULATOR_CONTROLLER_ELEVATOR);
        epsilonCheck();
        if(this.rawAxisOutput == 0) {
            //this.elevator.maintainLevel();
            this.elevator.moveElevator(0);
            
           // System.out.println("explode " + 0);
            //System.out.println("maintain!");
        }
        else {
            this.elevator.moveElevator(rawAxisOutput);
           // System.out.println("explode " + rawAxisOutput);
        }
    }

    public void epsilonCheck() {
        double epsilon = .1;
        if(this.rawAxisOutput > -epsilon && this.rawAxisOutput < epsilon) {
            this.rawAxisOutput = 0;
        }
    }

    public void end() {
        //this.elevator.setDisabled(false);
    }

}