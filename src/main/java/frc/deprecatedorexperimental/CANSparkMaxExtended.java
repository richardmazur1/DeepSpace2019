package frc.deprecatedorexperimental;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;

// TODO decide whether to implement this... it would save a few lines but probably isn't worth it
public class CANSparkMaxExtended {
    private CANSparkMax sparkMax;
    private CANEncoder encoder;
    private CANPIDController pid;
    private CANDigitalInput digitalInput;
    private final boolean useEncoder;
    private final boolean usePid;
    private final boolean useLimitSwitch;

    public CANSparkMaxExtended(int deviceId, MotorType motorType) {
        this(deviceId, motorType, false);
    }

    public CANSparkMaxExtended(int deviceId, MotorType motorType, boolean inverted) {
        this(deviceId, motorType, false, false);
    }

    public CANSparkMaxExtended(int deviceId, CANSparkMaxLowLevel.MotorType motorType, boolean inverted, LimitSwitch limitSwitch, LimitSwitchPolarity limitSwitchPolarity) {
        this(deviceId, motorType, inverted, false, false, limitSwitch, limitSwitchPolarity);
    }

    public CANSparkMaxExtended(int deviceId, CANSparkMaxLowLevel.MotorType motorType, boolean inverted, boolean useEncoder) {
        this(deviceId, motorType, inverted, useEncoder, true);
    }

    public CANSparkMaxExtended(int deviceId, CANSparkMaxLowLevel.MotorType motorType, boolean inverted, boolean useEncoder, boolean usePid) {
        this(deviceId, motorType, inverted, useEncoder, usePid, null, null);
    }

    public CANSparkMaxExtended(int deviceId, CANSparkMaxLowLevel.MotorType motorType, boolean inverted, boolean useEncoder, boolean usePid, LimitSwitch limitSwitch, LimitSwitchPolarity limitSwitchPolarity) {
        sparkMax = new CANSparkMax(deviceId, motorType);
        this.useEncoder = useEncoder;
        usePid = useEncoder && usePid;
        this.usePid = usePid;
        useLimitSwitch = limitSwitch != null && limitSwitchPolarity != null;
        if (this.useEncoder) {
            encoder = new CANEncoder(sparkMax);
        }
        if (this.usePid) {
            pid = new CANPIDController(sparkMax);
        }
        if (this.useLimitSwitch) {
            digitalInput = new CANDigitalInput(sparkMax, limitSwitch, limitSwitchPolarity);
        }
    }

    // Limit Switch
    public void REMOVE_ME_TODO() {
        final boolean BOOLEAN = false;
        digitalInput.enableLimitSwitch(BOOLEAN); // CANError
        digitalInput.get();                      // boolean
        digitalInput.isLimitSwitchEnabled();     // boolean
    }

    // Encoder
    public void REMOVE_ME_TODO_2() {
        encoder.getPosition(); // double
        encoder.getVelocity(); // double
    }

    // PID
    public void REMOVE_ME_TODO_3() {
        final double DOUBLE = 1.0;
        final int INT = 1;
        final ControlType CONTROL_TYPE = ControlType.kVoltage;
        // all return doubles
        pid.getD();
        pid.getD(INT);
        pid.getFF();
        pid.getFF(INT);
        pid.getI();
        pid.getI(INT);
        pid.getIZone();
        pid.getIZone(INT);
        pid.getOutputMax();
        pid.getOutputMax();
        pid.getP();
        pid.getP(INT);
        pid.getOutputMin();
        pid.getOutputMin(INT);

        // all return CANErrors
        pid.setD(DOUBLE);
        pid.setD(DOUBLE, INT);
        pid.setFF(DOUBLE);
        pid.setFF(DOUBLE, INT);
        pid.setI(DOUBLE);
        pid.setI(DOUBLE, INT);
        pid.setIZone(DOUBLE);
        pid.setIZone(DOUBLE, INT);
        pid.setOutputRange(DOUBLE, DOUBLE);
        pid.setOutputRange(DOUBLE, DOUBLE, INT);
        pid.setP(DOUBLE);
        pid.setP(DOUBLE, INT);
        pid.setReference(DOUBLE, CONTROL_TYPE);
        pid.setReference(DOUBLE, CONTROL_TYPE, INT);
        pid.setReference(DOUBLE, CONTROL_TYPE, INT, DOUBLE);
    }

    public void REMOVE_ME_TODO_4() {
        final CANSparkMax CAN_SPARK_MAX = new CANSparkMax(0, MotorType.kBrushless);
        final CANSparkMax.ExternalFollower EXTERNAL_FOLLOWER = new CANSparkMax.ExternalFollower(1, 1);
        final boolean BOOLEAN = false;
        final int INT = 1;
        sparkMax.burnFlash();
        sparkMax.clearFaults();
        sparkMax.close();
        sparkMax.disable();
        sparkMax.follow(CAN_SPARK_MAX);
        sparkMax.follow(CAN_SPARK_MAX, BOOLEAN);
        sparkMax.follow(EXTERNAL_FOLLOWER, INT);
        sparkMax.follow(EXTERNAL_FOLLOWER, INT, BOOLEAN);
        sparkMax.disable();
        sparkMax.get();
        sparkMax.getAppliedOutput();
        sparkMax.getBusVoltage();
        sparkMax.getEncoder();
    }

}