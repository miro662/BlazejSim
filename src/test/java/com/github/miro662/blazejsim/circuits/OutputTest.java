package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OutputTest {
    Entity entity;
    Output output;
    Connection connection;

    @BeforeEach
    void initializeInput() {
        entity = mock(Entity.class);
        output = new Output(entity);
    }

    @BeforeEach
    void initializeConnection() {
        connection = mock(Connection.class);
    }

    @Test
    void connect() {
        output.connect(connection);
        assertEquals(connection, output.getConnection());
    }

    @Test
    void connectWhenConnected() {
        Connection old = mock(Connection.class);
        output.connect(old);
        output.connect(connection);
        verify(old).disconnectOutput();
        assertEquals(connection, output.getConnection());
    }
}
