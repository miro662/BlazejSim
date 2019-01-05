package com.github.miro662.blazejsim.circuits.entities.constants;

import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.ClassEntity;
import com.github.miro662.blazejsim.circuits.entities.EntityOutput;
import com.github.miro662.blazejsim.circuits.entities.base.RegisterEntity;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Entity that always return zero
 */
@RegisterEntity(name = "Constants/Zero")
public class Zero extends ClassEntity {
    @EntityOutput(offset = 0)
    public Output y;

    @NotNull
    @Override
    protected SimulationState simulate(SimulationState oldState) {
        SimulationStateBuilder ssb = new SimulationStateBuilder();
        ssb.addFor(y, LogicState.LOW);
        return ssb.build();
    }

    @Override
    public String getEntityPath() {
        return "/entities/Zero.png";
    }
}
