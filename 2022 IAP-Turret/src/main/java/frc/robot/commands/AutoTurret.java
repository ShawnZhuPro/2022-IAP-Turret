// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Turret;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoTurret extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  public final Limelight lime;
  public final Turret turret;
  public PIDController pid;

  // The constant where the limit switch returns as closed
  public int limitSwitchClosed = 1;

  // The constant where the limit switch returns as opened
  public int limitSwitchOpen = 0;

  // When the limit swith is manually toggled 
  public boolean manualToggle = true;
  public boolean directionToggle;

 
  public AutoTurret(Limelight lime, Turret turret) {

    this.turret = turret;
    this.lime = lime;

   // P is the Proportional constant 
    /* It will correct the error depending on how big the amount of error is:
    * Small amount of error = low correction, High = larger correction
    */
    double kP = 0.0;

    // I is the Integral constant
    /* It adds up all the past errors to help remove constant errors because
    * no matter how small the constant error, the sum will be significant enough 
    * to adjust the controller output as needed
    */
    double kI = 0.0;

    // D is the derivative constant
    /* It will predict the amount of error in the future because it examines
    * the slope of the change in error 
    */
    double kD = 0.0;

    // PID is used to make small adjustments to achieve more precise movement
    pid = new PIDController(kP, kI, kD); 

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    turret.resetEncoders();
    turret.setAngle(0);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Switches from manual to auto if button is pressed
    if(RobotContainer.getJoy1().getTriggerReleased()){
      manualToggle = !manualToggle;
    }

    if(manualToggle == true){

      // The joystick returns -1 so we use -0.2
      turret.spin(-0.2*RobotContainer.getJoy1().getX()); 
    }

    if(!manualToggle == true){

      // This is an if statement when there is a valid target (tv == 1) and the reverse 
      //or forward limit switch is not closed
      // This is just a placeholder for safety
      if(lime.get_tv() == 1 && !(turret.getCCW_Reverse_LimitSw() == limitSwitchClosed | turret.getCW_Forward_LimitSw() == limitSwitchClosed)){

        // Use the pid to precisely turn the chassis horizontally at a calculated speed
        double speed = pid.calculate(lime.get_tx());

        // This should spin clockwise if there is a positive x position
        turret.spin(speed); 

      }

      // If the limelight has no valid targets
      else if(lime.get_tv() == 0){

        if(turret.getCCW_Reverse_LimitSw() == limitSwitchClosed){

          // It is true when the reverse limit switch is hit
          // Will go reverse in direction, so clockwise
          directionToggle = true;

        }

        if(turret.getCW_Forward_LimitSw() == limitSwitchClosed){

          // It is true when the forward limit switch is hit
          // Will go forward in direction, so counter clockwise
          directionToggle = false;

        }

        if(directionToggle == true){

          // Will go clockwise if the reverse limit switch is hit
          turret.spin(0.3); 

        }

        if(!directionToggle == true){
          // Will go counter clockwise if the forward limit switch it hit
          turret.spin(-0.3); 

        }

      } else {

        // Will disable motor if all condition above aren't met
        turret.spin(0); 

      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return false;

  }
}
