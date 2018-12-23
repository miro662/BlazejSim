package com.github.miro662.blazejsim.simulation;

import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationStateTest {
    private SimulationState state;

    private Connection connection;
    private Connection unknownConncetion;

    private Input input;
    private Input disconnectedInput;

    @BeforeEach
    void prepareSimulationState() {
        HashMap<Connection, LogicState> data = new HashMap<>();
        connection = mock(Connection.class);
        data.put(connection, LogicState.HIGH);
        unknownConncetion = mock(Connection.class);

        input = mock(Input.class);
        when(input.getConnection()).thenReturn(connection);
        disconnectedInput = mock(Input.class);
        when(disconnectedInput.getConnection()).thenReturn(null);

        state = new SimulationState(data);
    }

    @Test
    void stateFor() {
        assertEquals(LogicState.HIGH, state.getFor(connection));
        assertEquals(LogicState.UNDEFINED, state.getFor(unknownConncetion));

        assertEquals(LogicState.HIGH, state.getFor(input));
        assertEquals(LogicState.UNDEFINED, state.getFor(disconnectedInput));
    }
}
