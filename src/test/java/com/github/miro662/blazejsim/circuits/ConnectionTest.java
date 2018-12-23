package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
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
    void connectOutput() {
        Output output = mock(Output.class);
        connection.connectOutput(output);

        assertEquals(connection.getOutput(), output);
        verify(output).connect(connection);
    }

    @Test
    void connectOutputWhenOtherOutputConnected() {
        Output oldOutput = mock(Output.class);
        connection.connectOutput(oldOutput);
        Output newOutput = mock(Output.class);
        connection.connectOutput(newOutput);

        verify(oldOutput).disconnect();
        assertEquals(connection.getOutput(), newOutput);
        verify(newOutput).connect(connection);
    }

    @Test
    void disconnectOutput() {
        Output output = mock(Output.class);
        connection.connectOutput(output);
        connection.disconnectOutput();

        verify(output).disconnect();
        assertNull(connection.getOutput());
    }

    @Test
    void disconnectOutputWhenNoOutputConnected() {
        assertNull(connection.getOutput());
        connection.disconnectOutput();
    }

    @Test
    void connectInput() {
        Input input = mock(Input.class);
        connection.connectInput(input);

        assertTrue(connection.getInputs().contains(input));
        verify(input).connect(connection);
    }

    @Test
    void connectAnotherInput() {
        Input oldInput = mock(Input.class);
        connection.connectInput(oldInput);
        Input newInput = mock(Input.class);
        connection.connectInput(newInput);

        assertTrue(connection.getInputs().contains(oldInput));
        assertTrue(connection.getInputs().contains(newInput));
        verify(oldInput, never()).disconnect();
        verify(oldInput).connect(connection);
    }

    @Test
    void disconnectInput() {
        Input input = mock(Input.class);
        connection.connectInput(input);
        connection.disconnectInput(input);

        assertFalse(connection.getInputs().contains(input));
        verify(input).disconnect();
    }

    @Test
    void evaluateForNoOutput() {
        SimulationState state = mock(SimulationState.class);
        assertNull(connection.getOutput());
        assertEquals(LogicState.UNDEFINED, connection.evaluate(state));
    }

    @Test
    void evaluate() {
        Output output = mock(Output.class);
        SimulationState state = mock(SimulationState.class);
        connection.connectOutput(output);
        when(connection.evaluate(state)).thenReturn(LogicState.HIGH);

        assertEquals(LogicState.HIGH, connection.evaluate(state));
    }
}
