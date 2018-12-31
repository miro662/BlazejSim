package com.github.miro662.blazejsim;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.entities.SimpleOutput;
import com.github.miro662.blazejsim.circuits.entities.constants.One;
import com.github.miro662.blazejsim.circuits.entities.constants.Zero;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.OrGate;
import com.github.miro662.blazejsim.simulation.Simulation;

import java.util.concurrent.ExecutionException;

public class BlazejSim {

    public static void main(String[] args) throws InterruptedException {
        Circuit circuit = new Circuit();

        One one = new One();
        circuit.addEntity(one);

        Zero zero = new Zero();
        circuit.addEntity(zero);

        OrGate gate = new OrGate();
        circuit.addEntity(gate);

        SimpleOutput output = new SimpleOutput();
        circuit.addEntity(output);

        Connection zeroConnection = Connection.between(one.y, new Input[] {gate.a});
        Connection oneConnection = Connection.between(zero.y, new Input[] {gate.b});
        Connection outputConnection = Connection.between(gate.y, new Input[] {output.in});

        Simulation simulation = new Simulation(circuit);
        simulation.start();

        Thread.sleep(100);
        System.out.println(output.getOutputState());
    }
}
