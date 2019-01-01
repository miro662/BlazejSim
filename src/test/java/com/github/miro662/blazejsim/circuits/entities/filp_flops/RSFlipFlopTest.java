package com.github.miro662.blazejsim.circuits.entities.filp_flops;

import com.github.miro662.blazejsim.circuits.entities.Tester;
import com.github.miro662.blazejsim.circuits.entities.flip_flops.RSFlipFlop;
import com.github.miro662.blazejsim.simulation.LogicState;
import org.junit.jupiter.api.Test;

public class RSFlipFlopTest {
    @Test
    void testFlipFlop() {
        Tester.forEntity(new RSFlipFlop())
            .set("r", LogicState.LOW).set("s", LogicState.LOW).simulate()
                .assertOutput("q", LogicState.LOW)
                .assertOutput("notQ", LogicState.HIGH)
            .set("r", LogicState.LOW).set("s", LogicState.HIGH).simulate()
                .assertOutput("q", LogicState.HIGH)
                .assertOutput("notQ", LogicState.LOW)
            .set("r", LogicState.LOW).set("s", LogicState.LOW).simulate()
                .assertOutput("q", LogicState.HIGH)
                .assertOutput("notQ", LogicState.LOW)
            .set("r", LogicState.HIGH).set("s", LogicState.LOW).simulate()
                .assertOutput("q", LogicState.LOW)
                .assertOutput("notQ", LogicState.HIGH)
            .set("r", LogicState.LOW).set("s", LogicState.LOW).simulate()
                .assertOutput("q", LogicState.LOW)
                .assertOutput("notQ", LogicState.HIGH);
    }
}
