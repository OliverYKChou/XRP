// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.XRPDrivetrain;

import java.util.function.DoubleSupplier;
import frc.robot.subsystems.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.xrp.XRPRangefinder;

/** An example command that uses an example subsystem. */
public class DriveForwardSensor extends Command {
    private final XRPDrivetrain m_drivesubsystem;
    private final Ultrasonic m_ultrasonic;
    private final double m_speed;
    private final double m_mindistance;
    private double currentdistance;

  public DriveForwardSensor(XRPDrivetrain xrpdrivetrain,Ultrasonic ultrasonic, double speed, double mindistance) {

    m_drivesubsystem = xrpdrivetrain;
    m_ultrasonic = ultrasonic;
    m_speed = speed;
    m_mindistance = mindistance;
    
    addRequirements(m_drivesubsystem,m_ultrasonic);
  }

  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivesubsystem.resetEncoders();
    //  currentdistance = 0;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("range", m_ultrasonic.getDistanceCm1());
    m_drivesubsystem.arcadeDrive(0.7, 0.0); // Drive forward at specified speed
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivesubsystem.arcadeDrive(0, 0);
   
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_ultrasonic.getDistanceCm1() <= 30;
  }
}
