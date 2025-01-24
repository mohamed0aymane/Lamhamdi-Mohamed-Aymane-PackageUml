package org.mql.java.univerisite;


public class Teacher extends Person {
    private String subject;

    public Teacher(String id, String name, String email, String subject) {
        super(id, name, email);
        this.subject = subject;
    }

    // Getters and setters
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}

