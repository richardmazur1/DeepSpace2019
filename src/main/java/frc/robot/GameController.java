package frc.robot;

import frc.inputs.*;
import frc.utils.Constants;
import frc.utils.DriveSignal;

import static frc.utils.Constants.ROBOT_MAIN_SHUFFLEBOARD;

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

    public void outputTelemetry(){
        ROBOT_MAIN_SHUFFLEBOARD.putBoolean("Cargo Intake", this.cargoIntake());
        ROBOT_MAIN_SHUFFLEBOARD.putBoolean("Cargo Outtake Front", this.cargoOuttakeFront());
        ROBOT_MAIN_SHUFFLEBOARD.putBoolean("Cargo Outtake Left", this.cargoOuttakeLeft());
        ROBOT_MAIN_SHUFFLEBOARD.putBoolean("Cargo Outtake Right", this.cargoOuttakeRight());
        ROBOT_MAIN_SHUFFLEBOARD.putBoolean("Retract Front Jacks", this.retractFrontJack());
        ROBOT_MAIN_SHUFFLEBOARD.putBoolean("Lift All Jacks", this.liftAllJacks());
        ROBOT_MAIN_SHUFFLEBOARD.putNumber("Drive Signal Left", this.getDriveSignal().getLeftOutput());
        ROBOT_MAIN_SHUFFLEBOARD.putNumber("Drive Signal Right", this.getDriveSignal().getRightOutput());
        ROBOT_MAIN_SHUFFLEBOARD.putBoolean("Basic Syncrhonous Lift", this.basicSynchronousLift());
    }

}