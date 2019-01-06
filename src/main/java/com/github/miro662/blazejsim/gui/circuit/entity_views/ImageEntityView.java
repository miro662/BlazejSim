package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.gui.Parameters;
import com.github.miro662.blazejsim.simulation.SimulationState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public final class ImageEntityView extends EntityView {
    Image img;

    @Override
    public void draw(Graphics2D g2d, int centerX, int centerY, SimulationState state) {
        g2d.setColor(Color.BLACK);
        // draw entity itself
        g2d.drawImage(img, centerX - 16, centerY - 16, null);

        drawPins(g2d, centerX, centerY);
    }

    public ImageEntityView(Entity entity, String resourceLocation) {
        super(entity);
        try {
            img = ImageIO.read(getClass().getResource(resourceLocation));
        } catch (IOException e) {
            try {
                img = ImageIO.read(getClass().getResource("/entity/unknown.png"));
            } catch (IOException e1) {
                System.err.println("Cannot load fallback image");
            }
        }
    }
}
