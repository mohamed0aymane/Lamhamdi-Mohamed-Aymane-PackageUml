package org.mql.java.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileExplorer {
	private String pathPackage;
	private String namePackage;
	private List<Object> files;
	
	public FileExplorer(String pathPackage) {
	    this.pathPackage = pathPackage;
	    this.files = new ArrayList<>();
	    
	    // Déduire le package à partir du chemin donné
	    this.namePackage = pathPackage
	            .replace("src/", "")   // Retirer le préfixe src/
	            .replace("/", ".")     // Remplacer les séparateurs de dossier par des points
	            .replace("\\", ".")    // Compatibilité Windows
	            + ".";                 // Ajouter un point à la fin pour les sous-packages
	}

	//Constructeur pour les sous-packages
	private FileExplorer(String pathPackage,String packageName) {
		this.namePackage="";
		this.pathPackage=pathPackage;
		this.files=new ArrayList<>();
		
	}
	public List<Object> loadPackages(){
		try{ 
			File directory =new File(pathPackage);
				
				if(!directory .exists()) {
					System.out.println("le chemin specifie n'existe pas : " +pathPackage);
					return null;
				}
					File[] fileList=directory.listFiles();
					
					if(fileList==null || fileList.length==0) { 
						System.out.println("Aucun fichier dans le repertoire : " +pathPackage);
						return null ;
					}
					
					for(File file:fileList) {
						if(file.isDirectory()) {
							//gestion recursive pour les sous-dossiers
							String subPackage=namePackage+file.getName() + ".";
							FileExplorer subExplorer =new FileExplorer(file.getPath(),subPackage);
						//On stocke les classes du fichier
						}else if(file.getName().endsWith(".java")) {
							String className=file.getName().replace(".java", "");
							
								try{
									Class<?> cls=Class.forName(namePackage + className);
									files.add(cls);
									
								} catch(ClassNotFoundException e) {
									System.out.println("Classe introuvable :" + namePackage + className);
								}
							
						}
					}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return files;
		
	}
	

}

