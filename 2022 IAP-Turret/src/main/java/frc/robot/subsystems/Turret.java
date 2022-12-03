// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends SubsystemBase {

  private final WPI_TalonSRX motor;
  /** Creates a new Turret. */
  public Turret() {
    motor = new WPI_TalonSRX(Constants.MotorPorts.TurretPort);
    motor.configFactoryDefault();
    motor.setInverted(false);
    motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

    motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
    motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
  }

  public void setAngle(double angle) {
    motor.setSelectedSensorPosition(angle);
  }

  // Resets the position to 0 in ticks
  // Plural is for 2+ encoders
  public void resetEncoders() {
    motor.setSelectedSensorPosition(0);
  }

  public double getAngle() {
    return motor.getSelectedSensorPosition() * 360 / (4096.0);
  }

  public double getCW_Forward_LimitSw() {
    return motor.isFwdLimitSwitchClosed();
  } //We have to manually set the limit switches to closed

  public double getCCW_Reverse_LimitSw() {
    return motor.isRevLimitSwitchClosed();
  } //We have to manually set the limit switches to closed

  public void spin(double speed){
    motor.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Angle: ", getAngle());
    // Shows limit switch status on SmartDashboard
    // We need to adjust our code to align with what SmartDashboard returns
    SmartDashboard.putNumber("Forward Lim SW:", getCW_Forward_LimitSw());
    SmartDashboard.putNumber("Reverse Lim SW:", getCCW_Reverse_LimitSw());
  }
}