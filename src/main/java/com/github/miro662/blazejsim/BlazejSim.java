package com.github.miro662.blazejsim;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.entities.base.AnnotatedEntitiesBase;
import com.github.miro662.blazejsim.circuits.entities.base.EntityBase;
import com.github.miro662.blazejsim.circuits.entities.constants.One;
import com.github.miro662.blazejsim.circuits.entities.constants.Zero;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.AndGate;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.OrGate;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.XorGate;
import com.github.miro662.blazejsim.gui.MainWindow;
import com.github.miro662.blazejsim.gui.circuit.Point;

import java.awt.*;

public class BlazejSim {

    public static void main(String[] args) throws Circuit.NotFromCircuitException, Circuit.AlreadyConnectedInputException {
        Circuit circuit = new Circuit();

        EntityBase entityBase = new AnnotatedEntitiesBase();
        EventQueue.invokeLater(() -> new MainWindow(circuit, entityBase));
    }
}
