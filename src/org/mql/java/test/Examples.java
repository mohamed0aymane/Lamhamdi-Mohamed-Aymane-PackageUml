package org.mql.java.test;

import java.util.List;

import org.mql.java.controller.ClassParserAndRepresentation;
import org.mql.java.controller.FileExplorer;


public class Examples {

	public Examples() {
		exp02();
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

	public static void main(String[] args) {
		new Examples();

	}

}