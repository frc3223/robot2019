/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class LimeLightHatchDeploy extends CommandGroup {
  Drivetrain subsystem;
  OI oi;
  /**
   * Add your docs here.
   */
  public LimeLightHatchDeploy(Drivetrain subsystem,OI oi) {
    this.subsystem = subsystem;
    this.oi = oi;
    addSequential(new LimeLightLEDOn());
    addSequential(new WaitCommand(0.2));
    addSequential(new LimeLightAlign());
    addSequential(new SlideOut(Robot.m_galaga,Robot.m_oi));
    addSequential(new WaitCommand(0.2));
    addSequential(new GalagaIn(Robot.m_galaga,Robot.m_oi));      
    addSequential(new WaitCommand(0.2));
    addSequential(new SlideIn(Robot.m_galaga,Robot.m_oi));
    
    
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
