package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.SolCommand;


public class Solenoids extends Subsystem{
    DoubleSolenoid sol;
    Compressor c;

    public Solenoids(){
        sol = new DoubleSolenoid(RobotMap.solChannel,RobotMap.solChannel2);
        c = new Compressor(RobotMap.compressorIndex);
        c.setClosedLoopControl(true);
    }
    public void pressForward(){
        sol.set(DoubleSolenoid.Value.kForward);
    }
    public void turnOff(){
        sol.set(DoubleSolenoid.Value.kOff);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new SolCommand(this, new OI()));
    }
    
}
