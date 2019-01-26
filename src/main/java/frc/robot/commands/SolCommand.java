package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.google.inject.Inject;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Solenoids;
import frc.robot.OI;

public class SolCommand extends Command{
    Solenoids subsystem;
    OI oi;

    @Inject
    public SolCommand(Solenoids subsystem, OI joystick) {
        // Use requires() here to declare subsystem dependencies
        requires(subsystem);
        this.subsystem = subsystem;
        this.oi = joystick;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
       boolean a = oi.getJoystck().getRawButton(1);
       if(a) {
           this.subsystem.pressForward();
       }else{
           this.subsystem.turnOff();
       }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
