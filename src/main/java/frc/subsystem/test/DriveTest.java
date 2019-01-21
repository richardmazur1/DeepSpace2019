package frc.subsystem.test;

import frc.loops.Looper;
import frc.subsystem.Drive;
import frc.subsystem.SubsystemManager;
import frc.utils.DriveSignal;
import java.util.Arrays;

import static frc.utils.Constants.DRIVE_SHUFFLEBOARD;

public class DriveTest implements SubsystemTest {
    private static final double BREAK_PERIOD = 0.15;
    private Looper enabledLooper = new Looper();
    private Looper disabledLooper = new Looper();
    private Drive drive = Drive.getInstance();
    private DriveTestState state = DriveTestState.INIT_DISABLED;
    private double goalTimestamp = 0.0;
    private boolean initializedState = false;

    private enum DriveTestState {
        INIT_DISABLED("The robot should not move before setup", 1.0),
        DRIVE_FORWARD("The robot drive forward", 2.0),
        TURN_RIGHT("The robot should be turning right", 2.0),
        TURN_LEFT("The robot should be turning left", 2.0),
        DRIVE_REVERSE("The robot should be moving in reverse", 2.0),
        END_DISABLED("The robot shouldn't be moving once disabled", 2.0);

        private String information;
        private double timeToRun;

        DriveTestState(String information, double timeToRun) {
            this.information = information;
            this.timeToRun = timeToRun;
        }

        public String getInformation() {
            return information;
        }

        public double getTimeToRun() {
            return timeToRun;
        }
    }


    private void neutralizeDriveTrain() {
        drive.setOpenLoop(DriveSignal.NEUTRAL);
    }

    private void handleLoop(double timestamp, DriveTestState nextState) {
        if (goalTimestamp <= timestamp) {
            neutralizeDriveTrain();
        }
        if (goalTimestamp <= timestamp + BREAK_PERIOD) {
            initializedState = false;
            state = nextState;
        }
    }

    private SubsystemManager subsystemManager = new SubsystemManager(
            Arrays.asList(Drive.getInstance())
    );

    public DriveTest() {
        drive.setOpenLoop(DriveSignal.NEUTRAL);
    }

    private void outputTelemetry() {
        DRIVE_SHUFFLEBOARD.putString("Test State", state.toString());
        DRIVE_SHUFFLEBOARD.putString("Test State Expected", state.getInformation());
    }

    public void periodic(double timestamp) {
        switch (state) {
            case INIT_DISABLED:
                if (!initializedState) {
                    goalTimestamp = this.state.timeToRun + timestamp;
                    subsystemManager.registerDisabledLoop(enabledLooper);
                    subsystemManager.registerDisabledLoop(disabledLooper);
                    disabledLooper.start();
                    enabledLooper.stop();
                    initializedState = true;
                }
                handleLoop(timestamp, DriveTestState.DRIVE_FORWARD);
                break;
            case DRIVE_FORWARD:
                if (!initializedState) {
                    disabledLooper.stop();
                    enabledLooper.start();
                }
                drive.setOpenLoop(new DriveSignal(1.0, 1.0));
                handleLoop(timestamp, DriveTestState.TURN_RIGHT);
                break;
            case TURN_RIGHT:
                drive.setOpenLoop(new DriveSignal(1.0, -1.0));
                handleLoop(timestamp, DriveTestState.TURN_LEFT);
                break;
            case TURN_LEFT:
                drive.setOpenLoop(new DriveSignal(-1.0, 1.0));
                handleLoop(timestamp, DriveTestState.DRIVE_REVERSE);
                break;
            case DRIVE_REVERSE:
                drive.setOpenLoop(new DriveSignal(-1.0, -1.0));
                handleLoop(timestamp, DriveTestState.END_DISABLED);
                break;
            case END_DISABLED:
                subsystemManager.stop();
                break;
        }
        outputTelemetry();
        subsystemManager.stop();
    }
}
