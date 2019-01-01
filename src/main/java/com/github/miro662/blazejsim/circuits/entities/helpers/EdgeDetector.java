package com.github.miro662.blazejsim.circuits.entities.helpers;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;

/**
 * Utility for detecting rising and falling edges
 */
public class EdgeDetector {
    private Input input;

    private LogicState previousState;
    private LogicState currentState;

    private EdgeDetector(Input input) {
        this.input = input;
        previousState = LogicState.UNDEFINED;
        currentState = LogicState.UNDEFINED;
    }

    /**
     * Creates edge detector for given input
     * @param input input to check edges on
     * @return edge detector for given input
     */
    public static EdgeDetector forInput(Input input) {
        return new EdgeDetector(input);
    }

    /**
     * Registers simulation step in edge detector
     * @param state simulation state of step
     */
    public void step(SimulationState state) {
        previousState = currentState;
        currentState = state.getFor(input);
    }

    /**
     * Is rising edge currently on edge detector (was low in previous state and is high now)
     * @return true if rising edge
     */
    public boolean isRisingEdge() {
        return previousState == LogicState.LOW && currentState == LogicState.HIGH;
    }

    /**
     * Is falling edge currently on edge detector (was high in previous state and is currently low
     * @return true if falling edge
     */
    public boolean isFallingEdge() {
        return previousState == LogicState.HIGH && currentState == LogicState.LOW;

    }
}
