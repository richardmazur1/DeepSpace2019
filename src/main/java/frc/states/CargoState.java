package frc.states;

public class CargoState {
    public enum IntakeState {
        INTAKE,
        OUTTAKE_LEFT,
        OUTTAKE_RIGHT,
        STOPPED
    }

    // inputs
    public IntakeState intakeState = IntakeState.STOPPED;
    public boolean ballInHold = false;

    // outputs
    public double rearMotor;
    public double leftMotor;
    public double rightMotor;
    public double intake;
}
