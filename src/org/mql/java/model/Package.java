package org.mql.java.model;

import java.util.ArrayList;
import java.util.List;

//representation un package
public class Package {
	  private String name;
	    private List<Class<?>> classes;
	    private List<Class<?>> interfaces;
	    private List<Class<?>> enumerations;
	    private List<Class<?>> annotations;

	    public Package(String name) {
	        this.name = name;
	        this.classes = new ArrayList<>();
	        this.interfaces = new ArrayList<>();
	        this.enumerations = new ArrayList<>();
	        this.annotations = new ArrayList<>();
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
}
