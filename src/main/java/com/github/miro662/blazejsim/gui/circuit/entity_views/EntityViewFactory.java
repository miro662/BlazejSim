package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.base.RegisteredEntity;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityView;

public class EntityViewFactory {
    public static EntityView forEntity(Entity entity) {
        return new TemporaryEntityView(entity);
    }
}
