package org.mql.java.structurememoire;

import java.io.File;
import java.net.MalformedURLException;
import java.util.*;

public class Package {
	private String name;
	private List<Classe> classes=new ArrayList<>();
	private List<Package> relationsimport=new ArrayList<>();


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Classe> getClasses() {
		return classes;
	}
	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}
	public  Package explorePackages(File f,String basePath) throws ClassNotFoundException, MalformedURLException {
		Package p=new Package();
		// Obtenir le chemin relatif par rapport à la racine du projet
	    String relativePath = f.getAbsolutePath().replace(basePath, "").replace(File.separator, ".");
	    
	    // Retirer le premier point si présent
	    if (relativePath.startsWith(".")) {
	        relativePath = relativePath.substring(1);
	    }
	    
	    // Définir le nom complet du package
	    p.name = relativePath;

	//	p.name=f.getName();
		File ff[] = f.listFiles();
		
		for (File fg:ff) {
			if (fg.isFile() && fg.getName().endsWith(".class")) {
				
			//if (fg.isFile() && fg.getName().endsWith(".java")) {	
			Classe c=new Classe();
			Classe c1=		c.exploreClasses(fg,p.name,basePath);
		//	relationsimport.addAll(c1.getpacksimport());
			//setRelationsimport	(relationsimport);
			p.classes.add(c1);
			}}
		return p;
	}
	public List<Package> getRelationsimport() {
		return relationsimport;
	}
	public void setRelationsimport(List<Package> relationsimport) {
		this.relationsimport = relationsimport;
	}

}
