package org.mql.java.model;

import java.util.ArrayList;
import java.util.List;


public class Project {
	private String name;
    private List<Package> packages;

    public Project(String name) {
        this.name = name;
        this.packages = new ArrayList<>();
    }

    public void addPackage(Package pkg) {
        packages.add(pkg);
    }

    public List<Package> getPackages() {
        return packages;
    }

    public String getName() {
        return name;
    }
	

}
