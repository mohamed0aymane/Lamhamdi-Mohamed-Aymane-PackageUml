package org.mql.java.xml;

import org.mql.java.model.Package;
import org.mql.java.model.Project;
import org.mql.java.model.Relation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import java.io.File;
import java.util.List;

public class XMLParserWithNode {

    public void exportProjectToXML(Project project, String outputFilePath) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        
        Document document = documentBuilder.newDocument();
        
        Element rootElement = document.createElement("Project");
        rootElement.setAttribute("name", project.getName());
        document.appendChild(rootElement);
        
        XMLNode rootNode = new XMLNode(rootElement);
        
        for (Package pkg : project.getPackages()) {
            XMLNode packageNode = new XMLNode(document.createElement("Package"));
            ((Element) packageNode.getNode()).setAttribute("name", pkg.getName());
            rootNode.appendChild(packageNode);
            
            addClassesToPackageElement(packageNode, pkg.getClasses());
            addInterfacesToPackageElement(packageNode, pkg.getInterfaces());
            
            addEnumerationsToPackageElement(packageNode, pkg.getEnumerations());
            
            addAnnotationsToPackageElement(packageNode, pkg.getAnnotations());
            
            addRelationsToPackageElement(packageNode, pkg.getRelations());
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(outputFilePath));
        transformer.transform(source, result);
        
        System.out.println("Le projet a été exporté vers le fichier XML : " + outputFilePath);
    }

    private void addClassesToPackageElement(XMLNode packageNode, List<Class<?>> classes) {
        if (classes.isEmpty()) return;
        
        XMLNode classesNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Classes"));
        packageNode.appendChild(classesNode);
        
        for (Class<?> cls : classes) {
            XMLNode classNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Class"));
            ((Element) classNode.getNode()).setAttribute("name", cls.getSimpleName());  // Correction ici
            classesNode.appendChild(classNode);
        }
    }

    private void addRelationsToPackageElement(XMLNode packageNode, List<Relation> relations) {
        if (relations.isEmpty()) return;
        
        XMLNode relationsNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Relations"));
        packageNode.appendChild(relationsNode);
        
        for (Relation relation : relations) {
            XMLNode relationNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Relation"));
            ((Element) relationNode.getNode()).setAttribute("source", relation.getClassSourceName());
            ((Element) relationNode.getNode()).setAttribute("target", relation.getClassTargetName());
            ((Element) relationNode.getNode()).setAttribute("type", relation.getRelationType().name());
            relationsNode.appendChild(relationNode);
        }
    }

    private void addEnumerationsToPackageElement(XMLNode packageNode, List<Class<?>> enumerations) {
        if (enumerations.isEmpty()) return;
        
        XMLNode enumerationsNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Enumerations"));
        packageNode.appendChild(enumerationsNode);
        
        for (Class<?> enumeration : enumerations) {
            XMLNode enumerationNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Enumeration"));
            ((Element) enumerationNode.getNode()).setAttribute("name", enumeration.getSimpleName());
            enumerationsNode.appendChild(enumerationNode);
        }
    }

    private void addInterfacesToPackageElement(XMLNode packageNode, List<Class<?>> interfaces) {
        if (interfaces.isEmpty()) return;
        
        XMLNode interfacesNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Interfaces"));
        packageNode.appendChild(interfacesNode);
        
        for (Class<?> interfaceCls : interfaces) {
            XMLNode interfaceNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Interface"));
            ((Element) interfaceNode.getNode()).setAttribute("name", interfaceCls.getSimpleName());
            interfacesNode.appendChild(interfaceNode);
        }
    }

    private void addAnnotationsToPackageElement(XMLNode packageNode, List<Class<?>> annotations) {
        if (annotations.isEmpty()) return;
        
        XMLNode annotationsNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Annotations"));
        packageNode.appendChild(annotationsNode);
        
        for (Class<?> annotationCls : annotations) {
            XMLNode annotationNode = new XMLNode(packageNode.getNode().getOwnerDocument().createElement("Annotation"));
            ((Element) annotationNode.getNode()).setAttribute("name", annotationCls.getSimpleName());  // Correction ici
            annotationsNode.appendChild(annotationNode);
        }
    }
}
