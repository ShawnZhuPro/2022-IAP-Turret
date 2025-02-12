// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LockOnTarget extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Limelight limeLight;
  private final DriveTrain driveTrain;
  private double target;
  private double error; 
  
  public LockOnTarget(DriveTrain driveTrain, Limelight limeLight, double target) {
    this.driveTrain = driveTrain;
    this.limeLight = limeLight;
    this.target = target;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    error = target - limeLight.get_tx();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    error = target - limeLight.get_tx();
    double speed = .3*error/30;

      SmartDashboard.putNumber("Error", error);
      SmartDashboard.putNumber("LimelightTVNUM", RobotContainer.getLime().get_tv());
      SmartDashboard.putNumber("LimelightX", RobotContainer.getLime().get_tx());
      SmartDashboard.putNumber("LimelightY", RobotContainer.getLime().get_ty());

    if(Math.abs(speed) < .2){
      speed = .2 * Math.abs(error)/error;
    }
    driveTrain.tankDrive(-speed, speed);     
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    // This is called when there is a target in sight, so the chassis will stop turning completely
    driveTrain.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    // This code should run forever until there is a valid target (reflective tape or yellow ball)
    //in sight and will continue to repeat
    return Math.abs(error) <= 1;
    
  }
}
