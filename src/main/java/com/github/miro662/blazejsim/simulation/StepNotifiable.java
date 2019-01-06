package com.github.miro662.blazejsim.simulation;

@FunctionalInterface
public interface StepNotifiable {
    void notifyStep(SimulationState state);
}
