package com.github.miro662.blazejsim.circuits.entities.flip_flops;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.ClassEntity;
import com.github.miro662.blazejsim.circuits.entities.EntityInput;
import com.github.miro662.blazejsim.circuits.entities.EntityOutput;
import com.github.miro662.blazejsim.circuits.entities.base.RegisterEntity;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;

@RegisterEntity(name = "FlipFlops/RS")
public class RSFlipFlop extends ClassEntity {
    @EntityInput(offset = -8)
    public Input r;

    @EntityInput(offset = 8)
    public Input s;

    @EntityOutput(offset = -8)
    public Output q;

    @EntityOutput(offset = 8)
    public Output notQ;

    private boolean status;

    @NotNull
    @Override
    protected synchronized SimulationState simulate(SimulationState oldState) throws LogicState.UndefinedLogicStateException {
        if (oldState.getFor(s).getValue()) {
            status = true;
        } else if (oldState.getFor(r).getValue()) {
            status = false;
        }

        SimulationStateBuilder ssb = new SimulationStateBuilder();
        ssb.addFor(q, LogicState.fromBoolean(status));
        ssb.addFor(notQ, LogicState.fromBoolean(!status));

        return ssb.build();
    }

    @Override
    public String getEntityPath() {
        return "/entities/RS.png";
    }
}
