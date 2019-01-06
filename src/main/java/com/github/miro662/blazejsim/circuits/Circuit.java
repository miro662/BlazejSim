package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.gui.circuit.Point;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Groups entities and forms a circuit from them
 */
public class Circuit implements Serializable {
    private List<Entity> entities;
    private List<Connection> connections;

    public Circuit() {
        entities = new LinkedList<>();
        connections = new LinkedList<>();
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
        entities.remove(entity);
    }

    /**
     * Return all entities
     * @return stream containing all entities
     */
    public Stream<Entity> getEntities() {
        return entities.stream();
    }

    private void checkInCircuit(@NotNull Pin pin) throws NotFromCircuitException {
        if (!entities.contains(pin.getEntity())) {
            throw new NotFromCircuitException(pin.getEntity());
        }
    }

    /**
     * Connects 2 given entities using connection
     *
     * If there is already something connected to output, connect input to it
     * If output is already connected to this input, keep this connection
     * If there is something connected to input, throw exception
     * If trying to connect pin from entity not from circuit, throw exception
     * @param output output to be connected to input
     * @param input input to be connected to output
     * @return new connection
     * @throws NotFromCircuitException trying to connect inputs/outputs which are not from this circuit
     * @throws  AlreadyConnectedInputException something is already connected to given input
     */
    @NotNull
    public Connection connect(Output output, Input input) throws NotFromCircuitException, AlreadyConnectedInputException {
        checkInCircuit(output);
        checkInCircuit(input);

        if (input.getConnection() != null) {
            throw new AlreadyConnectedInputException();
        }

        Optional<Connection> connectionOption = connections.stream().filter((connection -> connection.getOutput() == output)).findFirst();
        Connection connection = connectionOption.orElseGet(() -> {
            Connection newConnection = new Connection();
            newConnection.connectOutput(output);
            connections.add(newConnection);
            return newConnection;
        });

        connection.connectInput(input);

        return connection;
    }

    /**
     * Disconnects given output from all inputs and deletes related connection
     * @param output to be disconnected
     */
    public void disconnect(Output output) throws NotFromCircuitException {
        checkInCircuit(output);

        if (output.getConnection() == null) return;

        Connection connection = output.getConnection();
        connection.disconnectOutput();
        connection.disconnectInputs();
        connections.remove(connection);
    }

    /**
     * Disconnects given input, if no inputs connected to given connection deletes it too
     */
    public void disconnect(Input input) throws NotFromCircuitException {
        checkInCircuit(input);

        if (input.getConnection() == null) return;

        Connection connection = input.getConnection();
        connection.disconnectInput(input);
        if (connection.getInputs().isEmpty()) {
            connection.disconnectOutput();
            connections.remove(connection);
        }
    }

    /**
     * Get all connections in circuit
     * @return stream containing all connections
     */
    public Stream<Connection> getConnections() {
        return connections.stream();
    }

    /**
     * Get entity at given position
     * @param position expected entity position
     * @return Optional of entity if there is entity at given position, empty optional if it does not exist
     */
    public Optional<Entity> getEntityAt(Point position) {
        return getEntities().filter((entity -> entity.getPosition().equals(position))).findFirst();
    }

    public final class NotFromCircuitException extends Exception {
        public NotFromCircuitException(Entity entity) {
            this.entity = entity;
        }

        private final Entity entity;

        public Entity getEntity() {
            return entity;
        }
    }

    public class AlreadyConnectedInputException extends Exception {

    }
}
