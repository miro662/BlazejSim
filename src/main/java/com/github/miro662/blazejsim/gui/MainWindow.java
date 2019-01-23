package com.github.miro662.blazejsim.gui;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.entities.base.EntityBase;
import com.github.miro662.blazejsim.gui.circuit.CircuitView;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    Circuit circuit;

    private EntityChooser entityChooser;
    private CircuitView circuitView;
    private SimulationControl simulationControl;
    private FileControls fileControls;

    public MainWindow(Circuit circuit, EntityBase entityBase) {
        super("BlazejSim");
        this.circuit = circuit;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(800, 600);

        JPanel topMenu = new JPanel(new BorderLayout());
        topMenu.setBackground(Parameters.menuColor);
        add(topMenu, BorderLayout.PAGE_START);

        circuitView = new CircuitView(circuit);
        add(circuitView, BorderLayout.CENTER);

        this.simulationControl = new SimulationControl(circuit, circuitView);
        topMenu.add(this.simulationControl, BorderLayout.LINE_END);

        this.fileControls = new FileControls(this);
        topMenu.add(this.fileControls, BorderLayout.LINE_START);

        entityChooser = new EntityChooser(entityBase);
        add(entityChooser, BorderLayout.LINE_START);

        entityChooser.addEntityChooseListener(circuitView);
    }

    void initForCircuit(Circuit circuit) {
        simulationControl.stopSimulation();
        this.circuit = circuit;
        circuitView.reset(this.circuit);
        simulationControl.initForCircuit(circuit);
    }
}
