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

    double kP = 0.0;
    double kI = 0.0;
    double kD = 0.0;
    PIDController pid = new PIDController(kP, kI, kD);

    if(limeLight.get_tv() == 0){
      driveTrain.tankDrive(pid.calculate(limeLight.get_tx()), -pid.calculate(limeLight.get_tx()));
    }

    else { 

      if(limeLight.get_tv() == 1){
      driveTrain.tankDrive(0.3, -0.3);
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
    return limeLight.get_tv() == 1;
  }
}
