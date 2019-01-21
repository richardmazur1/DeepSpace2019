package frc.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.utils.Constants;

// TODO pretty much this whole thing
//  invert whatever needs to be inverted
//  setup sensors &|| closed loop control for this
public class HatchMechanism extends Subsystem{
    private static HatchMechanism instance;
    private final WPI_TalonSRX hatch;
    private final WPI_TalonSRX intake;

    private HatchMechanism(){
        hatch = new WPI_TalonSRX(Constants.HATCH);
        intake = new WPI_TalonSRX(Constants.INTAKE);
    }

    public static HatchMechanism getInstance(){
        if(instance == null){
            instance = new HatchMechanism();
        }
        return instance;
    }

    @Override
    public void outputTelemetry() {
        // TODO(Raina)

    }

    @Override
    public void stop() {
        hatch.stopMotor();
        intake.stopMotor();
    }
}
