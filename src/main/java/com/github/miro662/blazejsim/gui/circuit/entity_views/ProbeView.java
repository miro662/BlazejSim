package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.visible.Probe;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;

import java.awt.*;

public class ProbeView extends EntityView {
    @Override
    public void draw(Graphics2D g2d, int centerX, int centerY, SimulationState state) {
        Color color = state.getFor(getEntity().getInput("in")) == LogicState.HIGH ? Color.YELLOW : Color.WHITE;
        g2d.setColor(color);
        g2d.fillOval(centerX - 16, centerY - 16, 32, 32);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - 16, centerY - 16, 32, 32);

        drawPins(g2d, centerX, centerY);
    }

    public ProbeView(Entity entity) {
        super(entity);
    }
}
