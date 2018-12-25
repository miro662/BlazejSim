package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import org.jetbrains.annotations.NotNull;

/**
 * Describes single input of Entity
 */
public class Input extends Pin {
    public Input(@NotNull Entity entity) {
        super(entity);
    }

    @Override
    void connect(@NotNull Connection connection) {
        if (this.getConnection() != null)
            this.getConnection().disconnectInput(this);

        super.connect(connection);
    }

    @Override
    public LogicState evaluate(SimulationState previousState) {
        if (getConnection() == null)
            return LogicState.UNDEFINED;

        else return getConnection().evaluate(previousState);
    }
}
