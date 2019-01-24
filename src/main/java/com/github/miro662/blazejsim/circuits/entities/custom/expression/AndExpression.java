package com.github.miro662.blazejsim.circuits.entities.custom.expression;

import com.github.miro662.blazejsim.simulation.LogicState;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Expression realising AND
 */
public class AndExpression implements Expression, Serializable {
    private Expression left;
    private Expression right;

    public AndExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public AndExpression() {
    }

    @Override
    public LogicState evaluate(ParameterProvider parameters) {
        try {
            return LogicState.fromBoolean(
                    left.evaluate(parameters).getValue() && right.evaluate(parameters).getValue()
            );
        } catch (LogicState.UndefinedLogicStateException e) {
            return LogicState.UNDEFINED;
        }
    }

    @Override
    public Stream<String> getParameters() {
        return Stream.concat(left.getParameters(), right.getParameters()).distinct();
    }
}
