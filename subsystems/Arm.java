package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.xrp.XRPServo;

public class Arm extends SubsystemBase{
    
    private final XRPServo m_armServo;
    
    public Arm(){
        m_armServo = new XRPServo(4);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void setArmPosition(double degrees){
        m_armServo.setAngle(degrees);
    }

}
