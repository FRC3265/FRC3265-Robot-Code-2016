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
	boolean enabled;

	
	public void robotInit() {
	//Runs Once to start(initilize)robot
		 
		//Each talon is assigned a name (talon0 for example ) 
		talon0 = new Talon(0);
		talon1 = new Talon(1);
		talon2 = new Talon(2);
		talon3 = new Talon(3);
		talon4 = new Talon(4);
		
		//Sets talon 1 & 2 to be the robots drive, This is called the drive train
		tankDrive = new RobotDrive(talon1, talon2);
		
		//Sets the joystick name to joysticks
		joystick = new Joystick(0);
		
		//Sets the limitswitch to limitswitch
		limitswitch = new DigitalInput(0);
		
		//Sets camera to run
		CameraServer server;
		server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam0");
	}

	/**
	 * This function is called periodically during autonomous
	 */
	
	public void autonomousPeriodic() {
		//Periodic is set so this code runs periodically 
		int count = 0;
		// Starting the timer at 0

		while (isAutonomous() && isEnabled()) {
			//This code does the same thing as Init (autonomousInit) meaning it runs constantly so long as this mode is active 
			
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

			}

		}

	}

	/**
	 * This function is called periodically during operator control
	 */
	 public void teleopPeriodic() {
		 	// Code is same as autonomous except drivable
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
		tankDrive.tankDrive(rightYAxis *.5 , leftYAxis* .5);
			
		//Sets ball intake 
		if(leftBumper){
	        talon0.set(1); 	
		}else if(rightBumper){
	        talon0.set(-1); 	
		} else{
			talon0.set(0);
		}
		
		 //Raises arm with Lefttrigger
		if (leftTrigger != 0) {
			talon4.set(leftTrigger);
			talon3.set(leftTrigger);
			}
		
			// Lowers arm with rightTrigger
			else if (rightTrigger != 0) {
				talon4.set(-rightTrigger);
				talon3.set(-rightTrigger);
				talon4.disable();
				talon3.disable();
			}else{
				talon4.disable();
				talon3.disable();
			}
		}	
	}
	 
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		
		while (isTest() && isEnabled()) {
			double leftYAxis = joystick.getRawAxis(1);
			double rightYAxis = joystick.getRawAxis(5);
			double leftTrigger = joystick.getRawAxis(2);
			double rightTrigger = joystick.getRawAxis(3);
			boolean rightBumper = joystick.getRawButton(6);
			boolean leftBumper = joystick.getRawButton(5);
			boolean bButton = joystick.getRawButton(2);
			boolean yButton = joystick.getRawButton(4);
			boolean xButton = joystick.getRawButton(3);
			
			tankDrive.tankDrive(rightYAxis, leftYAxis);
			
			if(leftBumper){
	        	talon0.set(1); 	

			}else if(rightBumper){
	        	talon0.set(-1); 	

			} else{
				talon0.set(0);
			}
			}
			
		

	}

}
