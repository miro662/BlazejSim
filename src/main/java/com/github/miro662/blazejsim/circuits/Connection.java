package com.github.miro662.blazejsim.circuits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Connection implements Serializable {
    private Input input;
    private List<Output> outputs;

    Connection() {
        input = null;
        outputs = new ArrayList<>();
    }

    /**
     * Connect to given input.
     * If connection is already connected to input, disconnect it from it and connect new inputs
     * @param input to connect to
     */
    public void connectInput(@NotNull Input input) {
        if (this.input != null) {
            this.input.disconnect();
        }
        this.input = input;
        input.connect(this);
    }

    /**
     * Connect to given output.
     * If connection already is connected to output, it does not disconnect - it adds new connection
     * @param output to connect to
     */
    public void connectOutput(@NotNull Output output) {
        outputs.add(output);
        output.connect(this);
    }

    /**
     * Get input this connection is connected to
     * Return null if no input connected
     * @return connected input or null if no input connected
     */
    @Nullable
    public Input getInput() {
        return input;
    }

    /**
     * Get all inputs this connection is connected to
     * @return iterator with connection's outputs
     */
    @NotNull
    public List<Output> getOutputs() {
        return outputs;
    }

    /**
     * Disconnect input connected to this connection
     * If no input connected, does nothing
     */
    public void disconnectInput() {
        if (input != null) {
            input.disconnect();
            input = null;
        }
    }

    /**
     * Disconnect given output connected to this connection
     * If given output is not connected, disconnect it
     * @param output to discconect
     */
    public void disconnectOutput(@NotNull Output output) {
        if (outputs.contains(output)) {
            output.disconnect();
            outputs.remove(output);
        }
    }
}
