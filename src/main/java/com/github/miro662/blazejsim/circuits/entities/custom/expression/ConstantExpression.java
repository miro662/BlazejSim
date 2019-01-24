package com.github.miro662.blazejsim.circuits.entities.custom.expression;

import com.github.miro662.blazejsim.simulation.LogicState;

import java.io.Serializable;

/**
 * Expression returning given constant
 */
public class ConstantExpression implements Expression, Serializable {
    private LogicState state;

    /**
     * Create constant expression with given state
     * @param state constant state of expression
     */
    public ConstantExpression(LogicState state) {
        this.state = state;
    }

    public ConstantExpression() {
        this.state = LogicState.UNDEFINED;
    }

    @Override
    public LogicState evaluate(ParameterProvider parameters) {
        return state;
    }
}
