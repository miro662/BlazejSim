package com.github.miro662.blazejsim.gui.circuit.entity_views;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.gui.Parameters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public final class TemporaryEntityView extends EntityView {
    Image img;

    @Override
    public void draw(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(Color.BLACK);
        // draw entity itself
        g2d.drawImage(img, centerX - 16, centerY - 16, null);

        drawPins(g2d, centerX, centerY);
    }

    public TemporaryEntityView(Entity entity) {
        super(entity);
        try {
            img = ImageIO.read(getClass().getResource(entity.getEntityPath()));
        } catch (IOException e) {
            try {
                img = ImageIO.read(getClass().getResource("/entity/unknown.png"));
            } catch (IOException e1) {
                System.err.println("Cannot load fallback image");
            }
        }
    }
}
