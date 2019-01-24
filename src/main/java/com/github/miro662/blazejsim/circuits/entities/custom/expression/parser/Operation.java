package com.github.miro662.blazejsim.circuits.entities.custom.expression.parser;

/**
 * Describes operation that might be a part of expression
 */
public class Operation {
    private int priority;
    ExpressionBuilder builder;
    Type type;

    /**
     * Type of expression operation
     *
     * UNARY - unary operation
     * BINARY - binary operation
     */
    public enum Type {
        UNARY,
        BINARY
    }

    /**
     * Creates new Operation
     * @param priority Operator priority
     * @param builder ExpressionBuilder that returns corresponding Expression for left and right side parameters.
     *                For UNARY operations, left side is null.
     * @param type Operation type
     */
    public Operation(int priority, ExpressionBuilder builder, Type type) {
        this.priority = priority;
        this.builder = builder;
        this.type = type;
    }

    /**
     * Returns self if it has lower prority than other operation or other if it has lower priority than self
     * @param other other operation
     * @return operation with lower priority
     */
    public Operation getOneWithLowerPriority(Operation other) {
        if (other.priority < this.priority) {
            return other;
        } else {
            return this;
        }
    }

    /**
     * Create OperationInstance for operation at given index
     * @param at index in string where repective operator is
     * @return OperationInstance of this operator at given position
     */
    public OperationInstance getInstance(int at) {
        return new OperationInstance(at, this);
    }
}
