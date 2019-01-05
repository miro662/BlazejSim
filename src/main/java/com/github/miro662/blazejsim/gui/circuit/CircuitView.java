package com.github.miro662.blazejsim.gui.circuit;

import com.github.miro662.blazejsim.gui.Parameters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CircuitView extends JPanel implements MouseListener {
    public CircuitView() {
        super();

        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        background(g2d);
        grid(g2d);
    }

    private void background(Graphics2D g2d) {
        g2d.setColor(Parameters.circuitBackgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private Point currentSize;

    private void grid(Graphics2D g2d) {
        g2d.setColor(Parameters.gridColor);

        Point currentSize = new Point(0, 0);

        // horizontal lines
        for (int x = Parameters.getHalfCellSize(); x < getWidth(); x += Parameters.cellSize) {
            g2d.drawLine(x, 0, x, getHeight());
            currentSize.setX(currentSize.getX() + 1);
        }

        // vertical lines
        for (int y = Parameters.getHalfCellSize(); y < getHeight(); y += Parameters.cellSize) {
            g2d.drawLine(0, y, getWidth(), y);
            currentSize.setY(currentSize.getY() + 1);
        }

        this.currentSize = currentSize;
    }

    private ClickPoint fromPosition(int x, int y) {
        return new ClickPoint(
                new Point(x / Parameters.cellSize, y / Parameters.cellSize),
                new Point(x % Parameters.cellSize - Parameters.getHalfCellSize(), y % Parameters.cellSize - Parameters.getHalfCellSize())
        );
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JOptionPane.showMessageDialog(this, fromPosition(e.getX(), e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
