package com.github.miro662.blazejsim.circuits.entities.custom.expression.parser;

import com.github.miro662.blazejsim.circuits.entities.custom.expression.Expression;

public class OperationInstance {
    private int at;
    private Operation operation;

    public OperationInstance(int at, Operation operation) {
        this.at = at;
        this.operation = operation;
    }

    public OperationInstance getOneWithLowerPriority(OperationInstance other) {
        if (operation == operation.getOneWithLowerPriority(other.operation)) {
            return this;
        } else {
            return other;
        }
    }

    public Expression build(Parser parser, String str, int from, int to) throws ParseException {
        Expression left = null;
        if (operation.type == Operation.Type.BINARY) {
            left = parser.parseFromTo(str, from, at);
        }

        Expression right = parser.parseFromTo(str, at + 1, to);

        return operation.builder.build(left, right);
    }
}
