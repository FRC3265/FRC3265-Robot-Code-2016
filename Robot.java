package org.usfirst.frc.team3265.robot;

import javax.print.DocFlavor.STRING;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;
/**---------------------------------------------------**/
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
@SuppressWarnings("unused")
public class Robot extends IterativeRobot {

	RobotDrive tankDrive;
	Joystick joystick;
	Talon talon0;
	Talon talon1;
	Talon talon2;
	Talon talon3;
	Talon talon4;
	DigitalInput limitswitch;
	Timer timer;
	// CameraServer camera;
	boolean enabled;

	
	public void robotInit() {
		talon0 = new Talon(0);
		talon1 = new Talon(1);
		talon2 = new Talon(2);
		talon3 = new Talon(3);
		talon4 = new Talon(4);
		tankDrive = new RobotDrive(talon1, talon2);
		joystick = new Joystick(0);
		limitswitch = new DigitalInput(0);
		CameraServer server;
		server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam0");

	}

	/**
	 * This function is called periodically during autonomous
	 */
	
	public void autonomousPeriodic() {
		int count = 0;

		while (isAutonomous() && isEnabled()) {
			// Set arms down
			if (count < 900) {
				//talon3.set(-0.70);
				//talon4.set(-0.70);
			} else if (count > 900) {
				talon3.disable();
				talon4.disable();
			}

			// Wait one second, and then drive forward
			if (count >= 600) {
				tankDrive.tankDrive(-0.75, -0.75);
				talon3.disable();
				talon4.disable();
			}
			// Robot continues to move until the time is met (3500)
			if (count < 4000) {
				count++;
			} else if (count == 4000) {
				Timer.delay(5);
				// Once 3500 is met, robot will stop driving until teleop is enabled.
				// talon1.set(0.0);
				// talon2.set(0.0);
			}

		}

	}

	/**
	 * This function is called periodically during operator control
	 */
	 public void teleopPeriodic() {
	//public void teleopInit() {

		int counter = 0;
		while (isOperatorControl() && isEnabled()) {
	/**-----------------------------------------------------------------------------------------------------**/
			double leftYAxis = joystick.getRawAxis(1);
			double rightYAxis = joystick.getRawAxis(5);
			double leftTrigger = joystick.getRawAxis(2);
			double rightTrigger = joystick.getRawAxis(3);
			boolean rightBumper = joystick.getRawButton(6);
			boolean leftBumper = joystick.getRawButton(5);
			boolean bButton = joystick.getRawButton(2);
			boolean yButton = joystick.getRawButton(4);
			boolean xButton = joystick.getRawButton(3);
	/**-----------------------------------------------------------------------------------------------------**/
			
			// Enables drive at 50% speed
			tankDrive.tankDrive(rightYAxis, leftYAxis);
			// Right bumper sets speed to 100%
			if (rightBumper == true) {
				tankDrive.tankDrive(rightYAxis / 1.5, leftYAxis / 1.5);

			} else if (rightBumper == false) {

				// Left bumper sets speed to 25%
				if (leftBumper == true) {
					tankDrive.tankDrive(rightYAxis * 2, leftYAxis * 2);
				} else {
					tankDrive.tankDrive(rightYAxis, leftYAxis);
				}
			}

	        	/*talon0.set(-1); 	*/

			
			if(limitswitch.get()) {	
	        	talon0.set(0); 	
	        
	        
	 		} else if(bButton == true ) {
	 	        	talon0.set(-1);
	 	        }

			// else {
			// talon0.set(0);
			// }

			// Makes start button toggle and sets ballIn to start

			// ----------------------------------------------------------------------

			// ----------------------------------------------------------------------

			// Raises arm with leftTrigger
			if (leftTrigger != 0) {
				talon4.set(leftTrigger);
				talon3.set(leftTrigger);
			}

			// Lowers arm with rightTrigger
			else if (rightTrigger != 0) {

				// Trying to lower speed which arm goes down with

				talon4.set(-rightTrigger);
				talon3.set(-rightTrigger);
				talon4.disable();
				talon3.disable();

				/*
				 * Try seting a positive value of .05 or somthing close instead
				 * of disabling it
				 */

			}
		}

		/** robot code here! **/
		//Timer.delay(0.005);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

		while (isTest()) {

			talon0.set(0.4);
			Timer.delay(2);
			talon1.set(0.4);
			Timer.delay(2);
			talon2.set(0.4);
			Timer.delay(2);
			talon3.set(0.4);
			Timer.delay(2);
			talon4.set(0.4);
			Timer.delay(2);
			tankDrive.tankDrive(0, 0);
			Timer.delay(1);

		}

	}

}
