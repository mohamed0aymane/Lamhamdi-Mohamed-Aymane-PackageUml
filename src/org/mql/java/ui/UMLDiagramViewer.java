package org.mql.java.ui;

import javax.swing.*;
import java.awt.*;
import org.mql.java.controller.FileExplorer;
import org.mql.java.controller.ClassParserAndRepresentation;
import org.mql.java.model.Package;
import org.mql.java.model.Relation;
import org.mql.java.model.RelationType;

public class UmlDiagramViewer extends JFrame {

    private static final long serialVersionUID = 1L;

    public UmlDiagramViewer() {
        setTitle("UML Diagram Viewer");
        setSize(1000, 800); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FileExplorer explorer = new FileExplorer("Lamhamdi Mohamed Aymane-tp04-packageUml", "src");
        explorer.loadProject();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout()); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        int col = 0;
        for (Package pkg : explorer.getPackages()) {
            JPanel packagePanel = createPackagePanel(pkg);
            gbc.gridx = col;
            gbc.gridy = row;

            mainPanel.add(packagePanel, gbc);

            col++;
            if (col == 2) { 
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

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout()); 
        contentPanel.setBackground(Color.WHITE); 
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        int columnCount = 2; 
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

        JLabel classLabel = new JLabel("<html><center>&lt;&lt;Class&gt;&gt;<br>" 
                + cls.getSimpleName() + "</center></html>", JLabel.CENTER);
        classLabel.setFont(new Font("Arial", Font.BOLD, 12));
        classLabel.setBackground(Color.BLUE);
        classLabel.setForeground(Color.WHITE);
        classLabel.setOpaque(true);
        classPanel.add(classLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 

        // Attributs
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        ClassParserAndRepresentation parser = new ClassParserAndRepresentation(cls.getName());
        parser.getAttributes().forEach(attr -> fieldsPanel.add(new JLabel(attr)));

        // Separateur entre les attributs et les methodes
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(0, 5)); 

        // Méthodes
        JPanel methodsPanel = new JPanel();
        methodsPanel.setLayout(new BoxLayout(methodsPanel, BoxLayout.Y_AXIS));
        parser.getMethods().forEach(method -> methodsPanel.add(new JLabel(method)));

        contentPanel.add(fieldsPanel);
        contentPanel.add(separator);
        contentPanel.add(methodsPanel);

        classPanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        return classPanel;
    }

    private JPanel createInterfaceDiagram(Class<?> cls) {
        JPanel interfacePanel = new JPanel();
        interfacePanel.setLayout(new BorderLayout());

        JLabel interfaceLabel = new JLabel("<html><center>&lt;&lt;Interface&gt;&gt;<br>" 
                + cls.getSimpleName() + "</center></html>", JLabel.CENTER);
        interfaceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        interfaceLabel.setBackground(Color.LIGHT_GRAY);
        interfaceLabel.setForeground(Color.WHITE);
        interfaceLabel.setOpaque(true);
        interfacePanel.add(interfaceLabel, BorderLayout.NORTH);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.BOLD, 12));
        detailsArea.setBackground(Color.WHITE);

        ClassParserAndRepresentation parser = new ClassParserAndRepresentation(cls.getName());
        parser.getMethods().forEach(method -> detailsArea.append(method + "\n"));

        interfacePanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        return interfacePanel;
    }

    private JPanel createEnumerationDiagram(Class<?> cls) {
        JPanel enumPanel = new JPanel();
        enumPanel.setLayout(new BorderLayout());
      
        JLabel enumLabel = new JLabel("<html><center>&lt;&lt;Enumeration&gt;&gt;<br>" 
                + cls.getSimpleName() + "</center></html>", JLabel.CENTER);
        enumLabel.setFont(new Font("Arial", Font.BOLD, 12));
        enumLabel.setBackground(Color.GREEN);
        enumLabel.setForeground(Color.WHITE);
        enumLabel.setOpaque(true);
        enumPanel.add(enumLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        if (cls.isEnum()) {
            Object[] enumConstants = cls.getEnumConstants();
            if (enumConstants != null) {
                for (Object constant : enumConstants) {
                    JLabel constantLabel = new JLabel(" "+constant.toString());
                    constantLabel.setFont(new Font("Arial", Font.BOLD, 12));
                    contentPanel.add(constantLabel);
                }
            }
        }

        enumPanel.add(contentPanel, BorderLayout.CENTER);

        return enumPanel;
    }

  

    private JPanel createAnnotationDiagram(Class<?> cls) {
        JPanel annotationPanel = new JPanel();
        annotationPanel.setLayout(new BorderLayout());

        JLabel annotationLabel = new JLabel("<html><center>&lt;&lt;Annotation&gt;&gt;<br>" 
                + cls.getSimpleName() + "</center></html>", JLabel.CENTER);
        annotationLabel.setFont(new Font("Arial", Font.BOLD, 12));
        annotationLabel.setBackground(Color.DARK_GRAY);
        annotationLabel.setForeground(Color.WHITE);
        annotationLabel.setOpaque(true);
        annotationPanel.add(annotationLabel, BorderLayout.NORTH);
        
        
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.BOLD, 12));
        detailsArea.setBackground(Color.WHITE);

        ClassParserAndRepresentation parser = new ClassParserAndRepresentation(cls.getName());
        parser.getMethods().forEach(method -> detailsArea.append(method + "\n"));

        annotationPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        return annotationPanel;
    }

    private JPanel createRelationsPanel(Package pkg) {
        JPanel relationsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRelations(g, pkg); 
            }
        };
        relationsPanel.setBackground(Color.WHITE);
        relationsPanel.setPreferredSize(new Dimension(300, 200)); 
        return relationsPanel;
    }

    private void drawRelations(Graphics g, Package pkg) {
        java.util.List<Relation> relations = pkg.getRelations();
        for (Relation relation : relations) {
            
            if (relation.getRelationType() == RelationType.COMPOSITION) {
                drawDiamond(g, relation.getClassSourceName(), relation.getClassTargetName(), true);
            } else if (relation.getRelationType() == RelationType.AGGREGATION) {
                drawDiamond(g, relation.getClassSourceName(), relation.getClassTargetName(), false);
            }
        }
    }

    private void drawDiamond(Graphics g, String sourceClass, String targetClass, boolean filled) {
        int x1 = 100; 
        int y1 = 100; 
        int x2 = 150;
        int y2 = 150;
        
        int[] xPoints = { x1, x1 - 10, x1, x1 + 10 }; 
        int[] yPoints = { y1, y1 + 10, y1 + 20, y1 + 10 };

        if (filled) {
            g.fillPolygon(xPoints, yPoints, 4);
        } else {
            g.drawPolygon(xPoints, yPoints, 4);
        }
    }
}
