package org.mql.java.test;



import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.mql.java.controller.ClassParserAndRepresentation;
import org.mql.java.controller.FileExplorer;

import org.mql.java.model.Project;
import org.mql.java.ui.UmlDiagramViewer;
import org.mql.java.xml.XMLParser;




public class Examples extends JFrame {

	
	private static final long serialVersionUID = 1L;

	public Examples() {
		
		exp05();
	}
	 void exp01() {
		 
		 FileExplorer explorer = new FileExplorer("Lamhamdi Mohamed Aymane-tp04-packageUml","src");
	        explorer.loadProject();
	        explorer.printResults();
	 }
	
	//parser  les methodes et les attributs d'un classe
	 void exp02() {
		 String path = "org.mql.java.model.Package";
		 ClassParserAndRepresentation representation = new ClassParserAndRepresentation(path);
		    System.out.println("Les attributs : " + representation.getAttributes());
		    System.out.println("Les méthodes : " + representation.getMethods());
		    System.out.println("Les agrégations : " + representation.getAggregations());
		    System.out.println("Les utilisations : " + representation.getUsages());
		    System.out.println("Les extensions : " + representation.getExtensions());
		    System.out.println("Les implémentations : " + representation.getImplementations());
	}
	

	//parser XMI
	 void exp03() {
		
	 }
	 //parser XML
	 void exp04() {
		 try {
	            
	            FileExplorer fileExplorer = new FileExplorer("Lamhamdi Mohamed Aymane-tp04-packageUml","src");
	            fileExplorer.loadProject();
	            Project project = fileExplorer.getProject();

	            XMLParser xmlExporter = new XMLParser();
	            xmlExporter.exportProjectToXML(project, "resources/projet.xml");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	  }
		

	 //	UmlDiagramViewer
	 void exp05() {
		 SwingUtilities.invokeLater(UmlDiagramViewer::new);
	 }
	 

	

	public static void main(String[] args) {
		new Examples();

	}

}