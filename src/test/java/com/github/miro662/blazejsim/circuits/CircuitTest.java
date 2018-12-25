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
}
