package com.github.miro662.blazejsim.circuits.entities.flip_flops;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.ClassEntity;
import com.github.miro662.blazejsim.circuits.entities.EntityInput;
import com.github.miro662.blazejsim.circuits.entities.EntityOutput;
import com.github.miro662.blazejsim.circuits.entities.helpers.EdgeDetector;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;

public class DFlipFlop extends ClassEntity {
    @EntityInput
    public Input d;

    @EntityInput
    public Input clk;

    @EntityOutput
    public Output q;

    @EntityOutput
    public Output notQ;

    private boolean status;
    private EdgeDetector clkEdge;

    public DFlipFlop() {
        super();
        status = false;
        clkEdge = EdgeDetector.forInput(clk);
    }

    @NotNull
    @Override
    protected synchronized SimulationState simulate(SimulationState oldState) throws LogicState.UndefinedLogicStateException {
        clkEdge.step(oldState);
        if (clkEdge.isRisingEdge()) {
            status = oldState.getFor(d).getValue();
        }

        SimulationStateBuilder ssb = new SimulationStateBuilder();
        ssb.addFor(q, LogicState.fromBoolean(status));
        ssb.addFor(notQ, LogicState.fromBoolean(!status));
        return ssb.build();
    }
}