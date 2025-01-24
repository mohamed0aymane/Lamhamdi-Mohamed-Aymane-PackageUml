package org.mql.java.univerisite;



import java.util.ArrayList;
import java.util.List;
/*
 * Relations utilisées :
Héritage :

Les classes Student et Teacher héritent de la classe Person.
Composition :

La classe Classroom contient une instance de Teacher et une liste d'instances de Student.
La classe School contient une liste de Classroom.
Agrégation :

La liste des étudiants dans 
Classroom montre une agrégation car 
les objets Student existent indépendamment de la classe Classroom.*/
public class StudentService {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student findStudentById(String id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}

