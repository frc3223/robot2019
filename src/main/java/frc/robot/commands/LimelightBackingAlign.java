import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LimeLight;

public class LimelightBackingAlign extends Command {
    LimeLight limelight;
    Drivetrain drivetrain;

    double p = 0.1;
    boolean isAligned = false;
    boolean shouldAbort = false;

    public LimelightBackingAlign() {
        this.limelight = (Robot.m_limelight);
        this.drivetrain = Robot.m_drivetrain;
    }

    @Override
    public void initialize() {
        int tv = limelight.getValidTarget();
        if (tv != 1) { // If the target is not valid
            shouldAbort = true;
        }
    }

    @Override
    public void execute() {
        int tv = limelight.getValidTarget();
        if (tv == 1) { // If the target is valid
            double tx = limelight.getHorizontalOffset();
            if (Math.abs(tx) < 1.0) {
                isAligned = true;
                tx = 0;
            }
            drivetrain.arcadeDrive(0.3, p * tx);
        }
    }

    @Override
    public boolean isFinished() {
        boolean result = shouldAbort || isAligned;
        shouldAbort = false;
        isAligned = false;
        return result;
    }

    @Override
    public void end() {
        drivetrain.stop();
    }
}