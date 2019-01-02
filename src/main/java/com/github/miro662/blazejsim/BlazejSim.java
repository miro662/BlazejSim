package com.github.miro662.blazejsim;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.entities.base.AnnotatedEntitiesBase;
import com.github.miro662.blazejsim.circuits.entities.base.EntityBase;
import com.github.miro662.blazejsim.gui.MainWindow;

import java.awt.*;

public class BlazejSim {

    public static void main(String[] args)  {
        Circuit circuit = new Circuit();
        EntityBase entityBase = new AnnotatedEntitiesBase();
        EventQueue.invokeLater(() -> new MainWindow(circuit, entityBase));
    }
}
