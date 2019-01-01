package com.github.miro662.blazejsim.circuits.entities.constants;

import com.github.miro662.blazejsim.circuits.entities.Tester;
import com.github.miro662.blazejsim.simulation.LogicState;
import org.junit.jupiter.api.Test;

public class OneTest {
    @Test
    void alwaysOne() {
        Tester.forEntity(new One()).simulate().assertOutput("y", LogicState.HIGH);
    }
}
