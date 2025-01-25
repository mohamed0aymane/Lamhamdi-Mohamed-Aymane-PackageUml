package org.mql.java.controller;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
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
                Class<?> superClass = cls.getSuperclass();
                if (superClass != null && superClass != Object.class) {
                    pkg.addRelation(new Relation(superClass.getSimpleName(), cls.getSimpleName(), RelationType.INHERITANCE));
                }

                Class<?>[] interfaces = cls.getInterfaces();
                for (Class<?> interfaceCls : interfaces) {
                    pkg.addRelation(new Relation(interfaceCls.getSimpleName(), cls.getSimpleName(), RelationType.IMPLEMENTATION));
                }

                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    detectRelationsForField(cls, field, pkg.getRelations(),pkg);
                }
            }
        }
    }

    private void detectRelationsForField(Class<?> cls, Field field, List<Relation> relations, Package pkg) {
        Class<?> fieldType = field.getType();
        Type genericType = field.getGenericType();

        if (Collection.class.isAssignableFrom(fieldType)) {
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericType;
                Type[] typeArguments = pt.getActualTypeArguments();
                for (Type typeArgument : typeArguments) {
                    if (typeArgument instanceof Class<?>) {
                        Class<?> genericClass = (Class<?>) typeArgument;
                        if (isCustomClass(genericClass)) {
                            relations.add(new Relation(cls.getSimpleName(), genericClass.getSimpleName(), RelationType.AGGREGATION));
                        }
                    }
                }
            }
        } 
       
        else if (fieldType.isArray()) {
            Class<?> componentType = fieldType.getComponentType();
            if (isCustomClass(componentType)) {
                relations.add(new Relation(cls.getSimpleName(), componentType.getSimpleName(), RelationType.AGGREGATION));
            }
        } 
        else if (isCustomClass(fieldType)) {
            if (fieldType != null && !fieldType.isPrimitive() && !fieldType.isInterface()) {
                relations.add(new Relation(cls.getSimpleName(), fieldType.getSimpleName(), RelationType.COMPOSITION));
            } else {
                
                relations.add(new Relation(cls.getSimpleName(), fieldType.getSimpleName(), RelationType.IMPLEMENTATION));
            }
        }
        else if (fieldType.isInterface()) {
            relations.add(new Relation(cls.getSimpleName(), fieldType.getSimpleName(), RelationType.REALISATION));
        }
        else if (isClassInPackage(fieldType, pkg)) {
            relations.add(new Relation(cls.getSimpleName(), fieldType.getSimpleName(), RelationType.DEPENDANCE));
        }
    }

    private boolean isCustomClass(Class<?> cls) {
        return !(cls.isPrimitive() || cls == String.class || cls == Integer.class || cls == Double.class || 
                 cls == Float.class || cls == Long.class || cls == Short.class || cls == Boolean.class || 
                 cls == Byte.class || cls == Character.class);
    }

    private boolean isClassInPackage(Class<?> cls, Package pkg) {
        return cls.getPackage() != null && cls.getPackage().getName().equals(pkg.getName());
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
    public List<Package> getPackages() {
        return project.getPackages();
    }
}
