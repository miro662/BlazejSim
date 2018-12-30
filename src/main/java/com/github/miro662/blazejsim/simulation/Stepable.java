package com.github.miro662.blazejsim.simulation;

/**
 * Interface describing anything that can be simulated
 */
public interface Stepable {
    /**
     * Simulate single step
     * @param oldState old simulation state
     * @return new simulation state
     */
    SimulationState step(SimulationState oldState);
}
