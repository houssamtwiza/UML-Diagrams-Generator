package org.mql.java.parse;

import java.lang.reflect.Field;

import org.mql.java.structurememoire.Classe;
import org.mql.java.structurememoire.Projet;
import org.mql.java.structurememoire.Package;

public class ProjectParser {
	 public Projet parseXML(String filePath) throws Exception {
	        // Créer un objet XMLNode pour analyser le fichier XML
	        XMLNode xmlNode = new XMLNode(filePath);

	        // Extraire les informations du projet à partir de l'élément racine
	        return extractProject(xmlNode);
	    }

	    private Projet extractProject(XMLNode xmlNode) {
	        Projet project = new Projet();
	        
	        // Extraire le nom du projet
	        XMLNode projectNode = xmlNode.child("Project");
	        if (projectNode != null) {
	            project.setName(projectNode.attribute("name"));
	        }

	        // Extraire les packages du projet
	        XMLNode[] packageNodes = xmlNode.children();
	        for (XMLNode packageNode : packageNodes) {
	            if (packageNode.getName().equals("Package")) {
	                Package pkg = extractPackage(packageNode);
	                project.getPackages().add(pkg);
	            }
	        }

	        return project;
	    }

	    private Package extractPackage(XMLNode packageNode) {
	        Package pkg = new Package();
	        pkg.setName(packageNode.attribute("name"));

	        // Extraire les classes du package
	        XMLNode[] classNodes = packageNode.children();
	        for (XMLNode classNode : classNodes) {
	            if (classNode.getName().equals("Class")) {
	                Classe cls = extractClass(classNode);
	                pkg.getClasses().add(cls);
	            }
	        }

	        return pkg;
	    }

	    private Classe extractClass(XMLNode classNode) {
	        Classe cls = new Classe();
	        cls.setName(classNode.attribute("name"));

	        // Extraire les relations d'agrégation
	        XMLNode[] relationNodes = classNode.children();
	        for (XMLNode relationNode : relationNodes) {
	            if (relationNode.getName().equals("Relation")) {
	                /*Field relation = extractRelation(relationNode);
	                cls.getRelationsAggregation().add(relation);*/
	            }
	        }

	        return cls;
	    }
	 /*   private Field extractRelation(XMLNode relationNode) {
	        // Créer une instance de la classe Field (une classe personnalisée pour représenter une relation)
	        Field relation = new Field(); 

	        // Extraire l'attribut "target" du nœud XML et l'assigner à la relation
	        relation.setName(relationNode.attribute("target")); 
	        
	        // Si tu souhaites ajouter d'autres informations liées à la relation, fais-le ici
	        
	        return relation;
	    }*/


	 /*   private Field extractRelation(XMLNode relationNode) {
	        Field relation = new Field();
	        relation.setName(relationNode.attribute("target"));
	        return relation;
	    }*/

}
