package com.github.miro662.blazejsim.simulation;

/**
 * Interface for objects which logic state can be evaluated
 */
public interface Evaluable {
    /**
     * Evaluate value of this object
     * @param previousState previous simulation state, on basis of which logic state should be calculated
     * @return evaluated logic state
     */
    default LogicState evaluate(SimulationState previousState) {
        return LogicState.UNDEFINED;
    }
}
