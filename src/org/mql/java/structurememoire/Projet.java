package org.mql.java.structurememoire;

import java.io.File;
import java.net.MalformedURLException;
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
public void explorerecursivepackgesinprojet(File f) throws ClassNotFoundException, MalformedURLException {
	if (f.isFile() || packages==null || f==null ) {
	}
	else{
		Package p= new Package();
		Package p1=	p.explorePackages(f);//cette methode statique va explorer le fichier et va en quelque sorte le transformer en package equivalent
	
		packages.add(p1);
		File fr[]=f.listFiles();
		for(File ff:fr){
		
			explorerecursivepackgesinprojet(ff);
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
