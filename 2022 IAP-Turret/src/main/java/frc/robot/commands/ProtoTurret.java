// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ProtoTurret extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  //private final ExampleSubsystem m_subsystem;

  private final Limelight limeLight;
  private final DriveTrain driveTrain;
  
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ProtoTurret(DriveTrain driveTrain, Limelight limeLight) {
   // m_subsystem = subsystem;
    this.driveTrain = driveTrain;
    this.limeLight = limeLight;
    // Use addRequirements() here to declare subsystem dependencies.
   // addRequirements(subsystem);
   
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

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
    PIDController pid = new PIDController(kP, kI, kD);

    // If there are no valid targets the limelight sees
    if(limeLight.get_tv() == 0){

      // This will use the PID's precise calculations to accurately turn the chassis clockwise
      driveTrain.tankDrive(pid.calculate(limeLight.get_tx()), -pid.calculate(limeLight.get_tx()));

    }

    else { 

      // If there are valid targets the limelight sees
      if(limeLight.get_tv() == 1){

      // This uses the PID to turn the chassis counter clockwise
      driveTrain.tankDrive(-pid.calculate(limeLight.get_tx()), pid.calculate(limeLight.get_tx()));

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

    // This code should run forever until there is a valid target (reflective tape or yellow ball)
    //in sight and will continue to repeat
    return limeLight.get_tv() == 1;
    
  }
}
