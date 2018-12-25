package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Groups entities and forms a circuit from them
 */
public class Circuit implements Serializable {
    private List<Entity> entities;

    public Circuit() {
        entities = new LinkedList<>();
    }

    /**
     * Add entity to circuit
     * @param entity entity to be added
     */
    public void addEntity(@NotNull Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    /**
     * Delete entity from circuit
     * @param entity entity to be removed
     */
    public void deleteEntity(@NotNull Entity entity) {
        entity.disconnectAll();
        if (entities.contains(entity)) {
            entities.remove(entity);
        }
    }

    /**
     * Return all entities
     * @return stream containing all entities
     */
    public Stream<Entity> getEntities() {
        return entities.stream();
    }
}
