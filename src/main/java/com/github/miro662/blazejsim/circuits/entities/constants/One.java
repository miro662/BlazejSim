package com.github.miro662.blazejsim.circuits.entities.constants;

import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.ClassEntity;
import com.github.miro662.blazejsim.circuits.entities.EntityOutput;
import com.github.miro662.blazejsim.circuits.entities.base.RegisterEntity;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Entity that always return one
 */
@RegisterEntity(name = "Constants/One")
public class One extends ClassEntity implements Serializable {
    @EntityOutput
    public Output y;

    @NotNull
    @Override
    protected SimulationState simulate(SimulationState oldState) {
        SimulationStateBuilder ssb = new SimulationStateBuilder();
        ssb.addFor(y, LogicState.HIGH);
        return ssb.build();
    }

    @Override
    public String getEntityPath() {
        return "/entities/One.png";
    }
}
