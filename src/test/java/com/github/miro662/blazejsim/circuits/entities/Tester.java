package com.github.miro662.blazejsim.circuits.entities;

import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedList;
import java.util.List;

/**
 * Class providing easy way to test your logic gates
 */
public class Tester {
    private Entity entity;
    private SimulationStateBuilder builder;
    private SimulationState state;

    private List<Connection> inputConnections;
    private List<Connection> outputConnections;

    private Tester(Entity entity) {
        this.entity = entity;
        this.builder = new SimulationStateBuilder();
        this.state = SimulationState.empty();

        this.inputConnections = new LinkedList<>();
        entity.getInputs().forEach((input -> {
            Connection c = new Connection();
            c.connectInput(input);
            inputConnections.add(c);
        }));
        this.outputConnections = new LinkedList<>();
        entity.getOutputs().forEach((output -> {
            Connection c = new Connection();
            c.connectOutput(output);
            outputConnections.add(c);
        }));
    }

    /**
     * Creates tester for given entity
     * @param entity entity to create tester for
     * @return tester tester for given entity
     */
    public static Tester forEntity(Entity entity) {
        return new Tester(entity);
    }

    /**
     * Sets given input pin of entity to given logic value
     * @param inputName name of input to be set
     * @param state state to be set
     * @return Tester with input set
     */
    @NotNull
    public Tester set(String inputName, LogicState state) {
        Input toSet = entity.getInput(inputName);
        if (toSet == null) {
            throw new NullPointerException();
        }
        builder.addFor(toSet, state);
        return this;
    }

    /**
     * Runs simulation step using set values
     * @return Tester with simulation result saved
     */
    @NotNull
    public Tester simulate() {
        SimulationState builtState = builder.build();
        this.state = entity.step(builtState);
        return this;
    }

    /**
     * Asserts logic state of given output pin
     * @param outputName name of output pin to be checked
     * @param expected expected logic state of output pin
     * @return same tester
     */
    @NotNull
    public Tester assertOutput(String outputName, LogicState expected) {
        Output toCheck = entity.getOutput(outputName);
        if (toCheck == null) {
            throw new NullPointerException();
        }
        Assertions.assertEquals(expected, state.getFor(toCheck));
        return this;
    }
}
