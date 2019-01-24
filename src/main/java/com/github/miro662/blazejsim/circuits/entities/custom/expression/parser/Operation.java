package com.github.miro662.blazejsim.circuits.entities.custom.expression.parser;

public class Operation {
    private int priority;
    ExpressionBuilder builder;
    Type type;

    public enum Type {
        UNARY,
        BINARY
    }

    public Operation(int priority, ExpressionBuilder builder, Type type) {
        this.priority = priority;
        this.builder = builder;
        this.type = type;
    }

    public Operation getOneWithLowerPriority(Operation other) {
        if (other.priority < this.priority) {
            return other;
        } else {
            return this;
        }
    }

    public OperationInstance getInstance(int at) {
        return new OperationInstance(at, this);
    }
}
