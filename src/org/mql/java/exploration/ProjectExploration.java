package org.mql.java.exploration;

import java.io.File;
import java.util.Vector;


import org.mql.java.structurememoire.Projet;

public class ProjectExploration {

public ProjectExploration() {
	
	
int i =getPackageList("C:\\Users\\houss\\eclipse-workspace\\p04-xml-parsers").getPackages().size();
System.out.println(i);
}
	
	public Projet getPackageList(String projectpath) {
		File srcDir = new File(projectpath, "\\src");
	
		Projet p=new Projet();
		if (!srcDir.exists() || !srcDir.isDirectory()) {
		    System.out.println("Le chemin spécifié n'existe pas ou n'est pas un répertoire valide.");
		    return null; // ou lancer une exception
		}
		
		p.addrecursive(srcDir);
			
		
	
		return p;
	}
	public static void main(String[] args) {
		new ProjectExploration();
		
	}

}
