package com.github.miro662.blazejsim.circuits.entities.custom.expression;

import com.github.miro662.blazejsim.simulation.LogicState;

public interface ParameterProvider {
    LogicState getParameter(String name);
}
