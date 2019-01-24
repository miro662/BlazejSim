package com.github.miro662.blazejsim.circuits.entities.custom.expression.parser;

import com.github.miro662.blazejsim.circuits.entities.custom.expression.Expression;

public interface ExpressionBuilder {
    Expression build(Expression left, Expression right) throws ParseException;
}
