package frc.subsystem.test;

import frc.robot.GameController;

public class GamepadTest implements SubsystemTest {
    private GameController gameController = GameController.getInstance();

    @Override
    public void periodic(double timestamp) {
        gameController.outputTelemetry();
    }
}
