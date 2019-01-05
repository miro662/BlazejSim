package com.github.miro662.blazejsim.gui.circuit;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.base.RegisteredEntity;
import com.github.miro662.blazejsim.gui.EntityChooser;
import com.github.miro662.blazejsim.gui.Parameters;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityViewFactory;

import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CircuitView extends JPanel implements MouseListener, EntityChooser.EntityChooseListener {
    private Circuit circuit;

    public CircuitView(Circuit circuit) {
        super();
        this.circuit = circuit;
        addMouseListener(this);
        initializeEntityViews();
        toCreate = null;
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        background(g2d);
        grid(g2d);
        drawEntityViews(g2d);
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

    private Point toPosition(Point gridPosition) {
        return new Point(
                gridPosition.getX() * Parameters.cellSize + Parameters.getHalfCellSize(),
                gridPosition.getY() * Parameters.cellSize + Parameters.getHalfCellSize()
        );
    }

    private List<EntityView> entityViews;
    private void initializeEntityViews() {
        entityViews = new LinkedList<>();

        circuit.getEntities().forEach((entity -> entityViews.add(EntityViewFactory.forEntity(entity))));
    }

    private void drawEntityViews(Graphics2D g2d) {
        for (EntityView ev : entityViews) {
            Point pos = toPosition(ev.getEntity().getPosition());
            ev.draw(g2d, pos.getX(), pos.getY());
        }
    }

    public void reset(Circuit circuit) {
        this.circuit = circuit;
        initializeEntityViews();
        repaint();
    }



    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (toCreate != null) {
            if (e.getButton() != 1) {
                toCreate = null;
            } else {
                Entity newEntity = toCreate.create();
                newEntity.setPosition(fromPosition(e.getX(), e.getY()).getGridPoint());
                circuit.addEntity(newEntity);

                EntityView ev = EntityViewFactory.forEntity(newEntity);
                entityViews.add(ev);

                toCreate = null;
                repaint();
            }
        }
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

    private RegisteredEntity toCreate;
    @Override
    public void choose(RegisteredEntity entity) {
        toCreate = entity;
    }
}
