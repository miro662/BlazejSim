package com.github.miro662.blazejsim.simulation;

import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Pin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Mutable builder for immutable simulation states
 */
public class SimulationStateBuilder {
    private HashMap<Connection, LogicState> logicStates;

    /**
     * Adds state of given connection to built simulation state
     * @param connection connection of which state will be added
     * @param state state to add for given connection
     */
    public void addFor(@NotNull Connection connection, LogicState state) {
        logicStates.put(connection, state);
    }

    /**
     * Adds state of connection connected to this pin
     * If no connection connected, do nothing
     * @param pin pin of which connection's state will be aded
     * @param state state to add for given connection
     */
    public void addFor(@NotNull Pin pin, LogicState state) {
        if (pin.getConnection() != null) {
            logicStates.put(pin.getConnection(), state);
        }
    }

    public SimulationStateBuilder() {
        logicStates = new HashMap<>();
    }

    /**
     * Build simulation state using added connection states
     * @return simulation status with added states for connections
     */
    public SimulationState build() {
        return new SimulationState(logicStates);
    }
}
