package com.github.miro662.blazejsim;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.entities.base.AnnotatedEntitiesBase;
import com.github.miro662.blazejsim.circuits.entities.base.EntityBase;
import com.github.miro662.blazejsim.circuits.entities.constants.One;
import com.github.miro662.blazejsim.circuits.entities.constants.Zero;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.AndGate;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.OrGate;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.XorGate;
import com.github.miro662.blazejsim.circuits.entities.visible.Probe;
import com.github.miro662.blazejsim.circuits.entities.visible.Switch;
import com.github.miro662.blazejsim.gui.MainWindow;
import com.github.miro662.blazejsim.gui.circuit.Point;

import java.awt.*;

public class BlazejSim {

    public static void main(String[] args) throws Circuit.NotFromCircuitException, Circuit.AlreadyConnectedInputException {
        Circuit circuit = new Circuit();

        Switch one = new Switch();
        one.setPosition(new Point(0, 0));
        circuit.addEntity(one);
        Switch zero = new Switch();
        one.setPosition(new Point(0, 2));
        circuit.addEntity(zero);

        AndGate and = new AndGate();
        and.setPosition(new Point(2, 0));
        circuit.addEntity(and);
        OrGate or = new OrGate();
        or.setPosition(new Point(2, 2));
        circuit.addEntity(or);
        XorGate xor = new XorGate();
        xor.setPosition(new Point(4, 1));
        circuit.addEntity(xor);

        Probe probe = new Probe();
        probe.setPosition(new Point(6, 1));
        circuit.addEntity(probe);

        circuit.connect(one.output, and.a);
        circuit.connect(one.output, or.a);

        circuit.connect(zero.output, and.b);
        circuit.connect(zero.output, or.b);

        circuit.connect(and.y, xor.a);
        circuit.connect(or.y, xor.b);

        circuit.connect(xor.y, probe.in);

        EntityBase entityBase = new AnnotatedEntitiesBase();
        EventQueue.invokeLater(() -> new MainWindow(circuit, entityBase));
    }
}
