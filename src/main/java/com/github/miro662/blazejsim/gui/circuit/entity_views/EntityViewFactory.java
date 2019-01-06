package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;

public class EntityViewFactory {
    public static EntityView forEntity(Entity entity) {
        return entity.getEntityView();
    }
}
