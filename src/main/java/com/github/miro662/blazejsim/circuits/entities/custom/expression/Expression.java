package com.github.miro662.blazejsim.circuits.entities.custom.expression;

import com.github.miro662.blazejsim.simulation.LogicState;

import java.util.stream.Stream;

/**
 * Interface describing expression
 */
public interface Expression {
    /**
     * Calculate value of expression for given parameters
     * @param parameters parameter provider containing all parameters
     * @return value of expression
     */
    LogicState evaluate(ParameterProvider parameters);

    /**
     * Get list of expression parameters
     * @return list of expression params
     */
    default Stream<String> getParameters() {
        return Stream.empty();
    }
}
