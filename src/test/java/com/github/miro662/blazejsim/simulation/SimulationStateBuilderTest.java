package com.github.miro662.blazejsim.simulation;

import com.github.miro662.blazejsim.circuits.Connection;
import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationStateBuilderTest {
    Connection connection;
    Input connectedPin;
    Output unconnectedPin;
    private SimulationStateBuilder ssb;

    @BeforeEach
    void preparePins() {
        connection = mock(Connection.class);
        connectedPin = mock(Input.class);
        when(connectedPin.getConnection()).thenReturn(connection);
        unconnectedPin = mock(Output.class);
        when(unconnectedPin.getConnection()).thenReturn(null);

        ssb = new SimulationStateBuilder();
    }

    @Test
    void addForConnection() {
        ssb.addFor(connection, LogicState.HIGH);
        SimulationState ss = ssb.build();
        assertEquals(LogicState.HIGH, ss.getFor(connection));
    }

    @Test
    void addForInput() {
        ssb.addFor(connectedPin, LogicState.LOW);
        SimulationState ss = ssb.build();
        assertEquals(LogicState.LOW, ss.getFor(connectedPin));
        assertEquals(LogicState.LOW, ss.getFor(connection));
    }

    @Test
    void addForUnconnectedPinDoesNothing() {
        ssb.addFor(unconnectedPin, LogicState.HIGH);
        SimulationState ss = ssb.build();
        assertEquals(LogicState.UNDEFINED, ss.getFor(unconnectedPin));
    }

    @Test
    void addForOverwritesOldValue() {
        ssb.addFor(connection, LogicState.HIGH);
        ssb.addFor(connectedPin, LogicState.LOW);
        SimulationState ss = ssb.build();
        assertEquals(LogicState.LOW, ss.getFor(connectedPin));
        assertEquals(LogicState.LOW, ss.getFor(connection));
    }

    @Test
    void join() {
        Connection otherConnection = mock(Connection.class);
        Connection builderConnection = mock(Connection.class);

        SimulationStateBuilder other = new SimulationStateBuilder();
        ssb.addFor(connection, LogicState.LOW);
        ssb.addFor(otherConnection, LogicState.LOW);

        ssb.addFor(connection, LogicState.HIGH);
        ssb.addFor(builderConnection, LogicState.HIGH);

        ssb.join(other.build());
        SimulationState ss = ssb.build();

        assertEquals(LogicState.HIGH, ss.getFor(connection));
        assertEquals(LogicState.LOW, ss.getFor(otherConnection));
        assertEquals(LogicState.HIGH, ss.getFor(builderConnection));
    }
}
