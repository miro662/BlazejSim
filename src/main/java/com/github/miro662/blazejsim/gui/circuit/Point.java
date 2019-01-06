package com.github.miro662.blazejsim.gui.circuit;

import java.io.Serializable;

public class Point implements Serializable {
    private int x;
    private int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    public boolean equals(Point point) {
        return this.x == point.x && this.y ==  point.y;
    }
}
