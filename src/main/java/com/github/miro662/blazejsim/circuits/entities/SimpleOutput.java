package com.github.miro662.blazejsim.circuits.entities;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import org.jetbrains.annotations.NotNull;

/**
 * helper class for making outputs
 */
public class SimpleOutput extends ClassEntity {
    @EntityInput
    public Input in;

    public synchronized LogicState getOutputState() {
        return outputState;
    }

    private LogicState outputState;

    @NotNull
    @Override
    protected synchronized SimulationState simulate(SimulationState oldState) {
        outputState = oldState.getFor(in);
        return SimulationState.empty();
    }
}
