package com.github.miro662.blazejsim.circuits.entities.base;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * Interface describing what entity base should provide
 */
public interface EntityBase {
    /**
     * Get all entities that can be provided
     * @return all provided entities
     */
    @NotNull Stream<RegisteredEntity> getAll();

    /**
     * Get entity with given name
     * @param name name of desired entity
     * @return desired entity or null if there is no entity with given name
     */
    RegisteredEntity get(String name);
}
