package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.gui.Parameters;

import java.awt.*;

public final class TemporaryEntityView extends EntityView {
    @Override
    public void draw(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(centerX - 16, centerY - 16, Parameters.getGateSize(), Parameters.getGateSize());
    }

    public TemporaryEntityView(Entity entity) {
        super(entity);
    }
}
