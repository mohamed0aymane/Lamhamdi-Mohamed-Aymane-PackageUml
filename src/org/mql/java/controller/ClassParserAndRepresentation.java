package org.mql.java.controller;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ClassParserAndRepresentation {
    private List<String> attributes;
    private List<String> methods;
    private List<String> aggregations;
    private List<String> usages;
    private List<String> extensions;
    private List<String> implementations;
    private String className;

    public ClassParserAndRepresentation(String className) {
        this.className = className;
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.aggregations = new ArrayList<>();
        this.usages = new ArrayList<>();
        this.extensions = new ArrayList<>();
        this.implementations = new ArrayList<>();
        parseClass();
    }

    private void parseClass() {
        try {
            Class<?> cls = Class.forName(className);

            // Héritage (extension)
            if (cls.getSuperclass() != null) {
                extensions.add(cls.getSuperclass().getName());
            }

            // Implémentations
            for (Class<?> iface : cls.getInterfaces()) {
                implementations.add(iface.getName());
            }

            // Attributs (agrégation)
            for (Field field : cls.getDeclaredFields()) {
                String modifier = getModifier(field.getModifiers());
                attributes.add(modifier + " " + field.getName() + " : " + field.getType().getSimpleName());

                // Si l'attribut est une classe, on considère cela comme une agrégation
                if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.")) {
                    aggregations.add(field.getType().getName());
                }
            }

            // Méthodes (utilisation)
            for (Method method : cls.getDeclaredMethods()) {
                StringBuilder builder = new StringBuilder();
                String modifier = getModifier(method.getModifiers());
                builder.append(modifier).append(" ").append(method.getName()).append("(");

                for (Parameter param : method.getParameters()) {
                    builder.append(param.getType().getSimpleName()).append(", ");

                    // Si un paramètre est une classe, on considère cela comme une utilisation
                    if (!param.getType().isPrimitive() && !param.getType().getName().startsWith("java.")) {
                        usages.add(param.getType().getName());
                    }
                }
                if (builder.lastIndexOf(",") > 0) {
                    builder.setLength(builder.length() - 2);
                }
                builder.append(") : ").append(method.getReturnType().getSimpleName());
                methods.add(builder.toString());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getModifier(int modifier) {
        switch (modifier & 7) {
            case Modifier.PUBLIC:
                return "+";
            case Modifier.PRIVATE:
                return "-";
            case Modifier.PROTECTED:
                return "#";
            default:
                return "~";
        }
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<String> getMethods() {
        return methods;
    }

    public List<String> getAggregations() {
        return aggregations;
    }

    public List<String> getUsages() {
        return usages;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public List<String> getImplementations() {
        return implementations;
    }

    public String getClassName() {
        return className.substring(className.lastIndexOf('.') + 1); // Extraire seulement le nom de la classe
    }

}
