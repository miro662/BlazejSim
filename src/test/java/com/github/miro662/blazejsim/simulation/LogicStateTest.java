package com.github.miro662.blazejsim.simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicStateTest {
    @Test
    void fromBoolean() {
        assertEquals(LogicState.HIGH, LogicState.fromBoolean(true));
        assertEquals(LogicState.LOW, LogicState.fromBoolean(false));
    }

    @Test
    void getValue() throws LogicState.UndefinedLogicStateException {
        assertTrue(LogicState.HIGH.getValue());
        assertFalse(LogicState.LOW.getValue());
        assertThrows(LogicState.UndefinedLogicStateException.class, () -> LogicState.UNDEFINED.getValue());
    }

    @Test
    void isDefined() {
        assertTrue(LogicState.HIGH.isDefined());
        assertTrue(LogicState.LOW.isDefined());
        assertFalse(LogicState.UNDEFINED.isDefined());
    }
}
