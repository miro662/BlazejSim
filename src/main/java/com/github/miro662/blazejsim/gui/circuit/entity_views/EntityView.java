package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;

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
}
