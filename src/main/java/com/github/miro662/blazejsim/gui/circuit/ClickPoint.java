package com.github.miro662.blazejsim.gui.circuit;

public class ClickPoint {
    private Point gridPoint;
    private Point offset;

    public ClickPoint(Point gridPoint) {
        this.gridPoint = gridPoint;
        this.offset = new Point(0, 0);
    }

    public ClickPoint(Point gridPoint, Point offset) {
        this.gridPoint = gridPoint;
        this.offset = offset;
    }

    public Point getGridPoint() {
        return gridPoint;
    }

    public void setGridPoint(Point gridPoint) {
        this.gridPoint = gridPoint;
    }

    public Point getOffset() {
        return offset;
    }

    public void setOffset(Point offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "position: " + gridPoint.toString() + ", offset: " + offset.toString();
    }
}
