package com.github.miro662.blazejsim.circuits;

import com.github.miro662.blazejsim.gui.circuit.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    @Test
    void equals() {
        Point point = new Point(2, 1);
        Point equalPoint = new Point(2, 1);
        Point notEqualPointX = new Point(3, 1);
        Point notEqualPointY = new Point(2, 7);
        Point notEqualPointXY = new Point(3, 7);

        assertTrue(point.equals(equalPoint));
        assertFalse(point.equals(notEqualPointX));
        assertFalse(point.equals(notEqualPointY));
        assertFalse(point.equals(notEqualPointXY));
    }
}
