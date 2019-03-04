/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    Joystick stick;
    int driverPort;
    int manipulatorPort;

    public Joystick driverController;
    public Joystick manipulatorController;

    public JoystickButton limelight_on_button;
    public JoystickButton galaga_in_button;
    public JoystickButton galaga_out_button;
    public JoystickButton slide_in_button;
    public JoystickButton slide_out_button;
    public JoystickButton intake_in_button;
    public JoystickButton intake_out_button;
    public JoystickButton all_down_button;
    public JoystickButton front_up_button;
    public JoystickButton back_up_button;
    public JoystickButton lift_forward_button;
    public JoystickButton lift_backward_button;

    public OI() {
        stick = new Joystick(RobotMap.joyIndex);
        getDriverPort();
        driverController = new Joystick(driverPort);
        manipulatorController = new Joystick(manipulatorPort);
        limelight_on_button = new JoystickButton(driverController, RobotMap.LIME_LIGHT_ON_BUTTON);
        galaga_in_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_GALAGA_IN);
        galaga_out_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_GALAGA_OUT);
        slide_in_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_SLIDE_IN);
        slide_out_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_SLIDE_OUT);
        intake_in_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_INTAKE_IN);
        intake_out_button = new JoystickButton(manipulatorController, RobotMap.MANIPULATOR_CONTROLLER_INTAKE_OUT);
        all_down_button = new JoystickButton(driverController, RobotMap.DRIVER_CONTROLLER_LIFT_ALL_DOWN);
        front_up_button = new JoystickButton(driverController, RobotMap.DRIVER_CONTROLLER_LIFT_FRONT_UP);
        back_up_button = new JoystickButton(driverController, RobotMap.DRIVER_CONTROLLER_LIFT_BACK_UP);
        lift_forward_button = new JoystickButton(driverController, RobotMap.DRIVER_CONTROLLER_LIFT_FORWARD);
        lift_backward_button = new JoystickButton(driverController, RobotMap.DRIVER_CONTROLLER_LIFT_BACKWARD);

    }

    public Joystick getJoystck() {

        return stick;

    }

    public void getDriverPort() {
        Joystick controller = new Joystick(0);
        String controllerName = controller.getName();

        driverPort = 0;
        manipulatorPort = 1;
    }

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    //// joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.

    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
