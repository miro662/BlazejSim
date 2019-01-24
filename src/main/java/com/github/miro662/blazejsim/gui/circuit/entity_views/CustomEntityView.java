package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.custom.CustomEntity;
import com.github.miro662.blazejsim.gui.circuit.Point;
import com.github.miro662.blazejsim.simulation.SimulationState;

import java.awt.*;

public final class CustomEntityView extends EntityView {
    CustomEntity entity;

    @Override
    public void draw(Graphics2D g2d, int centerX, int centerY, SimulationState state) {
        g2d.setColor(Color.BLACK);
        // draw entity itself
        g2d.drawRect(centerX - 16, centerY - 16, 32, 32);

        Font f = g2d.getFont();
        f = f.deriveFont(12.0f);
        g2d.drawString("!", centerX + 10, centerY + 4);

        drawPins(g2d, centerX, centerY, true);
    }

    public CustomEntityView(CustomEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    protected void onClick(Point offset) {
        EventQueue.invokeLater(() -> new ExpressionWindow(this.entity));
    }
}
