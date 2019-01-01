package com.github.miro662.blazejsim.circuits.entities.constants;

import com.github.miro662.blazejsim.circuits.entities.Tester;
import com.github.miro662.blazejsim.simulation.LogicState;
import org.junit.jupiter.api.Test;

public class ZeroTest {
    @Test
    void alwaysZero() {
        Tester.forEntity(new Zero()).simulate().assertOutput("y", LogicState.LOW);
    }
}
