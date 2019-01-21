package frc.robot;

import frc.inputs.*;
import frc.utils.Constants;
import frc.utils.DriveSignal;

public class GameController implements GameHid {
    private static GameController instance;
    private final DriverHid driverHid;
    private final OperatorHid operatorHid;

    private GameController() {
        if(Constants.USE_JOYSTICK_FOR_DRIVING){
            driverHid = DriverJoystick.getInstance();
        } else {
            driverHid = DriverGamepad.getInstance();
        }
        operatorHid = OperatorGamepad.getInstance();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    @Override
    public boolean cargoIntake() {
        return operatorHid.cargoIntake();
    }

    @Override
    public boolean cargoOuttakeFront() {
        return operatorHid.cargoOuttakeFront();
    }

    @Override
    public boolean cargoOuttakeRight() {
        return operatorHid.cargoOuttakeRight();
    }

    @Override
    public boolean cargoOuttakeLeft() {
        return operatorHid.cargoOuttakeLeft();
    }

    @Override
    public boolean liftAllJacks() {
        return operatorHid.liftAllJacks();
    }

    @Override
    public boolean retractFrontJack() {
        return operatorHid.retractFrontJack();
    }

    @Override
    public DriveSignal getDriveSignal() {
        return driverHid.getDriveSignal();
    }

    @Override
    public boolean retractRearJacks() {
        return operatorHid.retractRearJacks();
    }

    @Override
    public boolean basicSynchronousLift() {
        return operatorHid.basicSynchronousLift();
    }

}