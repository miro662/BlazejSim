package com.github.miro662.blazejsim.gui;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.base.EntityBase;
import com.github.miro662.blazejsim.circuits.entities.base.RegisteredEntity;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class EntityChooser extends JPanel {
    private EntityBase entityBase;
    private List<EntityChooseListener> entityChooseListeners;

    public EntityChooser(EntityBase entityBase) {
        this.entityBase = entityBase;
        this.entityChooseListeners = new LinkedList<>();

        int height = getHeight();
        setPreferredSize(new Dimension(200, height));

        ButtonGroup group = new ButtonGroup();

        entityBase.getAll().forEach((registeredEntity -> {
            setBackground(Parameters.chooserColor);
            JButton button = new JButton(registeredEntity.getName());
            button.setPreferredSize(new Dimension(180, 30));
            button.addActionListener((action) ->
                entityChooseListeners.forEach((ecl) -> ecl.choose(registeredEntity))
            );
            add(button);
        }));
    }

    public void addEntityChooseListener(EntityChooseListener listener) {
        entityChooseListeners.add(listener);
    }

    public void deleteEntityChooseListener(EntityChooseListener listener) {
        entityChooseListeners.remove(listener);
    }

    @FunctionalInterface
    public interface EntityChooseListener {
        void choose(RegisteredEntity entity);
    }
}
