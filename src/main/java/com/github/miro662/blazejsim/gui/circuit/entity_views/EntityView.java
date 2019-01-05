package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.gui.Parameters;

import java.awt.*;

public abstract class EntityView {
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public EntityView(Entity entity) {
        this.entity = entity;
    }

    public abstract void draw(Graphics2D g2d, int centerX, int centerY);

    protected void drawPins(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(Parameters.pinColor);

        entity.getInputs().forEach((input -> {
            int vertical = centerY + input.getOffset();
            int beginning = centerX - Parameters.getGateSize() / 2;
            int end = beginning - Parameters.pinSize;
            g2d.drawLine(beginning, vertical, end, vertical);
        }));

        entity.getOutputs().forEach((input -> {
            int vertical = centerY + input.getOffset();
            int beginning = centerX + Parameters.getGateSize() / 2;
            int end = beginning + Parameters.pinSize;
            g2d.drawLine(beginning, vertical, end, vertical);
        }));
    }
}
