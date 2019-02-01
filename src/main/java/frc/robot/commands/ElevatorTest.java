package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DataLogger;
import frc.robot.subsystems.Elevator;

public class ElevatorTest extends Command {

    public Elevator elevator;
    public int switchToDown = 0;
    public double increment = 0;
    public DataLogger logger;

    public ElevatorTest(Elevator elevator) {
        this.requires(elevator);
        this.elevator = elevator;
        this.logger = new DataLogger("ElevatorTestCommand");

        this.logger.add("Voltage (Battery)", () -> {
            return this.elevator.getBusVoltage();
        });
        this.elevator.logMotorVoltage(this.logger);
        this.elevator.logMotorCurrent(this.logger);
        this.logger.add("Velocity (m/s)", () -> {
            return this.elevator.getVelocity();
        });
        this.logger.add("Position (m)", () -> {
            return this.elevator.getPosition();
        });
    }

    @Override
    public void execute() {
        switch(this.switchToDown) {
            case 0:
                this.increment = 0.2;
                if(this.elevator.getPosition() >= 1) {
                    this.switchToDown = 1;
                }
                break;
            case 1:
                this.increment = -0.2;
                if(this.elevator.getPosition() >= -0.01 && this.elevator.getPosition() <= 0.01) {
                    this.switchToDown = 2;
                }
                break;
            case 2:
                this.increment = 0;
                break;
            default:
                this.increment = 0;
                System.out.println("this.switchToDown does not equal 0, 1, or 2, but equals " + this.switchToDown);
                break;
        }

        this.elevator.moveElevator(this.increment);
    }

    @Override
    public void end() {
        this.elevator.moveElevator(0);
    }

    @Override
    protected boolean isFinished() {
        if(this.switchToDown != 2) {
            return false;
        }
        else {
            return true;
        }
    }
}