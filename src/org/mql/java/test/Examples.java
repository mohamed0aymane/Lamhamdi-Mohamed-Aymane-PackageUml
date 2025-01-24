package org.mql.java.test;



import javax.swing.JFrame;


import org.mql.java.controller.ClassParserAndRepresentation;
import org.mql.java.controller.FileExplorer;



public class Examples extends JFrame {

	
	private static final long serialVersionUID = 1L;




	public Examples() {
		
		exp02();
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
	 

	

	public static void main(String[] args) {
		new Examples();

	}

}