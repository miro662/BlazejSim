package com.github.miro662.blazejsim.circuits.entities.visible;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.entities.ClassEntity;
import com.github.miro662.blazejsim.circuits.entities.EntityInput;
import com.github.miro662.blazejsim.circuits.entities.base.RegisterEntity;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.ImageEntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.ProbeView;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;

@RegisterEntity(name = "Probe")
public class Probe extends ClassEntity {
    @EntityInput(offset = 0)
    public Input in;

    @NotNull
    @Override
    protected SimulationState simulate(SimulationState oldState) throws LogicState.UndefinedLogicStateException {
        return SimulationState.empty();
    }

    @NotNull
    @Override
    public EntityView getEntityView() {
        return new ProbeView(this);
    }
}
