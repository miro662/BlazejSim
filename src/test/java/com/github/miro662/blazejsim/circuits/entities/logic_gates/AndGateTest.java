package com.github.miro662.blazejsim.circuits.entities.logic_gates;

import com.github.miro662.blazejsim.circuits.entities.Tester;
import com.github.miro662.blazejsim.simulation.LogicState;
import org.junit.jupiter.api.Test;

public class AndGateTest {
    @Test
    void testGate() {
        Tester.forEntity(new AndGate())
                .set("a", LogicState.LOW).set("b", LogicState.LOW).simulate().assertOutput("y", LogicState.LOW)
                .set("a", LogicState.HIGH).set("b", LogicState.LOW).simulate().assertOutput("y", LogicState.LOW)
                .set("a", LogicState.LOW).set("b", LogicState.HIGH).simulate().assertOutput("y", LogicState.LOW)
                .set("a", LogicState.HIGH).set("b", LogicState.HIGH).simulate().assertOutput("y", LogicState.HIGH);
    }
}
