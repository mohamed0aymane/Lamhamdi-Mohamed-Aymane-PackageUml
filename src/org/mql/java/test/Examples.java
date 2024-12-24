package org.mql.java.test;

import java.util.List;

import org.mql.java.controller.ClassParserAndRepresentation;
import org.mql.java.controller.FileExplorer;


public class Examples {

	public Examples() {
		exp01();
	}
	//parser  les methodes et les attributs
	 void exp01() {
		 String path = "org/mql/java/ui/shapes";
		 ClassParserAndRepresentation representation = new ClassParserAndRepresentation(path);
		 System.out.println("Les attributs : "+representation.getAttributes());
	     System.out.println("Les methodes  : "+representation.getMethodes());
	}
	 
	 void exp02() {
		 String path = "src/org/mql/java/controller/explorer"; // Remplacez par le chemin correct vers vos fichiers Java
	        FileExplorer explorer = new FileExplorer(path);
	        List<Object> files = explorer.loadPackages();
	        
	        if (files != null) {
	            System.out.println("Fichiers et classes détectés : ");
	            for (Object file : files) {
	                System.out.println(file);
	            }
	        } 
	 }

	public static void main(String[] args) {
		new Examples();

	}

}
