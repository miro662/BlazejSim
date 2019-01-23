package com.github.miro662.blazejsim.gui;

import com.github.miro662.blazejsim.circuits.Circuit;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;

public class FileControls extends JPanel {
    private MainWindow mainWindow;

    FileControls(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        JButton newFileButton = new JButton("New");
        newFileButton.addActionListener((e) -> this.mainWindow.initForCircuit(new Circuit()));
        this.add(newFileButton);

        JButton openFileButton = new JButton("Open");
        openFileButton.addActionListener((e) -> openFile());
        this.add(openFileButton);

        JButton saveFileButton = new JButton("Save");
        saveFileButton.addActionListener((e) -> saveFile());
        this.add(saveFileButton);

        this.setBackground(Parameters.menuColor);
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("BlazejSim circuit", "bsim"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                Circuit circuit = Circuit.loadFromFile(chooser.getSelectedFile());
                this.mainWindow.initForCircuit(circuit);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getLocalizedMessage());
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Cannot find one of elements: " + e.getLocalizedMessage());
            }
        }
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("BlazejSim circuit", "bsim"));
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                this.mainWindow.circuit.saveToFile(chooser.getSelectedFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
