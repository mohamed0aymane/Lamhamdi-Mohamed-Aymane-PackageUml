package org.mql.java.test;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.mql.java.controller.ClassParserAndRepresentation;
import org.mql.java.controller.FileExplorer;
import org.mql.java.ui.UMLDiagramViewer;


public class Examples extends JFrame {

	public Examples() {
		
		exp03();
	}
	//parser  les methodes et les attributs
	 void exp01() {
		 String path = "org.mql.java.ui.shapes.Rectangle";
		 ClassParserAndRepresentation representation = new ClassParserAndRepresentation(path);
		 System.out.println("Les attributs : "+representation.getAttributes());
	     System.out.println("Les methodes  : "+representation.getMethodes());
	}
	 
	 void exp02() {
 
		 FileExplorer explorer = new FileExplorer("src/org/mql/java");
	        explorer.loadPackages();
	        explorer.printResults();
	 }
	 void exp03(){
		// Exemple d'utilisation avec des classes fictives
	        FileExplorer explorer = new FileExplorer("src/org/mql/java/ui/shapes");
	        explorer.loadPackages();

	        List<Class<?>> classes = explorer.getClasses();
	        List<ClassParserAndRepresentation> representations = classes.stream()
	                .map(cls -> new ClassParserAndRepresentation(cls.getName()))
	                .toList();

	        SwingUtilities.invokeLater(() -> {
	            UMLDiagramViewer viewer = new UMLDiagramViewer(representations);
	            viewer.setVisible(true);
	        });
	 }

	public static void main(String[] args) {
		new Examples();

	}

}