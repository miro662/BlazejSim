package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.simulation.Evaluable;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes connection between 2 pins
 */
public class Connection implements Serializable, Evaluable {
    private List<Input> inputs;
    private Output output;

    Connection() {
        inputs = new ArrayList<>();
        output = null;
    }

    /**
     * Connect to given output.
     * If connection is already connected to output, disconnect it from it and connect new output
     * @param output to connect to
     */
    public void connectOutput(@NotNull Output output) {
        if (this.output != null) {
            this.output.disconnect();
        }
        this.output = output;
        output.connect(this);
    }

    /**
     * Connect to given output.
     * If connection already is connected to output, it does not disconnect - it adds new connection
     * @param input to connect to
     */
    public void connectInput(@NotNull Input input) {
        inputs.add(input);
        input.connect(this);
    }

    /**
     * Get output this connection is connected to
     * Return null if no output connected
     * @return connected output or null if no output connected
     */
    @Nullable
    public Output getOutput() {
        return output;
    }

    /**
     * Get all inputs this connection is connected to
     * @return iterator with connection's outputs
     */
    @NotNull
    public List<Input> getInputs() {
        return inputs;
    }

    /**
     * Disconnect output connected to this connection
     * If no output connected, does nothing
     */
    public void disconnectOutput() {
        if (output != null) {
            output.disconnect();
            output = null;
        }
    }

    /**
     * Disconnect given input connected to this connection
     * If given input is not connected, disconnect it
     * @param input to discconect
     */
    public void disconnectInput(@NotNull Input input) {
        if (inputs.contains(input)) {
            input.disconnect();
            inputs.remove(input);
        }
    }

    @Override
    public LogicState evaluate(SimulationState previousState) {
        if (output != null) {
            return output.evaluate(previousState);
        } else {
            return LogicState.UNDEFINED;
        }
    }
}
