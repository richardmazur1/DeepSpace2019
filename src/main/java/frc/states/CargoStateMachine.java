package frc.states;

public class CargoStateMachine {
    // TODO what should these speeds be?
    // These should probably always be positive, we can negate motors individually
    private static final double DEFAULT_OUTTAKE_POWER = 1.0;
    private static final double DEFAULT_INTAKE_POWER = 1.0;

    private CargoState systemState = new CargoState();
    private CargoState.IntakeState desiredIntakeState = CargoState.IntakeState.STOPPED;

    public synchronized void setDesiredState(CargoState.IntakeState intakeState) {
        desiredIntakeState = intakeState;
    }

    public synchronized CargoState onUpdate(CargoState currentState) {
        if (currentState.ballInHold) {
            // If ball is in hold, don't allow running the intake any more
            if (desiredIntakeState != CargoState.IntakeState.INTAKE) {
                systemState.intakeState = desiredIntakeState;
            } else {
                systemState.intakeState = CargoState.IntakeState.STOPPED;
            }
        } else {
            // If there is no ball in the hold, let the user do whatever they want
            systemState.intakeState = desiredIntakeState;
        }

        // Update motor powers if the new state is not equal to the last one
        if (systemState != currentState) {
            handleSystemStateUpdate();
        }

        // Update this nugget about the system state, has no bearing on the motor update thought
        systemState.ballInHold = currentState.ballInHold;

        return systemState;
    }

    public void handleSystemStateUpdate() {
        switch (systemState.intakeState) {

            case INTAKE:
                systemState.rearMotor = 0.0;
                systemState.leftMotor = DEFAULT_INTAKE_POWER;
                systemState.rightMotor = DEFAULT_INTAKE_POWER;
                systemState.intake = DEFAULT_INTAKE_POWER;
                break;
            case OUTTAKE_LEFT:
                systemState.rearMotor = -DEFAULT_OUTTAKE_POWER;
                systemState.leftMotor = DEFAULT_INTAKE_POWER;
                systemState.rightMotor = -DEFAULT_INTAKE_POWER;
                systemState.intake = 0;
                break;
            case OUTTAKE_RIGHT:
                systemState.rearMotor = DEFAULT_OUTTAKE_POWER;
                systemState.leftMotor = -DEFAULT_INTAKE_POWER;
                systemState.rightMotor = DEFAULT_INTAKE_POWER;
                systemState.intake = 0;
                break;
            case STOPPED:
                systemState.rearMotor = 0;
                systemState.leftMotor = 0;
                systemState.rightMotor = 0;
                systemState.intake = 0;
                break;
        }
    }
}
