package com.github.miro662.blazejsim.circuits.entities.logic_gates;

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

@RegisterEntity(name = "Logic Gates/AND")
public class AndGate extends ClassEntity {
    @EntityInput(offset = -8)
    public Input a;

    @EntityInput(offset = 8)
    public Input b;

    @EntityOutput(offset = 0)
    public Output y;

    @NotNull
    @Override
    protected SimulationState simulate(SimulationState oldState) throws LogicState.UndefinedLogicStateException {
        SimulationStateBuilder ssb = new SimulationStateBuilder();
        ssb.addFor(y, LogicState.fromBoolean(oldState.getFor(a).getValue() && oldState.getFor(b).getValue()));
        return ssb.build();
    }

    @Override
    public String getEntityPath() {
        return "/entities/and.png";
    }
}
