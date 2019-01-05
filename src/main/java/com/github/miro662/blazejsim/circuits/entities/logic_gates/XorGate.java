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

@RegisterEntity(name = "Logic Gates/XOR")
public class XorGate extends ClassEntity {
    @EntityInput
    public Input a;

    @EntityInput
    public Input b;

    @EntityOutput
    public Output y;

    @NotNull
    @Override
    protected SimulationState simulate(SimulationState oldState) throws LogicState.UndefinedLogicStateException {
        SimulationStateBuilder ssb = new SimulationStateBuilder();
        // XOR is true if only one value is HIGH
        ssb.addFor(y, LogicState.fromBoolean(oldState.getFor(a).getValue() != oldState.getFor(b).getValue()));
        return ssb.build();
    }

    @Override
    public String getEntityPath() {
        return "/entities/xor.png";
    }
}
