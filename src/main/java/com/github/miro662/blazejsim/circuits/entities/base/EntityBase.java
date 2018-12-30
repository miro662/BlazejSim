package com.github.miro662.blazejsim.circuits.entities.base;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * Interface describing what entity base should provide
 */
public interface EntityBase {
    @NotNull Stream<RegisteredEntity> search(String name);
    @NotNull Stream<RegisteredEntity> getAll();
    RegisteredEntity get(String name);
}
