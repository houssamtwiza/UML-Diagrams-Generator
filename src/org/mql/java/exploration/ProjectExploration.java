package org.mql.java.exploration;

import java.io.File;
import java.util.Vector;

import org.mql.java.structurememoire.Classe;
import org.mql.java.structurememoire.Package;
import org.mql.java.structurememoire.Projet;

public class ProjectExploration {

public ProjectExploration() throws ClassNotFoundException {
	
	Projet p= ProjectExploration.getPackageList("C:\\Users\\houss\\eclipse-workspace\\p04-xml-parsers");
	for (Package pp:p.getPackages()) {
		for(Classe cc:pp.getClasses()) {
			System.out.print(cc.getName());
		}
	}

}
	
	public static Projet getPackageList(String projectpath) throws ClassNotFoundException {
		File srcDir = new File(projectpath, "\\src");
	
		Projet p=new Projet();
		if (!srcDir.exists() || !srcDir.isDirectory()) {
		    System.out.println("Le chemin spécifié n'existe pas ou n'est pas un répertoire valide.");
		    return null; // ou lancer une exception
		}
		
		p.explorerecursivepackgesinprojet(srcDir);
			
		
	
		return p;
	}
	public static void main(String[] args) throws ClassNotFoundException {
		new ProjectExploration();
		
	}

}
