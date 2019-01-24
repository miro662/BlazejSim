package com.github.miro662.blazejsim.circuits.entities.custom.expression.parser;

public class ParseException extends Exception {
    private int at;
    public ParseException(String message, int at) {
        super(message);
        this.at = at;
    }

    public int getAt() {
        return at;
    }
}
