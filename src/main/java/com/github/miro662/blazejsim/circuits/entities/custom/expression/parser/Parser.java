package com.github.miro662.blazejsim.circuits.entities.custom.expression.parser;

import com.github.miro662.blazejsim.circuits.entities.custom.expression.*;
import com.github.miro662.blazejsim.simulation.LogicState;

import java.util.HashMap;

/**
 * Parses strings into expressions
 */
public class Parser {
    public HashMap<Character, Operation> operations;

    public Parser() {
        operations = new HashMap<>();

        operations.put('&', new Operation(1, AndExpression::new, Operation.Type.BINARY));
        operations.put('|', new Operation(1, OrExpression::new, Operation.Type.BINARY));
        operations.put('~', new Operation(2, ((left, right) -> new NotExpression(right)), Operation.Type.UNARY));
    }

    /**
     * Parse string into expression.
     *
     * Expression string syntax (by priority):
     * underscore letters - parameters (ParameterExpression)
     * 0/1/? - constants
     * ~_ - NOT
     * _&and;_ - AND
     * _|_ - OR
     *
     * You can use parentheses to modify priority.
     * Whitespaces are not allowed.
     *
     * @param str expression string
     * @return expression build during parsing
     * @throws ParseException when string is not correct
     */
    public Expression parse(String str) throws ParseException {
        return parseFromTo(str, 0, str.length());
    }

    Expression parseFromTo(String str, int from, int to) throws ParseException {
        int length = to - from;
        if (length < 1) {
            throw new ParseException("Trying to parse empty expression", from);
        }
        if (length == 1) {
            // constant or parameter
            return getConstant(str.charAt(from));
        }

        OperationInstance operationInstance = null;

        int parenthesesCount = 0;
        Boolean inBrackets = true;

        for (int i = from; i < to; ++i) {
            char c = str.charAt(i);
            if (c == '(') {
                parenthesesCount++;
            } else if (c == ')') {
                parenthesesCount--;
                if (parenthesesCount == 0 && i != to - 1)
                    inBrackets = false;
                if (parenthesesCount < 0) {
                    throw new ParseException("Cannot find corresponding opening parenthesis for closing one", i);
                }
            } else if (Character.isLetterOrDigit(c)) {
                // skip in such case - constant/parameter handled in other place
            } else if (parenthesesCount == 0) {
                Operation operation = operations.get(c);
                if (operation != null) {
                    OperationInstance proposed = operation.getInstance(i);
                    if (operationInstance != null) {
                        operationInstance = operationInstance.getOneWithLowerPriority(proposed);
                    } else {
                        operationInstance = proposed;
                    }
                }
            }
        }

        if (parenthesesCount != 0) {
            throw new ParseException("Unclosed parentheses", from);
        }

        if (inBrackets && str.charAt(from) == '(' && str.charAt(to - 1) == ')') {
            return parseFromTo(str, from + 1, to - 1);
        }

        if (operationInstance != null) {
            return operationInstance.build(this, str, from, to);
        } else {
            throw new ParseException("Wrong operation", from);
        }
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
                    throw new ParseException("TODO: clean", -1);
                }
        }
    }
}
