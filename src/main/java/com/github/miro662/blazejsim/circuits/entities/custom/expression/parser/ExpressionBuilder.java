package com.github.miro662.blazejsim.circuits.entities.custom.expression.parser;

import com.github.miro662.blazejsim.circuits.entities.custom.expression.Expression;

/**
 * Interface describing expression that can be built from 2 other ones
 */
@FunctionalInterface
public interface ExpressionBuilder {
    /**
     * Build expression from 2 parameters
     * @param left left-side parameter
     * @param right right-side parameter
     * @return built expression
     * @throws ParseException error in parsing parameters
     */
    Expression build(Expression left, Expression right) throws ParseException;
}
