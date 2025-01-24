package org.mql.java.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.model.Project;
import org.mql.java.model.Package;
//
//public class FileExplorer {
//    private String pathPackage;
//    private String namePackage;
//    private List<Class<?>> classes;
//    private List<Class<?>> enumerations;
//    private List<Class<?>> annotations;
//    private List<Class<?>> interfaces;
//  
//
//    public FileExplorer(String pathPackage) {
//        this.pathPackage = pathPackage;
//        this.classes = new ArrayList<>();
//        this.enumerations = new ArrayList<>();
//        this.annotations = new ArrayList<>();
//        this.interfaces = new ArrayList<>();
//       
//
//        // Deduire le package à partir du chemin donne
//        this.namePackage = pathPackage
//                .replace("src/", "")   // Retirer le prefixe src/
//                .replace("/", ".")     // Remplacer les separateurs de dossier par des points
//                .replace("\\", ".")    // Compatibilite Windows
//                + ".";                 // Ajouter un point à la fin pour les sous-packages
//    }
//
//    // Constructeur pour les sous-packages
//    private FileExplorer(String pathPackage, String packageName) {
//        this.pathPackage = pathPackage;
//        this.namePackage = packageName;
//        this.classes = new ArrayList<>();
//        this.enumerations = new ArrayList<>();
//        this.annotations = new ArrayList<>();
//        this.interfaces = new ArrayList<>();
//        
//    }
//
//    public void loadPackages() {
//        try {
//            File directory = new File(pathPackage);
//
//            if (!directory.exists()) {
//                System.out.println("Le chemin specifie n'existe pas : \n  - " + pathPackage);
//                return;
//            }
//
//            File[] fileList = directory.listFiles();
//
//            if (fileList == null || fileList.length == 0) {
//                System.out.println("Aucun fichier dans le repertoire :  \n  - " + pathPackage);
//                return;
//            }
//
//            for (File file : fileList) {
//                if (file.isDirectory()) {
//                    // Gestion recursive pour les sous-dossiers
//                    String subPackage = namePackage + file.getName() + ".";
//                    FileExplorer subExplorer = new FileExplorer(file.getPath(), subPackage);
//                    subExplorer.loadPackages();
//                    mergeResults(subExplorer);
//                } else if (file.getName().endsWith(".java")) {
//                    String className = file.getName().replace(".java", "");
//                    try {
//                        Class<?> cls = Class.forName(namePackage + className);
//                        categorizeClass(cls);
//                    } catch (ClassNotFoundException e) {
//                        System.out.println("Classe introuvable : " + namePackage + className);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void mergeResults(FileExplorer subExplorer) {
//        classes.addAll(subExplorer.getClasses());
//        enumerations.addAll(subExplorer.getEnumerations());
//        annotations.addAll(subExplorer.getAnnotations());
//        interfaces.addAll(subExplorer.getInterfaces());
//        
//    }
//
//    private void categorizeClass(Class<?> cls) {
//        if (cls.isAnnotation()) {
//            annotations.add(cls);
//        } else if (cls.isEnum()) {
//            enumerations.add(cls);
//        } else if (cls.isInterface()) {
//            interfaces.add(cls);
//        } else {
//            classes.add(cls);
//        }
//    }
//
//    
//
//
//    public List<Class<?>> getClasses() {
//        return classes;
//    }
//
//    public List<Class<?>> getEnumerations() {
//        return enumerations;
//    }
//
//    public List<Class<?>> getAnnotations() {
//        return annotations;
//    }
//
//    public List<Class<?>> getInterfaces() {
//        return interfaces;
//    }
//    public String getClassName(Class<?> cls) {
//        return cls.getName(); // Retourne le nom complet de la classe
//    }
//
//
//
//    public void printResults() {
//        System.out.println("\nClasses trouvees :");
//        classes.forEach(cls -> System.out.println("  - " + cls.getName()));
//
//        System.out.println("\nEnumerations trouvees :");
//        enumerations.forEach(enumCls -> System.out.println("  - " + enumCls.getName()));
//
//        System.out.println("\nAnnotations trouvees :");
//        annotations.forEach(annotation -> System.out.println("  - " + annotation.getName()));
//
//        System.out.println("\nInterfaces trouvees :");
//        interfaces.forEach(interfaceCls -> System.out.println("  - " + interfaceCls.getName()));
//
//        
//    }
//}
public class FileExplorer {
    private Project project;
    private String path;

    public FileExplorer(String projectName, String path) {
        this.project = new Project(projectName);
        this.path = path;
    }

    public void loadProject() {
        exploreDirectory(new File(path), "");
    }

    private void exploreDirectory(File directory, String packageName) {
        if (!directory.exists()) {
            System.out.println("Le chemin spécifié n'existe pas : " + directory.getPath());
            return;
        }

        File[] fileList = directory.listFiles();
        if (fileList == null || fileList.length == 0) {
            System.out.println("Aucun fichier dans le répertoire : " + directory.getPath());
            return;
        }

        Package currentPackage = new Package(packageName);
        for (File file : fileList) {
            if (file.isDirectory()) {
                exploreDirectory(file, packageName + file.getName() + ".");
            } else if (file.getName().endsWith(".java")) {
                String className = packageName + file.getName().replace(".java", "");
                try {
                    Class<?> cls = Class.forName(className);
                    categorizeClass(currentPackage, cls);
                } catch (ClassNotFoundException e) {
                    System.out.println("Classe introuvable : " + className);
                }
            }
        }

        if (!currentPackage.getClasses().isEmpty() ||
            !currentPackage.getInterfaces().isEmpty() ||
            !currentPackage.getEnumerations().isEmpty() ||
            !currentPackage.getAnnotations().isEmpty()) {
            project.addPackage(currentPackage);
        }
    }

    private void categorizeClass(Package pkg, Class<?> cls) {
        if (cls.isAnnotation()) {
            pkg.addAnnotation(cls);
        } else if (cls.isEnum()) {
            pkg.addEnumeration(cls);
        } else if (cls.isInterface()) {
            pkg.addInterface(cls);
        } else {
            pkg.addClass(cls);
        }
    }
    
    public List<Class<?>> getClasses() {
        List<Class<?>> classes = new ArrayList<>();
        for (Package pkg : project.getPackages()) {
            classes.addAll(pkg.getClasses());
        }
        return classes;
    }
    public List<Package> getPackages() {
        return project.getPackages();
    }

    public void printResults() {
        System.out.println("Projet : " + project.getName());
        for (Package pkg : project.getPackages()) {
        	String packageName= pkg.getName().endsWith(".")  ? pkg.getName().substring(0, pkg.getName().length() - 1) : pkg.getName();
            System.out.println("\nPackage : " + packageName);
            System.out.println("  Classes :");
            pkg.getClasses().forEach(cls -> System.out.println("    - " + cls.getSimpleName()));
            System.out.println("  Interfaces :");
            pkg.getInterfaces().forEach(cls -> System.out.println("    - " + cls.getSimpleName()));
            System.out.println("  Énumérations :");
            pkg.getEnumerations().forEach(cls -> System.out.println("    - " + cls.getSimpleName()));
            System.out.println("  Annotations :");
            pkg.getAnnotations().forEach(cls -> System.out.println("    - " + cls.getSimpleName()));
        }
    }

    public Project getProject() {
        return project;
    }
 }


