package com.github.miro662.blazejsim.circuits;

import org.jetbrains.annotations.NotNull;

/**
 * Describes single input of Entity
 */
public class Input extends Pin {
    Input(@NotNull Entity entity) {
        super(entity);
    }

    @Override
    void connect(@NotNull Connection connection) {
        if (this.getConnection() != null)
            this.getConnection().disconnectInput();

        super.connect(connection);
    }
}
