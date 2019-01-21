package frc.inputs;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.utils.Constants;

/**
 * An implementation of the Driver's controls for when the robot is being
 * operated with a gamepad.
 */
public class OperatorGamepad implements OperatorHid {
    private static OperatorGamepad instance;

    private static final int POV_DPAD_UP = 0;
    private static final int POV_DPAD_RIGHT = 90;
    private static final int POV_DPAD_DOWN = 180;
    private static final int POV_DPAD_LEFT = 270;

    private final XboxController operatorGamepad;

    private OperatorGamepad() {
        // TODO(Raina/Ryan) make the port into a constant
        operatorGamepad = new XboxController(Constants.OPERATOR_GAMEPAD_PORT);
    }

    public static OperatorGamepad getInstance() {
        if (instance == null) {
            instance = new OperatorGamepad();
        }
        return instance;
    }

    @Override
    public boolean cargoIntake() {
        return operatorGamepad.getPOV() == POV_DPAD_DOWN;
    }

    @Override
    public boolean cargoOuttakeFront() {
        return operatorGamepad.getPOV() == POV_DPAD_UP;
    }

    @Override
    public boolean cargoOuttakeRight() {
        return operatorGamepad.getPOV() == POV_DPAD_RIGHT;
    }

    @Override
    public boolean cargoOuttakeLeft() {
        return operatorGamepad.getPOV() == POV_DPAD_LEFT;
    }

    @Override
    public boolean liftAllJacks() {
        // TODO remove operatorGamepad.getRawButton(3); (if XboxController Works)
        return operatorGamepad.getXButton();
    }

    @Override
    public boolean retractFrontJack() {
        // TODO remove (Y button) operatorGamepad.getRawButton(4); (if XboxController Works)
        return operatorGamepad.getYButton();
    }

    @Override
    public boolean retractRearJacks() {
        // TODO remove (B button) operatorGamepad.getRawButton(2); (if XboxController Works)
        return operatorGamepad.getBButton();
    }

    @Override
    public boolean basicSynchronousLift() {
        return operatorGamepad.getAButton();
    }
}