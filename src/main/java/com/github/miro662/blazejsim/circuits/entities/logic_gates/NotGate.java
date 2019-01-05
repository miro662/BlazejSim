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

@RegisterEntity(name = "Logic Gates/NOT")
public class NotGate extends ClassEntity {
    @EntityInput(offset = 15)
    public Input a;

    @EntityOutput(offset = 15)
    public Output y;

    @NotNull
    @Override
    public SimulationState simulate(SimulationState state) throws LogicState.UndefinedLogicStateException {
        SimulationStateBuilder ssb = new SimulationStateBuilder();
        ssb.addFor(y, LogicState.fromBoolean(!state.getFor(a).getValue()));
        return ssb.build();
    }

    @Override
    public String getEntityPath() {
        return "/entities/not.png";
    }
}
