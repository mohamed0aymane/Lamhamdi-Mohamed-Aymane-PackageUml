package org.mql.java.xml;

import org.mql.java.model.Package;
import org.mql.java.model.Project;
import org.mql.java.model.Relation;
import org.mql.java.model.RelationType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLParser {


	public void exportProjectToXML(Project project, String outputFilePath) throws Exception {
	    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	    
	    Document document = documentBuilder.newDocument();
	    
	    Element rootElement = document.createElement("Project");
	    rootElement.setAttribute("name", project.getName());
	    document.appendChild(rootElement);
	    
	    for (Package pkg : project.getPackages()) {
	        Element packageElement = document.createElement("Package");
	        packageElement.setAttribute("name", pkg.getName());
	        rootElement.appendChild(packageElement);
	        
	        addClassesToPackageElement(document, packageElement, pkg.getClasses());
	        
	        addInterfacesToPackageElement(document, packageElement, pkg.getInterfaces());
	        
	        addEnumerationsToPackageElement(document, packageElement, pkg.getEnumerations());
	        
	        addAnnotationsToPackageElement(document, packageElement, pkg.getAnnotations());
	        
	        addRelationsToPackageElement(document, packageElement, pkg.getRelations());
	    }

	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(document);
	    StreamResult result = new StreamResult(new File(outputFilePath));
	    transformer.transform(source, result);
	    
	    System.out.println("Le projet a été exporté vers le fichier XML : " + outputFilePath);
	}

	    private void addClassesToPackageElement(Document document, Element packageElement, List<Class<?>> classes) {
	        if (classes.isEmpty()) return;
	        
	        Element classesElement = document.createElement("Classes");
	        packageElement.appendChild(classesElement);
	        
	        for (Class<?> cls : classes) {
	            Element classElement = document.createElement("Class");
	            classElement.setAttribute("name", cls.getSimpleName());
	            classesElement.appendChild(classElement);
	        }
	    }

	    private void addRelationsToPackageElement(Document document, Element packageElement, List<Relation> relations) {
	        if (relations.isEmpty()) return;
	        
	        Element relationsElement = document.createElement("Relations");
	        packageElement.appendChild(relationsElement);
	        
	        for (Relation relation : relations) {
	            Element relationElement = document.createElement("Relation");
	            relationElement.setAttribute("source", relation.getClassSourceName());
	            relationElement.setAttribute("target", relation.getClassTargetName());
	            relationElement.setAttribute("type", relation.getRelationType().name());
	            relationsElement.appendChild(relationElement);
	        }
	    }

	    private void addAnnotationsToPackageElement(Document document, Element packageElement, List<Class<?>> annotations) {
	        if (annotations.isEmpty()) return;
	        
	        Element annotationsElement = document.createElement("Annotations");
	        packageElement.appendChild(annotationsElement);
	        
	        for (Class<?> annotation : annotations) {
	            Element annotationElement = document.createElement("Annotation");
	            annotationElement.setAttribute("name", annotation.getSimpleName());
	            annotationsElement.appendChild(annotationElement);
	        }
	    }

	    private void addEnumerationsToPackageElement(Document document, Element packageElement, List<Class<?>> enumerations) {
	        if (enumerations.isEmpty()) return;
	        
	        Element enumerationsElement = document.createElement("Enumerations");
	        packageElement.appendChild(enumerationsElement);
	        
	        for (Class<?> enumeration : enumerations) {
	            Element enumerationElement = document.createElement("Enumeration");
	            enumerationElement.setAttribute("name", enumeration.getSimpleName());
	            enumerationsElement.appendChild(enumerationElement);
	        }
	    }

	    private void addInterfacesToPackageElement(Document document, Element packageElement, List<Class<?>> interfaces) {
	        if (interfaces.isEmpty()) return;
	        
	        Element interfacesElement = document.createElement("Interfaces");
	        packageElement.appendChild(interfacesElement);
	        
	        for (Class<?> interfaceCls : interfaces) {
	            Element interfaceElement = document.createElement("Interface");
	            interfaceElement.setAttribute("name", interfaceCls.getSimpleName());
	            interfacesElement.appendChild(interfaceElement);
	        }
	    }

	   
	

}
