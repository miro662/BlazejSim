package com.github.miro662.blazejsim.circuits.entities.custom.expression;

import com.github.miro662.blazejsim.simulation.LogicState;

/**
 * Describes something that can provide parameters for expression
 */
public interface ParameterProvider {
    /**
     * Return parameter with given name
     * @param name name of parameter
     * @return parameter value
     */
    LogicState getParameter(String name);
}
