package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.simulation.Evaluable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * Base class for all pins that can be parts of entity
 */
public class Pin implements Serializable, Evaluable {
    private Entity entity;
    private Connection connection;
    private int offset;

    public Pin(@NotNull Entity entity, int offset) {
        this.entity = entity;
        this.connection = null;
        this.offset = offset;
    }

    /**
     * Get entity this pin belongs to
     * @return entity this pin belongs to
     */
    @NotNull
    public Entity getEntity() {
        return entity;
    }
    void connect(@NotNull Connection connection) {
        this.connection = connection;
    }

    void disconnect() {
        this.connection = null;
    }

    /**
     * Get connection this pin is connected to
     * @return pin's connection or null if not connected
     */
    @Nullable
    public Connection getConnection() {
        return connection;
    }
}
