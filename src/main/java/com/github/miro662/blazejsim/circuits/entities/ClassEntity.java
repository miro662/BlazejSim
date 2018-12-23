package com.github.miro662.blazejsim.circuits.entities;


import com.github.miro662.blazejsim.circuits.Entity;
import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.simulation.LogicState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Base class of "class entities" - simple way to create your own entities
 */
public abstract class ClassEntity extends Entity {
    private HashMap<String, Input> inputs;
    private HashMap<String, Output> outputs;

    @Nullable
    @Override
    public Input getInput(String name) {
        return inputs.get(name);
    }

    @NotNull
    @Override
    public Iterable<Input> getInputs() {
        return inputs.values();
    }

    @NotNull
    @Override
    public Iterable<String> getInputNames() {
        return inputs.keySet();
    }

    @Nullable
    @Override
    public Output getOutput(String name) {
        return outputs.get(name);
    }

    @NotNull
    @Override
    public Iterable<Output> getOutputs() {
        return outputs.values();
    }

    @NotNull
    @Override
    public Iterable<String> getOuptutNames() {
        return outputs.keySet();
    }

    public ClassEntity() {
        initializeInputs();
        initializeOutputs();
    }

    private void initializeInputs() {
        inputs = new HashMap<>();
        // get all fields annotated as EntityInputs which are Inputs
        for (Field field : this.getClass().getFields()) {
            if (field.isAnnotationPresent(EntityInput.class) && field.getType() == Input.class) {
                // create new Input related to this Entity
                Input input = new Input(this);
                try {
                    // set this field to this input
                    field.set(this, input);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // add new input to inputs map
                inputs.put(field.getName(), input);
            }
        }
    }

    private void initializeOutputs() {
        outputs = new HashMap<>();
        // get all fields annotated as EntityInputs which are Outputs
        for (Field field : this.getClass().getFields()) {
            if (field.isAnnotationPresent(EntityOutput.class) && field.getType() == Output.class) {
                // create new Output related to this Entity
                Output output = new Output(this);
                try {
                    // set this field to this output
                    field.set(this, output);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // add new output to outputs map
                outputs.put(field.getName(), output);
            }
        }
    }
}
