package com.github.miro662.blazejsim.circuits.entities.filp_flops;

import com.github.miro662.blazejsim.circuits.entities.Tester;
import com.github.miro662.blazejsim.circuits.entities.flip_flops.DFlipFlop;
import com.github.miro662.blazejsim.circuits.entities.flip_flops.RSFlipFlop;
import com.github.miro662.blazejsim.simulation.LogicState;
import org.junit.jupiter.api.Test;

public class DFlipFlopTest {
    @Test
    void testFlipFlop() {
        Tester.forEntity(new DFlipFlop())
            .set("d", LogicState.HIGH).set("clk", LogicState.LOW).simulate()
                .assertOutput("q", LogicState.LOW)
                .assertOutput("notQ", LogicState.HIGH)
            .set("d", LogicState.HIGH).set("clk", LogicState.HIGH).simulate()
                .assertOutput("q", LogicState.HIGH)
                .assertOutput("notQ", LogicState.LOW)
            .set("d", LogicState.LOW).set("clk", LogicState.HIGH).simulate()
                .assertOutput("q", LogicState.HIGH)
                .assertOutput("notQ", LogicState.LOW)
            .set("d", LogicState.LOW).set("clk", LogicState.LOW).simulate()
                .assertOutput("q", LogicState.HIGH)
                .assertOutput("notQ", LogicState.LOW)
            .set("d", LogicState.LOW).set("clk", LogicState.HIGH).simulate()
                .assertOutput("q", LogicState.LOW)
                .assertOutput("notQ", LogicState.HIGH)
            .set("d", LogicState.HIGH).set("clk", LogicState.HIGH).simulate()
                .assertOutput("q", LogicState.LOW)
                .assertOutput("notQ", LogicState.HIGH)
            .set("d", LogicState.HIGH).set("clk", LogicState.LOW).simulate()
                .assertOutput("q", LogicState.LOW)
                .assertOutput("notQ", LogicState.HIGH);
    }
}
