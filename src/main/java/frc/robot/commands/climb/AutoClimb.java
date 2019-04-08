/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.DriveForwardFor;

public class AutoClimb extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoClimb() {
    addSequential(new ForwardToWall());
    addSequential(new DeployAllStilts());//lifts robot until fully up
    addSequential(new ForwardTilFront());
    addSequential(new ClimberFrontUp(Robot.m_climber, Robot.m_oi));//raise front stilts
    addSequential(new ForwardTilCaster());
    addSequential(new ClimberBackUp(Robot.m_climber, Robot.m_oi));//raise back stilt
    addSequential(new DriveForwardFor(0.5, Robot.m_drivetrain, Robot.m_oi));
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
