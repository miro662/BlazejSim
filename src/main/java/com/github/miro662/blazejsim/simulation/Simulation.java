package com.github.miro662.blazejsim.simulation;

import com.github.miro662.blazejsim.circuits.Circuit;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for running simulation
 */
public class Simulation {
    private Circuit circuit;
    private SimulationState currentState;
    private ExecutorService simulationExecutor;

    private void initializeExecutor() {
        //TODO: adjust this
        this.simulationExecutor = Executors.newFixedThreadPool(2);
    }

    /**
     * Creates simulation for given circuit with given initial state
     * @param circuit circuit to simulate
     * @param initialState circuit's initial state
     */
    public Simulation(@NotNull Circuit circuit, @NotNull SimulationState initialState) {
        this.circuit = circuit;
        this.currentState = initialState;
        initializeExecutor();
    }

    /**
     * Creates simulation for given circuit
     * Uses undefined state for all connections
     * @param circuit circuit to simulate
     */
    public Simulation(@NotNull Circuit circuit) {
        this.circuit = circuit;
        this.currentState = SimulationState.empty();
        initializeExecutor();
    }

    /**
     * Return current simulation state
     * @return current simulation state
     */
    public synchronized SimulationState getCurrentState() {
        return this.currentState;
    }

    /**
     * Simulates 1 execution step
     * @return next execution step
     */
    public synchronized SimulationState next() throws InterruptedException, ExecutionException {
        // could be written using parallel streams, used threadpools for learning reasons
        SimulationStateBuilder ssb = new SimulationStateBuilder();

        Stream<SimulationTask> simulationTasks = circuit.getEntities().map((entity -> new SimulationTask(entity, currentState)));
        Collection<SimulationTask> simulationTasksCollection = simulationTasks.collect(Collectors.toList());
        List<Future<SimulationState>> futures = simulationExecutor.invokeAll(simulationTasksCollection);
        for (Future<SimulationState> future : futures) {
            ssb.join(future.get());
        }

        currentState = ssb.build();
        return currentState;
    }

    class SimulationTask implements Callable<SimulationState> {
        Stepable toSimulate;
        SimulationState oldState;

        @Override
        public SimulationState call() {
            return toSimulate.step(oldState);
        }

        SimulationTask(Stepable toSimulate, SimulationState oldState) {
            this.toSimulate = toSimulate;
            this.oldState = oldState;
        }
    }
}
