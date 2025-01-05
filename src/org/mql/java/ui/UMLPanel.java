package org.mql.java.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.mql.java.controller.ClassParserAndRepresentation;

public class UMLPanel extends JPanel {
    private final List<ClassParserAndRepresentation> classRepresentations;

    public UMLPanel(List<ClassParserAndRepresentation> classRepresentations) {
        this.classRepresentations = classRepresentations;
        setPreferredSize(new Dimension(1000, 1000));
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 50;
        int y = 50;
        int gap = 50;

        for (ClassParserAndRepresentation representation : classRepresentations) {
            Dimension boxSize = calculateBoxSize(g2d, representation);
            drawClassBox(g2d, x, y, boxSize.width, boxSize.height, representation);
            y += boxSize.height + gap;

            if (y + boxSize.height > getHeight()) {
                y = 50;
                x += boxSize.width + gap;
            }
        }
    }

    private Dimension calculateBoxSize(Graphics2D g2d, ClassParserAndRepresentation representation) {
        int width = 200;
        int height = 30; // Height for the class name

        FontMetrics metrics = g2d.getFontMetrics();
        for (String attribute : representation.getAttributes()) {
            height += metrics.getHeight();
        }

        height += 10; // Separator line
        

        for (String method : representation.getMethodes()) {
            height += metrics.getHeight();
        }

        return new Dimension(width, height);
    }

    private void drawClassBox(Graphics2D g2d, int x, int y, int width, int height, ClassParserAndRepresentation representation) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(x, y, width, height);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);

        int textX = x + 10;
        int textY = y + 20;

        // Draw class name
        g2d.drawString(representation.getClassName(), textX, textY);
        textY += 10;

        g2d.drawLine(x, y + 30, x + width, y + 30);

        // Draw attributes
        for (String attribute : representation.getAttributes()) {
            textY += 15;
            g2d.drawString(attribute, textX, textY);
            
        }
        textY += 10;
        g2d.drawLine(x, y + 30 + (representation.getAttributes().size() * 15), x + width, y + 30 + (representation.getAttributes().size() * 15));

        // Draw methods
        for (String method : representation.getMethodes()) {
            textY += 15;
            g2d.drawString(method, textX, textY);
        }
    }
}
