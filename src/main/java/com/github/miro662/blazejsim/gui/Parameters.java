package com.github.miro662.blazejsim.gui;

import java.awt.*;

public class Parameters {
    public static final byte cellSize = 48;
    public static final byte pinSize = 8;
    public static final byte pinWidth = 5;

    public static Color menuColor = new Color(192, 192, 192);
    public static Color chooserColor = new Color(224, 224, 224);

    public static Color gridColor = new Color(240, 240, 240);
    public static Color circuitBackgroundColor = Color.WHITE;

    public static byte getHalfCellSize() {
        return cellSize / 2;
    }
}
