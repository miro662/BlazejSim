package com.github.miro662.blazejsim.circuits;

import org.jetbrains.annotations.NotNull;

/**
 * Describes single output of Entity
 */
public class Output extends Pin {
    public Output(@NotNull Entity entity) {
        super(entity);
    }

    @Override
    void connect(@NotNull Connection connection) {
        if (this.getConnection() != null)
            this.getConnection().disconnectOutput();

        super.connect(connection);
    }
}
