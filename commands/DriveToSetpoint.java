// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.XRPDrivetrain;
import frc.robot.Constants;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

/** An example command that uses an example subsystem. */
public class DriveToSetpoint extends Command {
  
  private final PIDController pid;
  private final PIDController pidturn;
  private final XRPDrivetrain m_xrpDrivetrain;
  
  public DriveToSetpoint(XRPDrivetrain xrpDrivetrain){

    m_xrpDrivetrain = xrpDrivetrain;
    pid = new PIDController(
      Constants.drivekP,
      Constants.drivekI,
      Constants.drivekD
    );

    pidturn = new PIDController(
      Constants.turnkP,
      Constants.turnkI,
      Constants.turnkD
    );

    pid.setTolerance(Constants.kdriveTolerance);
    pid.setSetpoint(Constants.kdriveSetpoint);
    pidturn.setSetpoint(Constants.kturnSetpoint);

  }
  

  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_xrpDrivetrain.resetEncoders();
    m_xrpDrivetrain.resetgyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double driveoutput = pid.calculate(m_xrpDrivetrain.getAverageDistanceCm());
    
    double turnoutput = pidturn.calculate(m_xrpDrivetrain.getHeadingDeg());
    m_xrpDrivetrain.arcadeDrive(driveoutput, turnoutput);


    SmartDashboard.putNumber("Average Distance ", m_xrpDrivetrain.getAverageDistanceCm());
    SmartDashboard.putNumber("gryo", m_xrpDrivetrain.getHeadingDeg());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_xrpDrivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Constants.kdriveSetpoint - m_xrpDrivetrain.getAverageDistanceCm() <= Constants.kdriveTolerance;
  }
}
