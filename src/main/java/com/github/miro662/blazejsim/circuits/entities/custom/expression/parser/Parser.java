package com.github.miro662.blazejsim.circuits.entities.custom.expression.parser;

import com.github.miro662.blazejsim.circuits.entities.custom.expression.ConstantExpression;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.Expression;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.ParameterExpression;
import com.github.miro662.blazejsim.simulation.LogicState;

public class Parser {
    public Expression parse(String str) throws ParseException {
        return parseFromTo(str, 0, str.length());
    }

    private Expression parseFromTo(String str, int from, int to) throws ParseException {
        int length = to - from;
        if (length < 1) {
            throw new ParseException();
        }
        if (length == 1) {
            // constant or parameter
            return getConstant(str.charAt(from));
        }
        throw new ParseException();
    }

    private Expression getConstant(char character) throws ParseException {
        switch (character) {
            case '0':
                return new ConstantExpression(LogicState.LOW);
            case '1':
                return new ConstantExpression(LogicState.HIGH);
            case '?':
                return new ConstantExpression(LogicState.UNDEFINED);
            default:
                if (Character.isLowerCase(character)) {
                    return new ParameterExpression(Character.toString(character));
                } else {
                    throw new ParseException();
                }
        }
    }
}
