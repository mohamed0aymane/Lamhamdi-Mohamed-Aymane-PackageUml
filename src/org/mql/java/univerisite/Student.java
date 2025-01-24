package org.mql.java.univerisite;



public class Student extends Person {
    private String level;

    public Student(String id, String name, String email, String level) {
        super(id, name, email);
        this.level = level;
    }

    // Getters and setters
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}
