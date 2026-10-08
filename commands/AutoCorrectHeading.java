// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.XRPDrivetrain;
import frc.robot.Constants;

/** An example command that uses an example subsystem. */
public class AutoCorrectHeading extends Command {
  
  private final PIDController pidturn;
  private final XRPDrivetrain m_xrpDrivetrain;
  
  public AutoCorrectHeading(XRPDrivetrain xrpDrivetrain){
    m_xrpDrivetrain = xrpDrivetrain;
    pidturn = new PIDController(
      Constants.turnkP,
      Constants.turnkI,
      Constants.turnkD
    );
    pidturn.setSetpoint(Constants.kturnSetpoint);
  }
  

  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_xrpDrivetrain.resetgyro();
}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double output = pidturn.calculate(m_xrpDrivetrain.getHeadingDeg());
    m_xrpDrivetrain.arcadeDrive(0,output);
    SmartDashboard.putNumber("Gyro Angle", m_xrpDrivetrain.getHeadingDeg());
    SmartDashboard.putNumber("left encoder",m_xrpDrivetrain.getLeftDistanceInch());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_xrpDrivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
