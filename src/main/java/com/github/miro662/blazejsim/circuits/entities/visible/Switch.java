package com.github.miro662.blazejsim.circuits.entities.visible;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.ClassEntity;
import com.github.miro662.blazejsim.circuits.entities.EntityInput;
import com.github.miro662.blazejsim.circuits.entities.EntityOutput;
import com.github.miro662.blazejsim.circuits.entities.base.RegisterEntity;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.ImageEntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.ProbeView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.SwitchView;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;

@RegisterEntity(name = "Switch")
public class Switch extends ClassEntity {
    @EntityOutput(offset = 0)
    public Output output;

    private transient boolean state;

    public synchronized boolean getState() {
        return state;
    }

    public synchronized void toggleState() {
        state = !state;
    }

    @NotNull
    @Override
    protected SimulationState simulate(SimulationState oldState) throws LogicState.UndefinedLogicStateException {
        SimulationStateBuilder ssb = new SimulationStateBuilder();
        ssb.addFor(output, LogicState.fromBoolean(state));
        return ssb.build();
    }

    @NotNull
    @Override
    public EntityView getEntityView() {
        return new SwitchView(this);
    }
}
