package com.github.miro662.blazejsim;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.entities.SimpleOutput;
import com.github.miro662.blazejsim.circuits.entities.constants.One;
import com.github.miro662.blazejsim.circuits.entities.constants.Zero;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.OrGate;
import com.github.miro662.blazejsim.simulation.Simulation;

public class BlazejSim {

    public static void main(String[] args) throws InterruptedException, Circuit.NotFromCircuitException, Circuit.AlreadyConnectedInputException {
        Circuit circuit = new Circuit();

        One one = new One();
        circuit.addEntity(one);

        Zero zero = new Zero();
        circuit.addEntity(zero);

        OrGate gate = new OrGate();
        circuit.addEntity(gate);

        SimpleOutput output = new SimpleOutput();
        circuit.addEntity(output);

        circuit.connect(one.y, gate.a);
        circuit.connect(zero.y, gate.b);
        circuit.connect(gate.y, output.in);

        Simulation simulation = new Simulation(circuit);
        simulation.start();

        Thread.sleep(100);
        System.out.println(output.getOutputState());
    }
}
