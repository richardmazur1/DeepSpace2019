package frc.subsystem;

import frc.loops.LooperInterface;

public abstract class Subsystem {

    public abstract void outputTelemetry();

    public abstract void stop();

    public void writePeriodicOutputs() {

    }

    public void readPeriodicInputs() {

    }

    public void zeroSensors() {

    }

    public void registerEnabledLoops(LooperInterface enabledLooper) {

    }
}