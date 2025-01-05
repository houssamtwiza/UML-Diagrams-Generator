package org.mql.java.structurememoire;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
// classe Java qui sert de structure de données en mémoire pour organiser les informations extraites
public class Projet {

    private String name;
    private List<Package> packages=new ArrayList<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Package> getPackages() {
		return packages;
	}
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	} 
public void addrecursive(File f) throws ClassNotFoundException {
	if (f.isFile() || packages==null || f==null ) {
	}
	else{
		Package p= Package.exploreFile(f);
	
		packages.add(p);
		File fr[]=f.listFiles();
		for(File ff:fr){
		
			addrecursive(ff);
		}
	}
}
/*if (files != null) {
    for (File file : files) {
        if (file.isDirectory()) {
            // Si le fichier est un répertoire, c'est probablement un package
            String subPackageName = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
            System.out.println("Package : " + subPackageName);
            addrecursive(file, subPackageName); // Récursion pour explorer ce sous-dossier
        }
    }
}*/
}
