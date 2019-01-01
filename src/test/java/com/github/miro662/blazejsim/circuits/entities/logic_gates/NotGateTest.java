package com.github.miro662.blazejsim.circuits.entities.logic_gates;

import com.github.miro662.blazejsim.circuits.entities.Tester;
import com.github.miro662.blazejsim.simulation.LogicState;
import org.junit.jupiter.api.Test;

public class NotGateTest {
    @Test
    void negation() {
        Tester.forEntity(new NotGate())
                .set("a", LogicState.LOW).simulate().assertOutput("y", LogicState.HIGH)
                .set("a", LogicState.HIGH).simulate().assertOutput("y", LogicState.LOW);
    }
}
