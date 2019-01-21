package frc.subsystem;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

import static frc.utils.Constants.ROBOT_STATE_SHUFFLEBOARD;

public class RobotState extends Subsystem {
    // TODO actually implement an odometry system. ORB-SLAM/SVO/Simple Wheel odometry might be useful?
    //  adding an extra notifier just for this might be worth it if it proves useful
    private static RobotState instance;

    private final AHRS navX;

    private static class PeriodicIO {
        double yaw;
        double roll;
        double pitch;
    }

    private PeriodicIO periodicIo = new PeriodicIO();

    public static RobotState getInstance() {
        if (instance != null) {
            instance = new RobotState();
        }
        return instance;
    }

    private RobotState() {
        navX = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public synchronized void readPeriodicInputs() {
        periodicIo.yaw = navX.getYaw();
        periodicIo.roll = navX.getRoll();
        periodicIo.pitch = navX.getPitch();
    }

    @Override
    public void outputTelemetry() {
        ROBOT_STATE_SHUFFLEBOARD.putNumber("Yaw", periodicIo.yaw);
        ROBOT_STATE_SHUFFLEBOARD.putNumber("Roll", periodicIo.roll);
        ROBOT_STATE_SHUFFLEBOARD.putNumber("Pitch", periodicIo.pitch);
    }

    @Override
    public void stop() {

    }

    public synchronized double getYaw() {
        return periodicIo.yaw;
    }

    public synchronized double getRoll() {
        return periodicIo.roll;
    }

    public synchronized double getPitch() {
        return periodicIo.pitch;
    }
}
