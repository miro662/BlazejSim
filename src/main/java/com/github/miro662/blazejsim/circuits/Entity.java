package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Describes single entity on circuit (e.g. logic gate, flip-flop)
 */
public abstract class Entity {
    /**
     * Get input pin with given name.
     * If input pin with given name does not exist, returns null
     * @param name name of pin
     * @return input pin with given name, otherwise null
     */
    @Nullable public abstract Input getInput(String name);

    /**
     * Get all input pins of this entity
     * @return input pins of this entity
     */
    @NotNull public abstract Iterable<Input> getInputs();

    /**
     * Get all names of input pins of this entity
     * @return names of input pins of this entity
     */
    @NotNull public abstract Iterable<String> getInputNames();

    /**
     * Get output pin with given name.
     * If output pin with given name does not exist, returns null
     * @param name name of pin
     * @return output in with given name, otherwise null
     */
    @Nullable public abstract Output getOutput(String name);

    /**
     * Get all output pins of this entity
     * @return output pins of this entity
     */
    @NotNull public abstract Iterable<Output> getOutputs();

    /**
     * Get all names of output pins of this entity
     * @return names of output pins of this entity
     */
    @NotNull public abstract Iterable<String> getOuptutNames();

    /**
     * Performs simulation step for given entity
     * @param oldState old simulation state
     * @return state describing changes made by this circuit
     */
    @NotNull
    public SimulationState step(SimulationState oldState) {
        try {
            return simulate(oldState);
        } catch (LogicState.UndefinedLogicStateException e) {
            return SimulationState.empty();
        }
    }

    @NotNull protected abstract SimulationState simulate(SimulationState oldState) throws LogicState.UndefinedLogicStateException;
}
