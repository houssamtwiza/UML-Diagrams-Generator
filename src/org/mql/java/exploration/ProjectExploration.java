package org.mql.java.exploration;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Vector;

import org.mql.java.structurememoire.Classe;
import org.mql.java.structurememoire.Package;
import org.mql.java.structurememoire.Projet;

public class ProjectExploration {

public ProjectExploration() throws ClassNotFoundException, MalformedURLException {
	
	Projet p= ProjectExploration.getPackageList("C:\\Users\\houss\\eclipse-workspace\\p02_generics");
  List<Package> pp= p.getPackages();
	System.out.println(pp.get(1).getClasses().get(4).getRelationsAggregation().size());
	for (Package ppp:p.getPackages()) {
	
		for(Classe cc:ppp.getClasses()) {
			
				
				System.out.println(cc.getName() );
			
		}}
	}

	
	public static Projet getPackageList(String projectpath) throws ClassNotFoundException, MalformedURLException {
		File srcDir = new File(projectpath, "\\bin");
		System.out.println(srcDir.getAbsolutePath());
		Projet p=new Projet();
		if (!srcDir.exists() || !srcDir.isDirectory()) {
		    System.out.println("Le chemin spécifié n'existe pas ou n'est pas un répertoire valide.");
		    return null; // ou lancer une exception
		}
		
		p.explorerecursivepackgesinprojet(srcDir);//cette methode va construire une classe Projet a partir du chemin src de notre projet
			
		
	
		return p;
	}
	public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
		new ProjectExploration();
		
	}

}
