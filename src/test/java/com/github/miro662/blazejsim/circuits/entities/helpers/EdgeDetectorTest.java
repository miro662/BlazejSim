package com.github.miro662.blazejsim.circuits.entities.helpers;

import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeDetectorTest {
    Connection connection;
    Input input;
    EdgeDetector detector;

    @BeforeEach
    void mockInput() {
        connection = mock(Connection.class);
        input = mock(Input.class);
        when(input.getConnection()).thenReturn(connection);
        detector = EdgeDetector.forInput(input);
    }

    @Test
    void detectsRisingEdges() {
        SimulationStateBuilder zeroBuilder = new SimulationStateBuilder();
        zeroBuilder.addFor(connection, LogicState.LOW);
        detector.step(zeroBuilder.build());

        SimulationStateBuilder oneBuilder = new SimulationStateBuilder();
        oneBuilder.addFor(connection, LogicState.HIGH);
        detector.step(oneBuilder.build());

        assertTrue(detector.isRisingEdge());
        assertFalse(detector.isFallingEdge());
    }

    @Test
    void detectsFallingEdges() {
        SimulationStateBuilder oneBuilder = new SimulationStateBuilder();
        oneBuilder.addFor(connection, LogicState.HIGH);
        detector.step(oneBuilder.build());

        SimulationStateBuilder zeroBuilder = new SimulationStateBuilder();
        zeroBuilder.addFor(connection, LogicState.LOW);
        detector.step(zeroBuilder.build());

        assertFalse(detector.isRisingEdge());
        assertTrue(detector.isFallingEdge());
    }

    @Test
    void noFalsePositives() {
        SimulationStateBuilder oneBuilder = new SimulationStateBuilder();
        oneBuilder.addFor(connection, LogicState.HIGH);
        detector.step(oneBuilder.build());
        detector.step(oneBuilder.build());

        assertFalse(detector.isRisingEdge());
        assertFalse(detector.isFallingEdge());

        SimulationStateBuilder zeroBuilder = new SimulationStateBuilder();
        zeroBuilder.addFor(connection, LogicState.LOW);
        detector.step(zeroBuilder.build());
        detector.step(zeroBuilder.build());

        assertFalse(detector.isRisingEdge());
        assertFalse(detector.isFallingEdge());
    }
}
