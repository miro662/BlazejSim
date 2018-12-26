package com.github.miro662.blazejsim.circuits.entities.base;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RegisteredEntityTest {
    @RegisterEntity(name = "Test")
    public static class AnnotatedEntity extends Entity {

        @Nullable
        @Override
        public Input getInput(String name) {
            return null;
        }

        @NotNull
        @Override
        public Stream<Input> getInputs() {
            return null;
        }

        @NotNull
        @Override
        public Stream<String> getInputNames() {
            return null;
        }

        @Nullable
        @Override
        public Output getOutput(String name) {
            return null;
        }

        @NotNull
        @Override
        public Stream<Output> getOutputs() {
            return null;
        }

        @NotNull
        @Override
        public Stream<String> getOuptutNames() {
            return null;
        }

        @NotNull
        @Override
        protected SimulationState simulate(SimulationState oldState) throws LogicState.UndefinedLogicStateException {
            return null;
        }
    }

    @Test
    void fromAnnotatedEntity() throws RegisteredEntity.UnannotatedEntityException {
        RegisteredEntity re = RegisteredEntity.fromAnnotatedEntity(AnnotatedEntity.class);

        assertEquals(AnnotatedEntity.class, re.getEntityClass());
    }

    @Test
    void fromAnnotatedEntityThrowsWhenUnannotatedEntityProvided() {
        assertThrows(RegisteredEntity.UnannotatedEntityException.class, () -> {
            RegisteredEntity.fromAnnotatedEntity(Entity.class);
        });
    }

    @Test
    void create() throws RegisteredEntity.UnannotatedEntityException {
        RegisteredEntity re = RegisteredEntity.fromAnnotatedEntity(AnnotatedEntity.class);
        assertEquals(AnnotatedEntity.class, re.getEntityClass());
        assertTrue(re.create() instanceof AnnotatedEntity);
    }
}
