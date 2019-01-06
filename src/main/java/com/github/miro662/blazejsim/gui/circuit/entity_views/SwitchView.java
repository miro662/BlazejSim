package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.visible.Switch;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.gui.circuit.Point;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SwitchView extends EntityView {
    private Switch aSwitch;

    @Override
    public void draw(Graphics2D g2d, int centerX, int centerY, SimulationState state) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(centerX - 16, centerY - 16, 32, 32);

        Color color = aSwitch.getState() ? Color.GREEN : Color.RED;

        g2d.setColor(color);
        g2d.fillRect(centerX - 12, centerY - 8, 24, 16);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(centerX - 12, centerY - 8, 24, 16);

        drawPins(g2d, centerX, centerY);
    }

    public SwitchView(@NotNull Switch aSwitch) {
        super(aSwitch);
        this.aSwitch = aSwitch;
    }

    @Override
    public void onClick(Point offset) {
        aSwitch.toggleState();
    }
}
