package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.custom.CustomEntity;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.parser.ParseException;
import com.github.miro662.blazejsim.gui.circuit.CircuitView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class ExpressionWindow extends JDialog {
    CustomEntity entity;
    JTextField expressionField;

    ExpressionWindow(CircuitView view, CustomEntity entity) {
        this.entity = entity;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
        setSize(200, 100);
        setTitle("Expression editor");

        expressionField = new JTextField();
        expressionField.setText(entity.getExpressionString());
        this.add(expressionField, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((action) -> {
            try {
                entity.setExpressionString(expressionField.getText());
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                EventQueue.invokeLater(view::repaint);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Parse error");
            }
        });
        this.add(saveButton, BorderLayout.PAGE_END);
    }
}
