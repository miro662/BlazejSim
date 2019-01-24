package com.github.miro662.blazejsim.circuits.entities.custom.expression;

import com.github.miro662.blazejsim.simulation.LogicState;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Expression which returns value of given parameter
 */
public class ParameterExpression implements Expression, Serializable {
    private String parameterName;

    public ParameterExpression(String parameterName) {
        this.parameterName = parameterName;
    }

    public ParameterExpression() {

    }

    @Override
    public LogicState evaluate(ParameterProvider parameters) {
        return parameters.getParameter(parameterName);
    }

    @Override
    public Stream<String> getParameters() {
        return Stream.of(parameterName);
    }
}
