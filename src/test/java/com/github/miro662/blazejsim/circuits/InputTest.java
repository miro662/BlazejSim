package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class InputTest {
    Entity entity;
    Input input;
    Connection connection;

    @BeforeEach
    void initializeInput() {
        entity = mock(Entity.class);
        input = new Input(entity);
    }

    @BeforeEach
    void initializeConnection() {
        connection = mock(Connection.class);
    }

    @Test
    void connect() {
        input.connect(connection);
        assertEquals(connection, input.getConnection());
    }

    @Test
    void connectWhenConnected() {
        Connection old = mock(Connection.class);
        input.connect(old);
        input.connect(connection);
        verify(old).disconnectInput(input);
        assertEquals(connection, input.getConnection());
    }

    @Test
    void evaluateForNoConnection() {
        assertNull(input.getConnection());
        SimulationState state = mock(SimulationState.class);
        assertEquals(LogicState.UNDEFINED, input.evaluate(state));
    }

    @Test
    void evaluate() {
        input.connect(connection);
        SimulationState state = mock(SimulationState.class);
        when(input.evaluate(state)).thenReturn(LogicState.HIGH);
        assertEquals(LogicState.HIGH, connection.evaluate(state));
    }
}
