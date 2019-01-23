package com.github.miro662.blazejsim.simulation;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.entities.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for running simulation
 */
public class Simulation {
    private Circuit circuit;
    private SimulationState currentState;
    private ForkJoinPool frpool;
    private Timer timer;

    private void initializeExecutor() {
        //TODO: adjust this
        this.frpool = ForkJoinPool.commonPool();
        timer = new Timer();
        notifiables = new LinkedList<>();
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
    public synchronized SimulationState next()  {
        SimulationTask task = SimulationTask.fromEntitiesList(circuit.getEntities(), currentState);
        currentState = this.frpool.invoke(task);
        notifiables.forEach((notifiable -> notifiable.notifyStep(currentState)));
        return currentState;
    }

    static class SimulationTask extends RecursiveTask<SimulationState> {
        private Stepable[] stepables;
        private int start;
        private int stop;

        private final int MAX_ENTITIES_PER_THREAD = 4;

        SimulationState oldState;

        @Override
        public SimulationState compute() {
            SimulationStateBuilder ssb = new SimulationStateBuilder();
            int no_of_entities = stop - start;
            if (no_of_entities <= MAX_ENTITIES_PER_THREAD) {
                for (int i = start; i < stop; ++i) {
                    SimulationState ss = stepables[i].step(oldState);
                    ssb.join(ss);
                }
            } else {
                SimulationTask firstTask = new SimulationTask(stepables, start, (no_of_entities / 2), oldState);
                SimulationTask secondTask = new SimulationTask(stepables, start + (no_of_entities / 2), stop, oldState);
                secondTask.fork();
                ssb.join(firstTask.compute());
                ssb.join(secondTask.join());
            }
            return ssb.build();
        }

        private SimulationTask(Stepable[] stepables, int start, int stop, SimulationState oldState) {
            this.stepables = stepables;
            this.start = start;
            this.stop = stop;
            this.oldState = oldState;
        }

        static SimulationTask fromEntitiesList(Stream<Entity> entities, SimulationState oldState) {
            Stepable[] stepables = entities.toArray(Stepable[]::new);
            return new SimulationTask(
                    stepables,
                    0,
                    stepables.length,
                    oldState
            );
        }
    }

    /**
     * Starts simulation
     */
    public synchronized void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    next();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10);
    }

    /**
     * Stops simulation
     */
    public synchronized void stop() {
        timer.cancel();
    }

    private List<StepNotifiable> notifiables;

    public void addStepNotifyable(@NotNull StepNotifiable notifiable) {
        notifiables.add(notifiable);
    }

    public void deleteStepNotifyable(@NotNull StepNotifiable notifiable) {
        notifiables.remove(notifiable);
    }
}
