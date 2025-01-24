package org.mql.java.exploration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

import org.mql.java.structurememoire.Classe;
import org.mql.java.structurememoire.Package;
import org.mql.java.structurememoire.Projet;

public class ProjectExploration {

public ProjectExploration(String s) throws ClassNotFoundException, IOException {
	
	Projet p= ProjectExploration.getPackageList(s);
  List<Package> pp= p.getPackages();
	//System.out.println(pp.get(1).getClasses().get(4).getRelationsAggregation().size());
	for (Package ppp:p.getPackages()) {
	
		for(Classe cc:ppp.getClasses()) {
			
				
				System.out.println(cc.getName() );
			
		}}
	}

	
	public static Projet getPackageList(String projectpath) throws ClassNotFoundException, IOException {
		File srcDir = new File(projectpath, "\\bin");
	//	System.out.println(srcDir.getAbsolutePath());
		final String s=srcDir.getAbsolutePath();
		Projet p=new Projet();
		 File file = new File(projectpath);
		   // Récupérer le nom du fichier avec extension
   

       
		p.setName(   file.getName() );
	/*	if (!srcDir.exists() || !srcDir.isDirectory()) {
		    System.out.println("Le chemin spécifié n'existe pas ou n'est pas un répertoire valide.");
		    return null; // ou lancer une exception
		}*/
		
		//p.explorerecursivepackgesinprojet(srcDir);//cette methode va construire une classe Projet a partir du chemin src de notre projet
	//	File srcDir = new File(projectpath);
		p.explorerecursivepackgesinprojet(srcDir,s );
		p.analyzeSourceCode(Paths.get(projectpath));	
		 String dotFileName = "packageDiagram.dot";
	        p.generateDotFile(dotFileName);  // Générer le fichier DOT pour Graphviz

	        System.out.println("Diagram generated! Use Graphviz to render the 'packageDiagram.dot' file.");
		
	        // Générer l'image à partir du fichier DOT
	        p.generateImageFromDot("packageDiagram.dot", "packageDiagram.png");
		return p;
	}
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		new ProjectExploration("C:\\Users\\houss\\eclipse-workspace\\p02_generics");
		
	}

}
