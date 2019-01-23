package com.github.miro662.blazejsim.gui;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.gui.circuit.CircuitView;
import com.github.miro662.blazejsim.simulation.Simulation;

import javax.swing.*;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class SimulationControl extends JPanel {
    private JButton playButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton stepButton;
    private JLabel statusLabel;

    private Circuit circuit;
    private CircuitView circuitView;
    private Optional<Simulation> simulation;

    private SimulationStatus simulationStatus;

    SimulationControl(Circuit circuit, CircuitView circuitView) {
        this.circuit = circuit;
        this.circuitView = circuitView;

        statusLabel = new JLabel("Off");
        this.add(statusLabel);

        stepButton = new JButton("Step");
        stepButton.addActionListener((e) -> stepSimulation());
        this.add(stepButton);

        playButton = new JButton("Play");
        playButton.addActionListener((e) -> startSimulation());
        this.add(playButton);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener((e) -> pauseSimulation());
        this.add(pauseButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener((e) -> stopSimulation());
        this.add(stopButton);

        this.setBackground(Parameters.menuColor);
        this.simulation = Optional.empty();
        this.setSimulationStatus(SimulationStatus.OFF);
    }

    public enum SimulationStatus {
        OFF,
        ON,
        PAUSED
    }

    public SimulationStatus getSimulationStatus() {
        return this.simulationStatus;
    }

    private void setSimulationStatus(SimulationStatus status) {
        this.simulationStatus = status;

        switch (this.simulationStatus) {
            case ON:
                playButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);
                stepButton.setEnabled(false);
                statusLabel.setText("ON");
                break;
            case OFF:
                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
                stepButton.setEnabled(false);
                statusLabel.setText("OFF");
                break;
            case PAUSED:
                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(true);
                stepButton.setEnabled(true);
                statusLabel.setText("PAUSED");
                break;
        }
    }

    private void startSimulation() {
        stopSimulation();
        Simulation newSimulation = new Simulation(circuit);
        newSimulation.start();
        circuitView.addSimulation(newSimulation);
        simulation = Optional.of(newSimulation);
        this.setSimulationStatus(SimulationStatus.ON);
    }

    public void stopSimulation() {
        simulation.ifPresent((simulation) -> {
            simulation.stop();
            circuitView.removeSimuation(simulation);
        });
        simulation = Optional.empty();
        this.setSimulationStatus(SimulationStatus.OFF);
    }

    private void pauseSimulation() {
        simulation.ifPresent((Simulation::stop));

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
        stepButton.setEnabled(true);
        this.setSimulationStatus(SimulationStatus.PAUSED);
    }

    private void stepSimulation() {
        simulation.ifPresent((Simulation::next));
    }

    void initForCircuit(Circuit circuit) {
        this.circuit = circuit;
    }
}
