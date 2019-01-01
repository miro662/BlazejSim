package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CircuitTest {
    private Circuit circuit;

    @BeforeEach
    void initializeCircuit() {
        circuit = new Circuit();
    }

    @Test
    void addAndDeleteEntity() {
        Entity entity = mock(Entity.class);

        circuit.addEntity(entity);
        assertTrue(circuit.getEntities().anyMatch((e) -> e == entity));

        circuit.deleteEntity(entity);
        assertFalse(circuit.getEntities().anyMatch((e) -> e == entity));
        verify(entity).disconnectAll();
    }

    @Test
    void deleteNotAddedEntity() {
        Entity entity = mock(Entity.class);
        assertFalse(circuit.getEntities().anyMatch((e) -> e == entity));

        circuit.deleteEntity(entity);
        assertFalse(circuit.getEntities().anyMatch((e) -> e == entity));
    }

    @Test
    void connectThrowsWhenOutputNotFromCircuit() {
        Entity entity = mock(Entity.class);
        Output output = mock(Output.class);
        when(output.getEntity()).thenReturn(entity);

        Entity addedEntity = mock(Entity.class);
        Input input = mock(Input.class);
        when(input.getEntity()).thenReturn(addedEntity);
        circuit.addEntity(addedEntity);

        assertThrows(Circuit.NotFromCircuitException.class, () -> circuit.connect(output, input));

        when(output.getEntity()).thenReturn(addedEntity);
        when(input.getEntity()).thenReturn(entity);

        assertThrows(Circuit.NotFromCircuitException.class, () -> circuit.connect(output, input));
    }

    @Test
    void connectThrowsForAlreadyConnectedInput() {
        Entity entity = mock(Entity.class);
        circuit.addEntity(entity);

        Output output = mock(Output.class);
        when(output.getEntity()).thenReturn(entity);

        Input input = mock(Input.class);
        when(input.getEntity()).thenReturn(entity);
        Connection connection = mock(Connection.class);
        when(input.getConnection()).thenReturn(connection);

        assertThrows(Circuit.AlreadyConnectedInputException.class, () -> circuit.connect(output, input));
    }

    @Test
    void connect() throws Circuit.NotFromCircuitException, Circuit.AlreadyConnectedInputException {
        Entity entity1 = mock(Entity.class);
        Output output = mock(Output.class);
        when(output.getEntity()).thenReturn(entity1);
        circuit.addEntity(entity1);

        Entity entity2 = mock(Entity.class);
        Input input = mock(Input.class);
        when(input.getEntity()).thenReturn(entity2);
        circuit.addEntity(entity2);

        Connection connection = circuit.connect(output, input);
        assertEquals(connection.getOutput(), output);
        assertTrue(connection.getInputs().contains(input));
        assertTrue(circuit.getConnections().anyMatch(
                connection1 -> connection1.getOutput() == output && connection1.getInputs().contains(input)
        ));

        Entity entity3 = mock(Entity.class);
        Input input3 = mock(Input.class);
        when(input3.getEntity()).thenReturn(entity3);
        circuit.addEntity(entity3);

        Connection connection2 = circuit.connect(output, input3);
        assertEquals(connection2.getOutput(), output);
        assertTrue(connection2.getInputs().contains(input3));
        assertTrue(connection2.getInputs().contains(input));
        assertEquals(1, circuit.getConnections().count());
        assertEquals(connection, connection2);
    }

    @Test
    void disconnectOutputThrowsWhenNotInOutput() {
        Entity entity1 = mock(Entity.class);
        Output output = mock(Output.class);
        when(output.getEntity()).thenReturn(entity1);

        assertThrows(Circuit.NotFromCircuitException.class, () -> circuit.disconnect(output));
    }

    @Test
    void disconnectOutput() throws Circuit.NotFromCircuitException, Circuit.AlreadyConnectedInputException {
        Entity entity1 = mock(Entity.class);
        Output output = mock(Output.class);
        when(output.getEntity()).thenReturn(entity1);
        circuit.addEntity(entity1);

        Entity entity2 = mock(Entity.class);
        Input input = mock(Input.class);
        when(input.getEntity()).thenReturn(entity2);
        circuit.addEntity(entity2);

        Connection connection = circuit.connect(output, input);
        when(output.getConnection()).thenReturn(connection);
        when(input.getConnection()).thenReturn(connection);
        circuit.disconnect(output);

        verify(output).disconnect();
        verify(input).disconnect();
        assertEquals(0, circuit.getConnections().count());
    }

    @Test
    void disconnectInputThrowsWhenNotInOutput() {
        Entity entity1 = mock(Entity.class);
        Input input = mock(Input.class);
        when(input.getEntity()).thenReturn(entity1);

        assertThrows(Circuit.NotFromCircuitException.class, () -> circuit.disconnect(input));
    }

    @Test
    void disconnectInput() throws Circuit.NotFromCircuitException, Circuit.AlreadyConnectedInputException {
        Entity entity1 = mock(Entity.class);
        Output output = mock(Output.class);
        when(output.getEntity()).thenReturn(entity1);
        circuit.addEntity(entity1);

        Entity entity2 = mock(Entity.class);
        Input input = mock(Input.class);
        when(input.getEntity()).thenReturn(entity2);
        circuit.addEntity(entity2);

        Entity entity3 = mock(Entity.class);
        Input input2 = mock(Input.class);
        when(input2.getEntity()).thenReturn(entity3);
        circuit.addEntity(entity3);

        Connection c = circuit.connect(output, input);
        circuit.connect(output, input2);
        when(output.getConnection()).thenReturn(c);
        when(input.getConnection()).thenReturn(c);
        when(input2.getConnection()).thenReturn(c);

        circuit.disconnect(input2);

        assertTrue(c.getInputs().contains(input));
        assertFalse(c.getInputs().contains(input2));
        assertEquals(output, c.getOutput());
        assertEquals(1, circuit.getConnections().count());
        verify(input2).disconnect();
    }


    @Test
    void disconnectOnlyInput() throws Circuit.NotFromCircuitException, Circuit.AlreadyConnectedInputException {
        Entity entity1 = mock(Entity.class);
        Output output = mock(Output.class);
        when(output.getEntity()).thenReturn(entity1);
        circuit.addEntity(entity1);

        Entity entity2 = mock(Entity.class);
        Input input = mock(Input.class);
        when(input.getEntity()).thenReturn(entity2);
        circuit.addEntity(entity2);

        Connection c = circuit.connect(output, input);
        when(output.getConnection()).thenReturn(c);
        when(input.getConnection()).thenReturn(c);

        circuit.disconnect(input);

        assertEquals(0, circuit.getConnections().count());
        verify(output).disconnect();
        verify(input).disconnect();
    }
}
