/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.elevator.MoveElevSetpoint;
import frc.robot.commands.elevator.MoveElevator;
import frc.robot.commands.elevator.ZeroElevator;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LimeLight;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.commands.ActivateLimeLight;
import frc.robot.commands.LimeLightHatchDeploy;
import frc.robot.commands.LimeLightHatchGrab;
import frc.robot.commands.LimeLightLEDOff;
import frc.robot.commands.RumbleFrontStilts;
import frc.robot.commands.climb.AutoClimb;
import frc.robot.commands.climb.StiltZero;
import frc.robot.subsystems.DataLogger;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Galaga;

import frc.robot.subsystems.Carriage;
import frc.robot.subsystems.Climber;
//import frc.robot.subsystems.PhotoSensor;
import frc.robot.commands.DetectCasterClimb;
import frc.robot.commands.DetectFrontObject;
import frc.robot.commands.RumbleFrontStilts;


import edu.wpi.first.networktables.NetworkTableEntry;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
  private ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI m_oi;

  public static Drivetrain m_drivetrain = null;
  public static LimeLight m_limelight = null;
  public static Galaga m_galaga = null;
  public static Carriage m_carriage = null;
  public static Climber m_climber = null;
  public DataLogger logger;
  public DriverStation driverStation;
  public Elevator m_elevator;
  public StiltZero m_stiltZero;
  public MoveElevator moveElevatorBottom;
  public MoveElevator moveElevatorMiddle;
  public MoveElevator moveElevatorTop;
  public ZeroElevator zeroElevator;
  //public static PhotoSensor m_photoSensor = null;

  public static DetectCasterClimb casterCommand = null;
  public static DetectFrontObject frontObjectCommand = null;
  public static RumbleFrontStilts rumbleFrontStilts = null;
  public static MoveElevSetpoint elevatorSetpoint = null;
  //public Intake m_intake;

  NetworkTableEntry xEntry;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override

  public void robotInit() {
    //Initializing stuff
    this.logger = new DataLogger("Log");
    this.driverStation = DriverStation.getInstance();

    this.logger.add("Time delta", ()-> this.getPeriod());
    this.logger.add("Voltage", ()-> RobotController.getBatteryVoltage());
    this.logger.add("Match No.", ()-> this.driverStation.getMatchNumber());

    m_drivetrain = new Drivetrain();
    //m_drivetrain.logMotorCurrents(this.logger);
    m_limelight = new LimeLight();
    m_oi = new OI();
    m_galaga = new Galaga(m_oi);
    m_carriage = new Carriage();
    //m_intake = new Intake();
    m_climber = new Climber(m_oi);
    m_elevator = new Elevator(m_oi);
    m_stiltZero = new StiltZero(m_climber);
    //m_photoSensor = new PhotoSensor();
  

    m_elevator.logEverything();

    this.moveElevatorBottom = new MoveElevator(this.m_elevator, this.m_oi, 0);
    this.moveElevatorMiddle = new MoveElevator(this.m_elevator, this.m_oi, 1);
    this.moveElevatorTop = new MoveElevator(this.m_elevator, this.m_oi, 2);

    this.zeroElevator = new ZeroElevator(this.m_elevator, this.m_oi);

    //m_oi.auto_deploy_sequence_button.whenPressed(new LimeLightHatchDeploy(m_drivetrain,m_oi));
    //m_oi.auto_grab_sequence_button.whenPressed(new LimeLightHatchGrab(m_drivetrain,m_oi));
    //m_oi.turn_limelight_off.cancelWhenPressed(new LimeLightHatchDeploy(m_drivetrain,m_oi));
    //m_oi.turn_limelight_off.cancelWhenPressed(new LimeLightHatchGrab(m_drivetrain,m_oi));
    //m_oi.limelight_on_button.whenPressed(new ActivateLimeLight());
    //m_oi.galaga_in_button.whenPressed(new GalagaIn(m_galaga, m_oi));
    //m_oi.galaga_out_button.whenPressed(new GalagaOut(m_galaga, m_oi));
    //m_oi.slide_in_button.whenPressed(new SlideIn(m_galaga, m_oi));
    //m_oi.slide_out_button.whenPressed(new SlideOut(m_galaga, m_oi));
    //m_oi.intake_in_button.whenPressed(new IntakeIn(m_intake, m_oi));
    //m_oi.intake_out_button.whenPressed(new IntakeOut(m_intake, m_oi));
    //m_oi.all_down_button.whenPressed(new ClimberDeploy(m_climber, m_oi));
    //m_oi.front_up_button.whenPressed(new ClimberFrontUp(m_climber, m_oi));
    //m_oi.back_up_button.whenPressed(new ClimberBackUp(m_climber, m_oi));
    //m_oi.auto_climb.whenPressed(new AutoClimb());

    /*new Thread(() -> {
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
      camera.setResolution(640, 480);
    }).start();*/
    casterCommand = new DetectCasterClimb();
    frontObjectCommand = new DetectFrontObject();
    rumbleFrontStilts = new RumbleFrontStilts();
    //elevatorSetpoint = new MoveElevSetpoint(m_elevator, m_oi);

    //Initialization message
    System.out.println("Robot online.");
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    //De-initialization message
    System.out.println("Robot is now offline.");
    //elevator.setDisabled(false);
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    //Initialization message
    System.out.println("Initiating autonomous!");
    m_stiltZero.start();
    zeroElevator.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    //Initialization message
    System.out.println("Initiating teleop!");
    //elevator.setDisabled(true);
    casterCommand.start();
    frontObjectCommand.start();
    rumbleFrontStilts.start();
    //elevatorSetpoint.start();

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
System.out.println("left stilt is:"+ m_oi.getLeftStiltSensor()+"and the right stilt is:"+ m_oi.getRightStiltSensor());
    this.logger.log();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}