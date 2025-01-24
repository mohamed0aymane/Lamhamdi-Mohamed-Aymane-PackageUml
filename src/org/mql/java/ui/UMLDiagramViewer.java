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
        setSize(1000, 800); // Augmenter la taille pour mieux gérer l'espace
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Charger les données du projet
        FileExplorer explorer = new FileExplorer("MonProjet", "src");
        explorer.loadProject();

        // Panneau principal pour afficher les packages
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout()); // Utilisation de GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les éléments
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ajouter les packages avec 3 par ligne minimum
        int row = 0;
        int col = 0;
        for (Package pkg : explorer.getPackages()) {
            JPanel packagePanel = createPackagePanel(pkg);
            gbc.gridx = col;
            gbc.gridy = row;

            mainPanel.add(packagePanel, gbc);

            col++;
            if (col == 2) { // Passer à la ligne suivante après 3 colonnes
                col = 0;
                row++;
            }
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

        // Ajouter le nom du package
        String packageName = pkg.getName().endsWith(".")
                ? pkg.getName().substring(0, pkg.getName().length() - 1)
                : pkg.getName();
        JLabel packageLabel = new JLabel(packageName, JLabel.CENTER);
        packageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        packageLabel.setOpaque(true);
        packageLabel.setForeground(Color.BLACK);

        packagePanel.add(packageLabel, BorderLayout.NORTH);

        // Ajouter les éléments du package (classes, interfaces, énumérations, annotations)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout()); // Utilisation de GridBagLayout
        contentPanel.setBackground(Color.WHITE); // Fond blanc pour le contraste
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marges internes

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les sous-panneaux
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Lister les classes, interfaces, énumérations et annotations
        int columnCount = 3; // Deux colonnes
        int col = 0;
        for (Class<?> cls : pkg.getClasses()) {
            contentPanel.add(createClassDiagram(cls), gbc);
            col++;
            if (col == columnCount) {
                col = 0;
                gbc.gridy++;
                gbc.gridx = 0;
            } else {
                gbc.gridx++;
            }
        }
        for (Class<?> cls : pkg.getInterfaces()) {
            contentPanel.add(createInterfaceDiagram(cls), gbc);
            col++;
            if (col == columnCount) {
                col = 0;
                gbc.gridy++;
                gbc.gridx = 0;
            } else {
                gbc.gridx++;
            }
        }
        for (Class<?> cls : pkg.getEnumerations()) {
            contentPanel.add(createEnumerationDiagram(cls), gbc);
            col++;
            if (col == columnCount) {
                col = 0;
                gbc.gridy++;
                gbc.gridx = 0;
            } else {
                gbc.gridx++;
            }
        }
        for (Class<?> cls : pkg.getAnnotations()) {
            contentPanel.add(createAnnotationDiagram(cls), gbc);
            col++;
            if (col == columnCount) {
                col = 0;
                gbc.gridy++;
                gbc.gridx = 0;
            } else {
                gbc.gridx++;
            }
        }

        packagePanel.add(contentPanel, BorderLayout.CENTER);

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

        // Création du panneau principal pour les attributs et méthodes
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Pour aligner verticalement

        // Attributs
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        ClassParserAndRepresentation parser = new ClassParserAndRepresentation(cls.getName());
        parser.getAttributes().forEach(attr -> fieldsPanel.add(new JLabel(attr)));

        // Séparateur entre les attributs et les méthodes
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(0, 5)); // Pour donner de l'espace visuel

        // Méthodes
        JPanel methodsPanel = new JPanel();
        methodsPanel.setLayout(new BoxLayout(methodsPanel, BoxLayout.Y_AXIS));
        parser.getMethods().forEach(method -> methodsPanel.add(new JLabel(method)));

        // Ajouter les panneaux d'attributs et de méthodes dans le panneau principal
        contentPanel.add(fieldsPanel);
        contentPanel.add(separator);
        contentPanel.add(methodsPanel);

        // Ajouter le contenu dans le panneau de la classe
        classPanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);

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
        parser.getMethods().forEach(method -> detailsArea.append(method + "\n"));

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
