package com.github.miro662.blazejsim.circuits.entities;

import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.gui.circuit.Point;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.ImageEntityView;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.Stepable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Describes single entity on circuit (e.g. logic gate, flip-flop)
 */
public abstract class Entity implements Stepable, Serializable {
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
    @NotNull public abstract Stream<Input> getInputs();

    /**
     * Get all names of input pins of this entity
     * @return names of input pins of this entity
     */
    @NotNull public abstract Stream<String> getInputNames();

    /**
     * Get name of given input
     * @param input input whose name should be retrieved
     * @return name of given input or ? if entity does not have such input
     */
    public String getInputName(Input input) {
        return getInputNames().filter(name -> getInput(name) == input).findFirst().orElse("?");
    }

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
    @NotNull public abstract Stream<Output> getOutputs();

    /**
     * Get all names of output pins of this entity
     * @return names of output pins of this entity
     */
    @NotNull public abstract Stream<String> getOuptutNames();

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

    /**
     * Disconnect all connections from entity
     */
    public void disconnectAll() {
        getInputs().forEach((input) -> {
            Connection connection = input.getConnection();
            if (connection != null) {
                connection.disconnectInput(input);
            }
        });
        getOutputs().forEach((output) -> {
            Connection connection = output.getConnection();
            if (connection != null) {
                connection.disconnectOutput();
            }
        });
    }

    private Point position;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Entity() {
        this.position = new Point(0, 0);
    }

    public EntityView getEntityView() {
        return new ImageEntityView(this, "/entities/unknown.png");
    }

    /**
     * Checks if entity is connected to anything
     * @return true if entity is connected to anything
     */
    public boolean isConnectedToAnything() {
        boolean anyInputConnected = getInputs().anyMatch(input -> input.getConnection() != null);
        boolean anyOutputConnected = getOutputs().anyMatch(output -> output.getConnection() != null);
        return anyInputConnected || anyOutputConnected;
    }
}
