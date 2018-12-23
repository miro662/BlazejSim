package com.github.miro662.blazejsim.circuits;

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
    void testConnect() {
        input.connect(connection);
        assertEquals(input.getConnection(), connection);
    }

    @Test
    void testConnectWhenConnected() {
        Connection old = mock(Connection.class);
        input.connect(old);
        input.connect(connection);
        verify(old).disconnectInput();
        assertEquals(input.getConnection(), connection);
    }
}
