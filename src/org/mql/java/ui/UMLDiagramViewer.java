package org.mql.java.ui;

import javax.swing.*;
import java.awt.*;
import org.mql.java.controller.FileExplorer;
import org.mql.java.controller.ClassParserAndRepresentation;
import org.mql.java.model.Package;

public class UmlDiagramViewer extends JFrame {

    private static final long serialVersionUID = 1L;

    public UmlDiagramViewer() {
        setTitle("UML Diagram Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Charger les données du projet
        FileExplorer explorer = new FileExplorer("MonProjet", "src");
        explorer.loadProject();

        // Panneau principal pour afficher les packages
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Affichage en grille
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // Ajouter les packages comme rectangles
        for (Package pkg : explorer.getPackages()) {
            JPanel packagePanel = createPackagePanel(pkg);
            mainPanel.add(packagePanel);
        }

        // Ajouter le panneau principal à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);

        setVisible(true);
    }

    private JPanel createPackagePanel(Package pkg) {
        JPanel packagePanel = new JPanel();
        packagePanel.setLayout(new BorderLayout());
        packagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        packagePanel.setBackground(Color.WHITE);

        // Ajouter le nom du package
        String packageName = pkg.getName().endsWith(".")
                ? pkg.getName().substring(0, pkg.getName().length() - 1)
                : pkg.getName();
        JLabel packageLabel = new JLabel(packageName, JLabel.CENTER);
        packageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        packageLabel.setOpaque(true);
        packageLabel.setBackground(Color.GRAY);
        packageLabel.setForeground(Color.WHITE);
        packagePanel.add(packageLabel, BorderLayout.NORTH);

        // Ajouter les éléments du package
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Lister les classes, interfaces, énumérations et annotations
        pkg.getClasses().forEach(cls -> contentPanel.add(createClassDiagram(cls)));
        pkg.getInterfaces().forEach(cls -> contentPanel.add(createInterfaceDiagram(cls)));
        pkg.getEnumerations().forEach(cls -> contentPanel.add(createEnumerationDiagram(cls)));
        pkg.getAnnotations().forEach(cls -> contentPanel.add(createAnnotationDiagram(cls)));

        packagePanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        return packagePanel;
    }

    private JPanel createClassDiagram(Class<?> cls) {
        JPanel classPanel = new JPanel();
        classPanel.setLayout(new BorderLayout());
        classPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        JLabel classLabel = new JLabel("Class: " + cls.getSimpleName(), JLabel.CENTER);
        classLabel.setFont(new Font("Arial", Font.BOLD, 12));
        classLabel.setBackground(Color.CYAN);
        classLabel.setOpaque(true);
        classPanel.add(classLabel, BorderLayout.NORTH);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailsArea.setBackground(Color.WHITE);

        ClassParserAndRepresentation parser = new ClassParserAndRepresentation(cls.getName());
        detailsArea.append("\n");
       // Attributes:
        parser.getAttributes().forEach(attr -> detailsArea.append( attr + "\n"));
        
        // Ligne séparatrice entre les attributs et les méthodes
      //methods
    
       
        parser.getMethods().forEach(method -> detailsArea.append( "\n"+method + "\n"));

        classPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        return classPanel;
    }

    private JPanel createInterfaceDiagram(Class<?> cls) {
        JPanel interfacePanel = new JPanel();
        interfacePanel.setLayout(new BorderLayout());
        interfacePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        JLabel interfaceLabel = new JLabel("Interface: " + cls.getSimpleName(), JLabel.CENTER);
        interfaceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        interfaceLabel.setBackground(Color.LIGHT_GRAY);
        interfaceLabel.setOpaque(true);
        interfacePanel.add(interfaceLabel, BorderLayout.NORTH);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailsArea.setBackground(Color.WHITE);

        ClassParserAndRepresentation parser = new ClassParserAndRepresentation(cls.getName());
        
        parser.getMethods().forEach(method -> detailsArea.append( method + "\n"));

        interfacePanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        return interfacePanel;
    }

    private JPanel createEnumerationDiagram(Class<?> cls) {
        JPanel enumPanel = new JPanel();
        enumPanel.setLayout(new BorderLayout());
        enumPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        JLabel enumLabel = new JLabel("Enumeration: " + cls.getSimpleName(), JLabel.CENTER);
        enumLabel.setFont(new Font("Arial", Font.BOLD, 12));
        enumLabel.setBackground(Color.YELLOW);
        enumLabel.setOpaque(true);
        enumPanel.add(enumLabel, BorderLayout.NORTH);

        return enumPanel;
    }

    private JPanel createAnnotationDiagram(Class<?> cls) {
        JPanel annotationPanel = new JPanel();
        annotationPanel.setLayout(new BorderLayout());
        annotationPanel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

        JLabel annotationLabel = new JLabel("Annotation: " + cls.getSimpleName(), JLabel.CENTER);
        annotationLabel.setFont(new Font("Arial", Font.BOLD, 12));
        annotationLabel.setBackground(Color.PINK);
        annotationLabel.setOpaque(true);
        annotationPanel.add(annotationLabel, BorderLayout.NORTH);

        return annotationPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UmlDiagramViewer::new);
    }
}
