package org.mql.java.controller;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.mql.java.model.Project;
import org.mql.java.model.Package;
import org.mql.java.model.Relation;
import org.mql.java.model.RelationType;

public class FileExplorer {
    private Project project;
    private String path;

    public FileExplorer(String projectName, String path) {
        this.project = new Project(projectName);
        this.path = path;
    }

    public void loadProject() {
        exploreDirectory(new File(path), "");
        determineRelations(); 
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
    
    private void determineRelations() {
        for (Package pkg : project.getPackages()) {
            for (Class<?> cls : pkg.getClasses()) {
                // Vérification de l'héritage
                Class<?> superClass = cls.getSuperclass();
                if (superClass != null && superClass != Object.class) {
                    pkg.addRelation(new Relation(superClass.getSimpleName(), cls.getSimpleName(), RelationType.INHERITANCE));
                }

                // Vérification des interfaces implémentées
                Class<?>[] interfaces = cls.getInterfaces();
                for (Class<?> interfaceCls : interfaces) {
                    pkg.addRelation(new Relation(interfaceCls.getSimpleName(), cls.getSimpleName(), RelationType.IMPLEMENTATION));
                }

                // Vérification des relations dans les champs
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    Class<?> fieldType = field.getType();

                    // Relation d'agrégation ou de composition
                    if (isClassInPackage(fieldType, pkg)) {
                        RelationType relationType = determineCompositionOrAggregation(fieldType);
                        pkg.addRelation(new Relation(cls.getSimpleName(), fieldType.getSimpleName(), relationType));
                    } else if (isCollection(fieldType)) {
                        Class<?> genericType = getGenericType(field);
                        if (genericType != null && isClassInPackage(genericType, pkg)) {
                            // L'agrégation est définie ici
                            pkg.addRelation(new Relation(cls.getSimpleName(), genericType.getSimpleName(), RelationType.AGGREGATION));
                        }
                    }

                    // Relation de dépendance (type générique ou classe)
                    if (isClassInPackage(fieldType, pkg)) {
                        pkg.addRelation(new Relation(cls.getSimpleName(), fieldType.getSimpleName(), RelationType.DEPENDANCE));
                    }

                    // Relation de réalisation (interface implémentée ou classe concrète)
                    if (fieldType.isInterface()) {
                        pkg.addRelation(new Relation(cls.getSimpleName(), fieldType.getSimpleName(), RelationType.REALISATION));
                    }
                }
            }
        }
    }

    private boolean isCollection(Class<?> cls) {
        // Vérification si le type est une collection générique
        return Collection.class.isAssignableFrom(cls) || List.class.isAssignableFrom(cls) || Set.class.isAssignableFrom(cls);
    }

    private RelationType determineCompositionOrAggregation(Class<?> cls) {
        // Vérification de l'agrégation ou de la composition
        if (Collection.class.isAssignableFrom(cls)) {
            return RelationType.AGGREGATION;
        }
        return RelationType.COMPOSITION;
    }

    private Class<?> getGenericType(Field field) {
        try {
            if (field.getGenericType() instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) field.getGenericType();
                
                if (type.getActualTypeArguments().length > 0 && type.getActualTypeArguments()[0] instanceof Class) {
                    return (Class<?>) type.getActualTypeArguments()[0];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isClassInPackage(Class<?> cls, Package pkg) {
        return cls.getPackage() != null && cls.getPackage().getName().equals(pkg.getName());
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
            String packageName = pkg.getName().endsWith(".") ? pkg.getName().substring(0, pkg.getName().length() - 1) : pkg.getName();
            System.out.println("\nPackage : " + packageName);
            System.out.println("  Classes :");
            pkg.getClasses().forEach(cls -> System.out.println("    - " + cls.getSimpleName()));
            System.out.println("  Interfaces :");
            pkg.getInterfaces().forEach(cls -> System.out.println("    - " + cls.getSimpleName()));
            System.out.println("  Énumérations :");
            pkg.getEnumerations().forEach(cls -> System.out.println("    - " + cls.getSimpleName()));
            System.out.println("  Annotations :");
            pkg.getAnnotations().forEach(cls -> System.out.println("    - " + cls.getSimpleName()));
            System.out.println("  Relations :");
            
            pkg.getRelations().forEach(relation -> 
                System.out.println("    - " + relation.getClassSourceName() + " " 
                    + relation.getRelationType() + " " + relation.getClassTargetName()));
        }
    }

    public Project getProject() {
        return project;
    }
}
