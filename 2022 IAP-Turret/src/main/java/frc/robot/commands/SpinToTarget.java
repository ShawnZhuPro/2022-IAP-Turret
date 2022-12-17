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

public class SpinToTarget extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Limelight limeLight;
  private final DriveTrain driveTrain;  
  
  public SpinToTarget(DriveTrain driveTrain, Limelight limeLight) {
    this.driveTrain = driveTrain;
    this.limeLight = limeLight;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      SmartDashboard.putNumber("LimelightTVNUM", RobotContainer.getLime().get_tv());
      SmartDashboard.putNumber("LimelightX", RobotContainer.getLime().get_tx());
      SmartDashboard.putNumber("LimelightY", RobotContainer.getLime().get_ty());

      // This will spin the turret clockwise at 0.4/1.0 speed when there is no target in sight
      //if(limeLight.get_tv() == 0){
        driveTrain.tankDrive(0.2, -0.2);
     // } else if(limeLight.get_tv() == 1){
       // driveTrain.tankDrive(0, 0);
      //}
      
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
    return limeLight.get_tv() == 1;
  }
}
