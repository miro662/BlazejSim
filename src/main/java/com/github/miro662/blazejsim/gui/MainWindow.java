package com.github.miro662.blazejsim.gui;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.entities.base.EntityBase;
import com.github.miro662.blazejsim.circuits.entities.constants.Zero;
import com.github.miro662.blazejsim.gui.circuit.CircuitView;
import com.github.miro662.blazejsim.gui.circuit.Point;
import com.github.miro662.blazejsim.simulation.Simulation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.Optional;

public class MainWindow extends JFrame {
    private Circuit circuit;
    private EntityBase entityBase;

    private Optional<Simulation> simulation;
    private JButton playButton;
    private JButton stopButton;
    private JButton pauseButton;

    private EntityChooser entityChooser;
    private CircuitView circuitView;

    public MainWindow(Circuit circuit, EntityBase entityBase) {
        super("BlazejSim");
        this.circuit = circuit;
        this.entityBase = entityBase;
        this.simulation = Optional.empty();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(800, 600);

        JPanel topMenu = new JPanel(new BorderLayout());
        topMenu.setBackground(Parameters.menuColor);
        add(topMenu, BorderLayout.PAGE_START);

        topMenu.add(createSimulationMenu(), BorderLayout.LINE_END);
        topMenu.add(createFileMenu(), BorderLayout.LINE_START);

        entityChooser = new EntityChooser(entityBase);
        add(entityChooser, BorderLayout.LINE_START);

        circuitView = new CircuitView(circuit);
        add(circuitView, BorderLayout.CENTER);
        entityChooser.addEntityChooseListener(circuitView);
    }

    private JPanel createFileMenu() {
        JPanel fileMenu = new JPanel(new FlowLayout());

        JButton newFileButton = new JButton("New");
        newFileButton.addActionListener((e) -> initForCircuit(prepareNewCircuit()));
        fileMenu.add(newFileButton);

        JButton openFileButton = new JButton("Open");
        openFileButton.addActionListener((e) -> openFile());
        fileMenu.add(openFileButton);

        JButton saveFileButton = new JButton("Save");
        saveFileButton.addActionListener((e) -> saveFile());
        fileMenu.add(saveFileButton);

        fileMenu.setBackground(Parameters.menuColor);
        return fileMenu;
    }

    private JPanel createSimulationMenu() {
        JPanel simulationMenu = new JPanel(new FlowLayout());

        playButton = new JButton("Play");
        playButton.addActionListener((e) -> startSimulation());
        simulationMenu.add(playButton);

        pauseButton = new JButton("Pause");
        pauseButton.setEnabled(false);
        pauseButton.addActionListener((e) -> pauseSimulation());
        simulationMenu.add(pauseButton);

        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener((e) -> stopSimulation());
        simulationMenu.add(stopButton);

        simulationMenu.setBackground(Parameters.menuColor);
        return simulationMenu;
    }

    private void startSimulation() {
        stopSimulation();
        Simulation newSimulation = new Simulation(circuit);
        newSimulation.start();
        circuitView.addSimulation(newSimulation);
        simulation = Optional.of(newSimulation);

        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    private void stopSimulation() {
        simulation.ifPresent((simulation) -> {
            simulation.stop();
            circuitView.removeSimuation(simulation);
        });
        simulation = Optional.empty();

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
    }

    private void pauseSimulation() {
        simulation.ifPresent((Simulation::stop));

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("BlazejSim circuit", "bsim"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
                ObjectInputStream ois = new ObjectInputStream(fis);
                Circuit c = (Circuit) ois.readObject();
                ois.close();
                fis.close();

                initForCircuit(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("BlazejSim circuit", "bsim"));
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(circuit);
                oos.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: delete this method
    //It is necessary until I'll make circuit edition facilities, only once
    private Circuit prepareNewCircuit() {
        return new Circuit();
    }

    private void initForCircuit(Circuit circuit) {
        stopSimulation();
        this.circuit = circuit;
        circuitView.reset(this.circuit);
    }
}
