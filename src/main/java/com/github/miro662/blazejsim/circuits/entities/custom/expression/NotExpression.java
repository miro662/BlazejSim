package com.github.miro662.blazejsim.circuits.entities.custom.expression;

import com.github.miro662.blazejsim.simulation.LogicState;

import java.io.Serializable;
import java.util.stream.Stream;

public class NotExpression implements Expression, Serializable {
    private Expression expr;

    public NotExpression(Expression expr) {
        this.expr = expr;
    }

    public NotExpression() {
    }

    @Override
    public LogicState evaluate(ParameterProvider parameters) {
        try {
            return LogicState.fromBoolean(
                    !expr.evaluate(parameters).getValue()
            );
        } catch (LogicState.UndefinedLogicStateException e) {
            return LogicState.UNDEFINED;
        }
    }

    @Override
    public Stream<String> getParameters() {
        return expr.getParameters();
    }
}
