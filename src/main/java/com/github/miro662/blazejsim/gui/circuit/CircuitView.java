package com.github.miro662.blazejsim.gui.circuit;

import com.github.miro662.blazejsim.circuits.*;
import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.base.RegisteredEntity;
import com.github.miro662.blazejsim.gui.EntityChooser;
import com.github.miro662.blazejsim.gui.Parameters;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityViewFactory;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.Simulation;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.StepNotifiable;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;

public class CircuitView extends JPanel implements MouseListener, MouseMotionListener, EntityChooser.EntityChooseListener, StepNotifiable {
    private Circuit circuit;

    public CircuitView(Circuit circuit) {
        super();
        this.circuit = circuit;
        addMouseListener(this);
        addMouseMotionListener(this);
        initializeEntityViews();
        toCreate = null;
        state = SimulationState.empty();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        background(g2d);
        grid(g2d);
        drawEntityViews(g2d);
        drawConnections(g2d);

        drawConnectonHint(g2d);
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
            ev.draw(g2d, pos.getX(), pos.getY(), state);
        }
    }

    public void reset(Circuit circuit) {
        this.circuit = circuit;
        initializeEntityViews();
        repaint();
    }

    private void drawConnections(Graphics2D g2d) {
        circuit.getConnections().forEach(connection -> {
            g2d.setColor(getColorForConnection(connection));
            Point outputPosition = toPosition(connection.getOutput().getEntity().getPosition());
            outputPosition.setY(outputPosition.getY() + connection.getOutput().getOffset());
            outputPosition.setX(outputPosition.getX() + Parameters.getGateSize() / 2 + Parameters.pinSize);
            connection.getInputs().forEach((input -> {
                Point inputPosition = toPosition(input.getEntity().getPosition());
                inputPosition.setY(inputPosition.getY() + input.getOffset());
                inputPosition.setX(inputPosition.getX() - Parameters.getGateSize() / 2 - Parameters.pinSize);
                g2d.drawLine(outputPosition.getX(), outputPosition.getY(), inputPosition.getX(), inputPosition.getY());
            }));
        });
    }

    SimulationState state;
    @Override
    public synchronized void notifyStep(SimulationState state) {
        this.state = state;
        EventQueue.invokeLater(() -> repaint());
    }

    public void addSimulation(Simulation simulation) {
        simulation.addStepNotifyable(this);
    }

    public void removeSimuation(Simulation simulation) {
        simulation.deleteStepNotifyable(this);
        state = SimulationState.empty();
    }

    private Color getColorForConnection(Connection connection) {
        LogicState state = this.state.getFor(connection);
        if (state == LogicState.HIGH) return Parameters.highColor;
        else if (state == LogicState.LOW) return Parameters.lowColor;
        else return Parameters.undefinedColor;
    }

    @NotNull
    private EntityView getViewForEntity(@NotNull Entity entity) {
        return entityViews.stream().filter(ev -> ev.getEntity() == entity).findFirst().get();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    private Output toConnectOutput;
    private Input toConnectInput;
    private Point lmp;

    private ClickPoint pressedCell;

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
        } else {
            pressedCell = fromPosition(e.getX(), e.getY());
            circuit.getEntityAt(pressedCell.getGridPoint()).ifPresent(entity -> {
                EntityView ev = getViewForEntity(entity);

                if (e.getButton() == 1) {
                    Optional<Output> outputOptional = ev.getOutputPinAt(pressedCell.getOffset());
                    outputOptional.ifPresent(output -> toConnectOutput = output);

                    Optional<Input> inputOptional = ev.getInputPinAt(pressedCell.getOffset());
                    inputOptional.ifPresent(input -> {
                        toConnectInput = input;
                    });

                    lmp = new Point(e.getX(), e.getY());
                } else if (e.getButton() == 3) {
                    Optional<Output> outputOptional = ev.getOutputPinAt(pressedCell.getOffset());
                    outputOptional.ifPresent(output -> {
                        try {
                            circuit.disconnect(output);
                        } catch (Circuit.NotFromCircuitException ex) {
                            JOptionPane.showMessageDialog(this, "Trying to disconnect pin which is not in this circuit", "BlazejSim", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    Optional<Input> inputOptional = ev.getInputPinAt(pressedCell.getOffset());
                    inputOptional.ifPresent(input -> {
                        try {
                            circuit.disconnect(input);
                        } catch (Circuit.NotFromCircuitException ex) {
                            JOptionPane.showMessageDialog(this, "Trying to disconnect pin which is not in this circuit", "BlazejSim", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    if (ev.inEntity(pressedCell.getOffset())) {
                        try {
                            circuit.deleteEntity(entity);
                            entityViews.remove(ev);
                        } catch (Circuit.TryingToDeleteConnectedEntity tryingToDeleteConnectedEntity) {
                            JOptionPane.showMessageDialog(this, "Cannot delete entity that is connected to something");
                        }
                        EventQueue.invokeLater(() -> repaint());
                    }
                }
            });

            repaint();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ClickPoint cp = fromPosition(e.getX(), e.getY());

        if (pressedCell != null) {
            circuit.getEntityAt(pressedCell.getGridPoint()).ifPresent(entity -> {
                EntityView ev = getViewForEntity(entity);
                if (cp.getGridPoint().equals(pressedCell.getGridPoint())) {
                    ev.clicked(cp.getOffset());
                } else {
                    if (ev.inEntity(pressedCell.getOffset())) {
                        entity.setPosition(cp.getGridPoint());
                    }
                }
            });
        }
        pressedCell = null;

        circuit.getEntityAt(cp.getGridPoint()).ifPresent(entity -> {
            EntityView ev = getViewForEntity(entity);
            if (toConnectOutput != null) {
                Optional<Input> inputOptional = ev.getInputPinAt(cp.getOffset());
                inputOptional.ifPresent(input -> {
                    try {
                        circuit.connect(toConnectOutput, input);
                    } catch (Circuit.NotFromCircuitException ex) {
                        JOptionPane.showMessageDialog(this, "Trying to connect object which is not in this circuit", "BlazejSim", JOptionPane.ERROR_MESSAGE);
                    } catch (Circuit.AlreadyConnectedInputException ignored) {

                    }
                });
            }
            if (toConnectInput != null) {
                Optional<Output> outputOptional = ev.getOutputPinAt(cp.getOffset());
                outputOptional.ifPresent(output -> {
                    try {
                        circuit.connect(output, toConnectInput);
                    } catch (Circuit.NotFromCircuitException ex) {
                        JOptionPane.showMessageDialog(this, "Trying to connect object which is not in this circuit", "BlazejSim", JOptionPane.ERROR_MESSAGE);
                    } catch (Circuit.AlreadyConnectedInputException ignored) {

                    }
                });
            }
        });
        EventQueue.invokeLater(() -> repaint());
        toConnectOutput = null;
        toConnectInput = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        lmp = new Point(e.getX(), e.getY());
        EventQueue.invokeLater(() -> repaint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lmp = new Point(e.getX(), e.getY());
        EventQueue.invokeLater(() -> repaint());
    }

    private void drawConnectonHint(Graphics2D g2d) {
        g2d.setColor(Parameters.undefinedColor);

        if (toConnectOutput != null || toConnectInput != null) {
            Pin toConnectPin = toConnectOutput != null ? toConnectOutput : toConnectInput;
            int direction = toConnectOutput != null ? 1 : -1;
            Point outputPos = toPosition(toConnectPin.getEntity().getPosition());
            outputPos.setY(outputPos.getY() + toConnectPin.getOffset());
            outputPos.setX(outputPos.getX() + Parameters.getHalfCellSize() * direction);
            g2d.drawLine(outputPos.getX(), outputPos.getY(), lmp.getX(), lmp.getY());
        }
    }

    private RegisteredEntity toCreate;
    @Override
    public void choose(RegisteredEntity entity) {
        toCreate = entity;
    }
}
