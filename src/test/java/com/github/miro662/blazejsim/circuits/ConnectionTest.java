package com.github.miro662.blazejsim.circuits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectionTest {
    Connection connection;

    @BeforeEach
    void initializeConnection() {
        connection = new Connection();
    }

    @Test
    public void testConnectInput() {
        Input input = mock(Input.class);
        connection.connectInput(input);

        assertEquals(connection.getInput(), input);
        verify(input).connect(connection);
    }

    @Test
    public void testConnectInputWhenOtherInputConnected() {
        Input oldInput = mock(Input.class);
        connection.connectInput(oldInput);
        Input newInput = mock(Input.class);
        connection.connectInput(newInput);

        verify(oldInput).disconnect();
        assertEquals(connection.getInput(), newInput);
        verify(newInput).connect(connection);
    }

    @Test
    public void testDisconnectInput() {
        Input input = mock(Input.class);
        connection.connectInput(input);
        connection.disconnectInput();

        verify(input).disconnect();
        assertNull(connection.getInput());
    }

    @Test
    public void testDisconnectInputWhenNoInputConnected() {
        assertNull(connection.getInput());
        connection.disconnectInput();
    }

    @Test
    public void testConnectOutput() {
        Output output = mock(Output.class);
        connection.connectOutput(output);

        assertTrue(connection.getOutputs().contains(output));
        verify(output).connect(connection);
    }

    @Test
    public void testConnectAnotherOutput() {
        Output oldOutput = mock(Output.class);
        connection.connectOutput(oldOutput);
        Output newOutput = mock(Output.class);
        connection.connectOutput(newOutput);

        assertTrue(connection.getOutputs().contains(oldOutput));
        assertTrue(connection.getOutputs().contains(newOutput));
        verify(oldOutput, never()).disconnect();
        verify(newOutput).connect(connection);
    }

    @Test
    public void testDisconnectOutput() {
        Output output = mock(Output.class);
        connection.connectOutput(output);
        connection.disconnectOutput(output);

        assertFalse(connection.getOutputs().contains(output));
        verify(output).disconnect();
    }
}
