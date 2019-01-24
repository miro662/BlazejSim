package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.custom.CustomEntity;

import javax.swing.*;
import java.awt.*;

public class ExpressionWindow extends JFrame {
    JTextField expressionField;

    ExpressionWindow(CustomEntity entity) {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
        setSize(200, 100);
        setTitle("Expression editor");

        expressionField = new JTextField();
        this.add(expressionField, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        this.add(saveButton, BorderLayout.PAGE_END);
    }
}
