package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.gui.Parameters;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.gui.circuit.Point;

import java.awt.*;
import java.util.Optional;

public abstract class EntityView {
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public EntityView(Entity entity) {
        this.entity = entity;
    }

    public abstract void draw(Graphics2D g2d, int centerX, int centerY, SimulationState state);

    protected void drawPins(Graphics2D g2d, int centerX, int centerY) {
        drawPins(g2d, centerX, centerY, false);
    }

    protected void drawPins(Graphics2D g2d, int centerX, int centerY, boolean printNames) {
        g2d.setColor(Parameters.pinColor);

        entity.getInputs().forEach((input -> {
            int vertical = centerY + input.getOffset();
            int beginning = centerX - Parameters.getGateSize() / 2;
            int end = beginning - Parameters.pinSize;
            g2d.drawLine(beginning, vertical, end, vertical);
            if (printNames) {
                Font f = g2d.getFont();
                f.deriveFont(6.0f);
                g2d.setFont(f);
                g2d.drawString(entity.getInputName(input), beginning + 2, vertical + 4);
            }
        }));

        entity.getOutputs().forEach((input -> {
            int vertical = centerY + input.getOffset();
            int beginning = centerX + Parameters.getGateSize() / 2;
            int end = beginning + Parameters.pinSize;
            g2d.drawLine(beginning, vertical, end, vertical);
        }));
    }

    public boolean inEntity(Point offset) {
        return Math.abs(offset.getX()) < Parameters.getGateSize() / 2 && Math.abs(offset.getY()) < Parameters.getGateSize() / 2;
    }

    public void clicked(Point offset) {
        if (inEntity(offset)) {
            onClick(offset);
        }
    }

    protected void onClick(Point offset) {}

    public Optional<Output> getOutputPinAt(Point offset) {
        if (offset.getX() < Parameters.getGateSize() / 2) return Optional.empty();
        return getEntity().getOutputs().filter(output ->
                Math.abs(offset.getY() - output.getOffset()) <= Parameters.pinTolerance
        ).findFirst();
    }

    public Optional<Input> getInputPinAt(Point offset) {
        if (offset.getX() > - (Parameters.getGateSize() / 2)) return Optional.empty();
        return getEntity().getInputs().filter(input ->
                Math.abs(offset.getY() - input.getOffset()) <= Parameters.pinTolerance
        ).findFirst();
    }
}
