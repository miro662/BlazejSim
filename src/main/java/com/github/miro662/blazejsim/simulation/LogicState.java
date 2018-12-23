package com.github.miro662.blazejsim.simulation;

/**
 * Enum describing current logic state
 */
public enum LogicState {
    /**
     * High logic state
     */
    HIGH (true, true),
    /**
     * Low logic state
     */
    LOW (false, true),
    /**
     * Undefined (high-impedance) logic state
     */
    UNDEFINED (false, false);

    private boolean value;
    private boolean defined;

    LogicState(boolean value, boolean defined) {
        this.value = value;
        this.defined = defined;
    }

    /**
     * Creates logic state representing given boolean value (HIGH for true, LOW for false)
     * @param value to be represented as logic value
     * @return Logic state representing given value
     */
    public static LogicState fromBoolean(boolean value) {
        return value ? LogicState.HIGH : LogicState.LOW;
    }

    /**
     * Returns logic state as value if it is defined, otherwise mark it as undefined
     * @return logic value as boolean
     * @throws UndefinedLogicStateException when state is not boolean
     */
    public boolean getValue() throws UndefinedLogicStateException {
        if (!defined)
            throw new UndefinedLogicStateException();
        else
            return value;
    }

    /**
     * Is this logic state defined
     * @return True if defined, False if not
     */
    public boolean isDefined() {
        return this.defined;
    }

    /**
     * Was trying to get boolean value of undefined state
     */
    public class UndefinedLogicStateException extends Exception {
    }
}
