package org.mql.java.xml;

import java.io.FileWriter;
import java.io.IOException;

import org.mql.java.model.Package;
import org.mql.java.model.Project;
import org.mql.java.model.Relation;

public class XmiGenerator {

    public void generateXmi(Project project, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<uml:Model xmi:version=\"2.1\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:uml=\"http://www.omg.org/spec/UML/2.1.2\">\n");

            for (Package pkg : project.getPackages()) {
                writer.write("  <packagedElement xmi:type=\"uml:Package\" name=\"" + pkg.getName() + "\">\n");

                for (Class<?> cls : pkg.getClasses()) {
                    writer.write("    <packagedElement xmi:type=\"uml:Class\" name=\"" + cls.getSimpleName() + "\"/>\n");
                }

                for (Class<?> iface : pkg.getInterfaces()) {
                    writer.write("    <packagedElement xmi:type=\"uml:Interface\" name=\"" + iface.getSimpleName() + "\"/>\n");
                }

                for (Class<?> enumCls : pkg.getEnumerations()) {
                    writer.write("    <packagedElement xmi:type=\"uml:Enumeration\" name=\"" + enumCls.getSimpleName() + "\"/>\n");
                }

                for (Relation relation : pkg.getRelations()) {
                    writer.write("    <ownedRelationship xmi:type=\"uml:Association\" source=\"" + relation.getClassSourceName() +
                            "\" target=\"" + relation.getClassTargetName() + "\"/>\n");
                }

                writer.write("  </packagedElement>\n");
            }

            writer.write("</uml:Model>");
            System.out.println("Fichier XMI généré avec succès : " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
