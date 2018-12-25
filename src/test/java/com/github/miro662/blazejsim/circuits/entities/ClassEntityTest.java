package com.github.miro662.blazejsim.circuits.entities;

import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.simulation.SimulationState;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClassEntityTest {
    class ClassEntityToTest extends ClassEntity {
        @EntityInput
        public Input in;

        @EntityInput
        public Input in2;

        @EntityOutput
        public Output out;

        @NotNull
        @Override
        protected SimulationState simulate(SimulationState oldState) {
            return SimulationState.empty();
        }
    }

    private ClassEntityToTest entity;

    @BeforeEach
    void initializeEntity() {
        entity = new ClassEntityToTest();
    }

    @Test
    void getInput() {
        assertEquals(entity.in, entity.getInput("in"));
        assertEquals(entity.in2, entity.getInput("in2"));
        assertNull(entity.getInput("non_existing"));
        assertNull(entity.getInput("out"));
    }

    @Test
    void getInputs() {
        assertTrue(entity.getInputs().anyMatch((x) -> x == entity.in));
        assertTrue(entity.getInputs().anyMatch((x) -> x == entity.in2));
    }

    @Test
    void getInputNames() {
        assertTrue(entity.getInputNames().anyMatch((x) -> x.equals("in")));
        assertTrue(entity.getInputNames().anyMatch((x) -> x.equals("in2")));
    }

    @Test
    void getOutput() {
        assertEquals(entity.out, entity.getOutput("out"));
        assertNull(entity.getOutput("non_existing"));
        assertNull(entity.getOutput("in"));
        assertNull(entity.getOutput("in2"));
    }

    @Test
    void getOutputs() {
        assertTrue(entity.getOutputs().anyMatch((x) -> x == entity.out));
    }

    @Test
    void getOutputNames() {
        assertTrue(entity.getOuptutNames().anyMatch((x) -> x.equals("out")));
    }
}
