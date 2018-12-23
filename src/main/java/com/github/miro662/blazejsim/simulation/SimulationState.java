package com.github.miro662.blazejsim.simulation;

import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Input;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * State of simulation
 */
public final class SimulationState {
    private final HashMap<Connection, LogicState> logicStates;

    /**
     * Get simulation state for given connection
     * @param connection to get status for
     * @return Logic state for connection if it is specified in state, UNDEFINED if not
     */
    public LogicState getFor(@NotNull Connection connection) {
        return logicStates.getOrDefault(connection, LogicState.UNDEFINED);
    }

    /**
     * Get simulation state for given input
     * @param input to get status for
     * @return Input state for input's connection if it is specified in state, UNDEFINED if input has no connection
     */
    public LogicState getFor(@NotNull Input input) {
        Connection connection = input.getConnection();
        if (connection == null) {
            return LogicState.UNDEFINED;
        } else {
            return getFor(connection);
        }
    }

    SimulationState(HashMap<Connection, LogicState> logicStates) {
        this.logicStates = new HashMap<>(logicStates);
    }
}
