// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.net.PortForwarder;

public class Limelight extends SubsystemBase {

NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

//This gets the tx, or the horizontal offset 
//from the crosshair in degrees (-27.0 to 27.0)
NetworkTableEntry tx = table.getEntry("tx"); 

//This gets the ty, or the vertical offset
//from the crosshair in degrees (-20.5 to 20.5)
NetworkTableEntry ty = table.getEntry("ty"); 

//This gets the ta, or how much in % of the target
//is visible (0.0-100.0)
NetworkTableEntry ta = table.getEntry("ta"); 

//This gets the tv, which sees if the limelight 
//has a valid target (1) or no valid target (0)
NetworkTableEntry tv = table.getEntry("tv"); 

private double txNum;
private double tyNum;
private double taNum;
private int tvNum;

double x = tx.getDouble(0.0);
double y = ty.getDouble(0.0);
double area = ta.getDouble(0.0);
  
  /** Creates a new ExampleSubsystem. */
  public Limelight() {

    //We have to add these ports so that we can connect to 
    //the limelight with our code through the robot's wifi
    PortForwarder.add(5800, "limelight.local", 5800);
    PortForwarder.add(5801, "limelight.local", 5801);
    PortForwarder.add(5802, "limelight.local", 5802);
    PortForwarder.add(5803, "limelight.local", 5803);
    PortForwarder.add(5804, "limelight.local", 5804);
    PortForwarder.add(5805, "limelight.local", 5805);

  }
  
  public double get_tx(){
    return txNum;
  }

  public double get_ty(){
    return tyNum;
  }

  public double get_ta(){
    return taNum;
  }

  public int get_tv(){
    return tvNum;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // This will output the x (horizontal offset) from the target in SmartDashboard
    SmartDashboard.putNumber("LimelightX", x);

    // This will output the y (vertical offset) from the target in SmartDashboard
    SmartDashboard.putNumber("LimelightY", y);

    // This will output the area of the target in SmartDashboard
    SmartDashboard.putNumber("LimelightArea", area);  
    
    // This will output the value of the target in SmartDashboard (0 or 1)
    SmartDashboard.putNumber("LimelightV", tvNum);   
    
    // We will be assigning tyNum to the double (-27.0 to 27.0) that limelight returns
    txNum = tx.getDouble(0.0);

    // We will be assigning tyNum to the double (-20.5 to 20.5) that limelight returns
    tyNum = ty.getDouble(0.0);

    // We will be assigning taNum to the double (0.0-100.0) that limelight returns
    taNum = ta.getDouble(0.0);

    // tvNum can be either 1 or 0, so we instantiate by adding (int) in front
    // We will be assigning tvNum to the int (1 or 0) that limelight returns
    tvNum = (int) tv.getDouble(0.0); 

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
