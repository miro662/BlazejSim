package com.github.miro662.blazejsim;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.SimpleOutput;
import com.github.miro662.blazejsim.circuits.entities.base.AnnotatedEntitiesBase;
import com.github.miro662.blazejsim.circuits.entities.base.EntityBase;
import com.github.miro662.blazejsim.circuits.entities.constants.Zero;
import com.github.miro662.blazejsim.circuits.entities.logic_gates.OrGate;
import com.github.miro662.blazejsim.simulation.Simulation;

public class BlazejSim {

    public static void main(String[] args) throws InterruptedException, Circuit.NotFromCircuitException, Circuit.AlreadyConnectedInputException {
        EntityBase base = new AnnotatedEntitiesBase();
        Circuit circuit = new Circuit();

        Entity one = base.get("Constants/One").create();
        circuit.addEntity(one);

        Entity zero = base.get("Constants/Zero").create();
        circuit.addEntity(zero);

        Entity or = base.get("Logic Gates/OR").create();
        circuit.addEntity(or);

        SimpleOutput orOutput = new SimpleOutput();
        circuit.addEntity(orOutput);

        SimpleOutput andOutput = new SimpleOutput();
        circuit.addEntity(andOutput);

        Entity and = base.get("Logic Gates/AND").create();
        circuit.addEntity(and);

        circuit.connect(one.getOutput("y"), or.getInput("a"));
        circuit.connect(zero.getOutput("y"), or.getInput("b"));
        circuit.connect(one.getOutput("y"), and.getInput("a"));
        circuit.connect(zero.getOutput("y"), and.getInput("b"));
        circuit.connect(or.getOutput("y"), orOutput.in);
        circuit.connect(and.getOutput("y"), andOutput.in);

        Simulation simulation = new Simulation(circuit);
        simulation.start();

        Thread.sleep(100);
        System.out.println("OR " + orOutput.getOutputState());
        System.out.println("AND " + andOutput.getOutputState());
    }
}
