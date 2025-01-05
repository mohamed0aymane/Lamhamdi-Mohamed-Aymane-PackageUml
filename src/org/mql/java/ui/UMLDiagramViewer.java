package org.mql.java.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.mql.java.controller.ClassParserAndRepresentation;
import org.mql.java.controller.FileExplorer;

public class UMLDiagramViewer extends JFrame {

    public UMLDiagramViewer(List<ClassParserAndRepresentation> classRepresentations) {
        setTitle("UML Diagram Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        UMLPanel panel = new UMLPanel(classRepresentations);
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);
    }

   
}
