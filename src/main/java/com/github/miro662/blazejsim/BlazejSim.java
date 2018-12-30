package com.github.miro662.blazejsim;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.entities.constants.One;
import com.github.miro662.blazejsim.circuits.entities.constants.Zero;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.AndGate;
import com.github.miro662.blazejsim.simulation.Simulation;
import com.github.miro662.blazejsim.simulation.SimulationState;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

public class BlazejSim {
    public void aaa() {

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Circuit circuit = new Circuit();

        One one = new One();
        circuit.addEntity(one);

        Zero zero = new Zero();
        circuit.addEntity(zero);

        Connection zeroConnection = new Connection();
        zeroConnection.connectOutput(zero.y);

        Connection oneConnection = new Connection();
        oneConnection.connectOutput(one.y);

        AndGate gate = new AndGate();
        zeroConnection.connectInput(gate.a);
        zeroConnection.connectInput(gate.b);
        circuit.addEntity(gate);

        Connection andConnection = new Connection();
        andConnection.connectOutput(gate.y);

        Simulation simulation = new Simulation(circuit);
        SimulationState sim = simulation.next();
        System.out.println(sim.getFor(gate.y));
        sim = simulation.next();
        System.out.println(sim.getFor(gate.y));
    }
}
