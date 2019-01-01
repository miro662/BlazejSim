package com.github.miro662.blazejsim.circuits.entities;


import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Base class of "class entities" - simple way to create your own entities.
 *
 * ClassEntity automatically uses fields:
 * - of type Input, annotated with @EntityInput as entity inputs
 * - of type Output, annotated with @EntityOutput as entity outputs
 */
public abstract class ClassEntity extends Entity {
    private HashMap<String, Input> inputs;
    private HashMap<String, Output> outputs;

    @Nullable
    @Override
    public final Input getInput(String name) {
        return inputs.get(name);
    }

    @NotNull
    @Override
    public final Stream<Input> getInputs() {
        return inputs.values().stream();
    }

    @NotNull
    @Override
    public final Stream<String> getInputNames() {
        return inputs.keySet().stream();
    }

    @Nullable
    @Override
    public final Output getOutput(String name) {
        return outputs.get(name);
    }

    @NotNull
    @Override
    public final Stream<Output> getOutputs() {
        return outputs.values().stream();
    }

    @NotNull
    @Override
    public final Stream<String> getOuptutNames() {
        return outputs.keySet().stream();
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
