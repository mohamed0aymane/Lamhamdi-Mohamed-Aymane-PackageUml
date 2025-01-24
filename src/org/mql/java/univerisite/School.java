package org.mql.java.univerisite;


import java.util.List;

public class School {
    private String name;
    private List<Classroom> classrooms; // Composition

    public School(String name, List<Classroom> classrooms) {
        this.name = name;
        this.classrooms = classrooms;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Classroom> getClassrooms() { return classrooms; }
    public void setClassrooms(List<Classroom> classrooms) { this.classrooms = classrooms; }
}
