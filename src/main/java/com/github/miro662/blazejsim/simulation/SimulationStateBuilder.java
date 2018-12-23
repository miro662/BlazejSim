package com.github.miro662.blazejsim.simulation;

import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.Pin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Mutable builder for immutable simulation states
 */
public class SimulationStateBuilder {
    private HashMap<Connection, LogicState> logicStates;

    public void addFor(@NotNull Connection connection, LogicState state) {
        logicStates.put(connection, state);
    }

    public void addFor(@NotNull Pin pin, LogicState state) {
        if (pin.getConnection() != null) {
            logicStates.put(pin.getConnection(), state);
        }
    }

    public SimulationStateBuilder() {
        logicStates = new HashMap<>();
    }

    public SimulationState build() {
        return new SimulationState(logicStates);
    }
}
