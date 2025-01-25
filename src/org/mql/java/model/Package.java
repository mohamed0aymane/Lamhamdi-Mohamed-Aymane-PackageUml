package org.mql.java.model;

import java.util.ArrayList;
import java.util.List;

public class Package {
    private String name;
    private List<Class<?>> classes;
    private List<Class<?>> interfaces;
    private List<Class<?>> enumerations;
    private List<Class<?>> annotations;
    private List<Relation> relations;

    public Package(String name) {
        this.name = name;
        this.classes = new ArrayList<>();
        this.interfaces = new ArrayList<>();
        this.enumerations = new ArrayList<>();
        this.annotations = new ArrayList<>();
        this.relations = new ArrayList<>();
    }

    public void addClass(Class<?> cls) {
        classes.add(cls);
    }

    public void addInterface(Class<?> cls) {
        interfaces.add(cls);
    }

    public void addEnumeration(Class<?> cls) {
        enumerations.add(cls);
    }

    public void addAnnotation(Class<?> cls) {
        annotations.add(cls);
    }

    public String getName() {
        return name;
    }

    public List<Class<?>> getClasses() {
        return classes;
    }

    public List<Class<?>> getInterfaces() {
        return interfaces;
    }

    public List<Class<?>> getEnumerations() {
        return enumerations;
    }

    public List<Class<?>> getAnnotations() {
        return annotations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClasses(List<Class<?>> classes) {
        this.classes = classes;
    }

    public void setInterfaces(List<Class<?>> interfaces) {
        this.interfaces = interfaces;
    }

    public void setEnumerations(List<Class<?>> enumerations) {
        this.enumerations = enumerations;
    }

    public void setAnnotations(List<Class<?>> annotations) {
        this.annotations = annotations;
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    public List<Relation> getRelations() {
        return relations;
    }


}
