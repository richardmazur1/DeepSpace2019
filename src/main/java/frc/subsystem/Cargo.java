package frc.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.loops.Loop;
import frc.loops.LooperInterface;
import frc.states.CargoState;
import frc.states.CargoStateMachine;
import frc.utils.Constants;

import static frc.utils.Constants.CARGO_SHUFFLEBOARD;

public class Cargo extends Subsystem {
    private static Cargo instance;

    // Hardware
    private final WPI_TalonSRX rearSide;
    private final WPI_TalonSRX leftFront;
    private final WPI_TalonSRX rightFront;
    private final DigitalInput cargoSensor = new DigitalInput(Constants.CARGO_SENSOR);

    private CargoState currentState = new CargoState();
    private CargoStateMachine cargoStateMachine = new CargoStateMachine();
    private CargoState.IntakeState desiredState = CargoState.IntakeState.STOPPED;

    private Cargo() {
        rearSide = new WPI_TalonSRX(Constants.CARGO_CENTER);
        leftFront = new WPI_TalonSRX(Constants.CARGO_LEFT);
        rightFront = new WPI_TalonSRX(Constants.CARGO_RIGHT);
        // TODO make sure that the motors run the way that we expect, should be: +1 on
        //  left/right is intake and output, and +1 on rear moves it to the right when
        //  viewed from the back
        rightFront.setInverted(true);
    }

    public synchronized static Cargo getInstance() {
        if (instance == null) {
            instance = new Cargo();
        }
        return instance;
    }

    public synchronized void setDesiredState(CargoState.IntakeState intakeState) {
        cargoStateMachine.setDesiredState(intakeState);
    }

    @Override
    public void outputTelemetry() {
        CARGO_SHUFFLEBOARD.putString("Cargo Intake State", currentState.intakeState.toString());
        CARGO_SHUFFLEBOARD.putNumber("Rear Output", currentState.rearMotor);
        CARGO_SHUFFLEBOARD.putNumber("Left Output", currentState.leftMotor);
        CARGO_SHUFFLEBOARD.putNumber("Right Output", currentState.rightMotor);
    }

    @Override
    public synchronized void stop() {
        leftFront.stopMotor();
        rightFront.stopMotor();
        rearSide.stopMotor();
        setDesiredState(CargoState.IntakeState.STOPPED);
    }

    @Override
    public void registerEnabledLoops(LooperInterface enabledLooper) {
        Loop loop = new Loop() {
            @Override
            public void onStart(double timestamp) {
                desiredState = CargoState.IntakeState.STOPPED;
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized (Cargo.this) {
                    CargoState newState = cargoStateMachine.onUpdate(getCargoState());
                    updateOutputFromState(newState);
                }
            }

            @Override
            public void onStop(double timestamp) {
                desiredState = CargoState.IntakeState.STOPPED;
                stop();
            }
        };
        enabledLooper.registerLoop(loop);
    }

    private synchronized CargoState getCargoState() {
        currentState.ballInHold = cargoSensor.get();
        return currentState;
    }

    private synchronized void updateOutputFromState(CargoState state) {
        rearSide.set(ControlMode.PercentOutput, state.rearMotor);
        leftFront.set(ControlMode.PercentOutput, state.leftMotor);
        rightFront.set(ControlMode.PercentOutput, state.rightMotor);
    }
    /*

    private PeriodicIO periodicIo = new PeriodicIO();
    @Override
    public void outputTelemetry() {
        CARGO_SHUFFLEBOARD.putNumber("Rear Side Cargo Output", periodicIo.rearOutput);
        CARGO_SHUFFLEBOARD.putNumber("Left Side Cargo Output", periodicIo.leftOutput);
        CARGO_SHUFFLEBOARD.putNumber("Right Side Cargo Output", periodicIo.rightOutput);
        CARGO_SHUFFLEBOARD.putBoolean("Cargo In Hold", periodicIo.cargoInHold);
    }

        @Override
    public synchronized void stop() {
        setOutputs(CargoControlValues.STOPPED);
    }

    @Override
    public synchronized void writePeriodicOutputs() {
        rearSide.set(ControlMode.PercentOutput, periodicIo.rearOutput);
        leftFront.set(ControlMode.PercentOutput, periodicIo.leftOutput);
        rightFront.set(ControlMode.PercentOutput, periodicIo.rightOutput);
    }

    @Override
    public synchronized void readPeriodicInputs() {
        periodicIo.cargoInHold = cargoSensor.get();
    }

    private synchronized void setOutputs(CargoControlValues outputValues) {
        periodicIo.rearOutput = outputValues.getRear();
        periodicIo.leftOutput = outputValues.getLeft();
        periodicIo.rightOutput = outputValues.getRight();
    }

    private static class CargoControlValues {
        private double rear;
        private double left;
        private double right;

        public static final CargoControlValues STOPPED = new CargoControlValues(0, 0, 0);
        public static final CargoControlValues OUTTAKE_TO_RIGHT = new CargoControlValues(OUTTAKE_SPEED, -OUTTAKE_SPEED, OUTTAKE_SPEED);
        public static final CargoControlValues OUTTAKE_TO_LEFT = new CargoControlValues(-OUTTAKE_SPEED, OUTTAKE_SPEED, -OUTTAKE_SPEED);
        public static final CargoControlValues INTAKE = new CargoControlValues(0, INTAKE_SPEED, INTAKE_SPEED);

        public CargoControlValues(double rear, double left, double right) {
            this.rear = rear;
            this.left = left;
            this.right = right;
        }

        public double getRear() {
            return rear;
        }

        public double getLeft() {
            return left;
        }

        public double getRight() {
            return right;
        }
    }

    public static class PeriodicIO {
        // Inputs
        boolean cargoInHold;

        // Outputs
        double rearOutput;
        double leftOutput;
        double rightOutput;
    }
*/
}