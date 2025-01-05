package org.mql.java.structurememoire;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.List;

public class Classe {
	public Classe() {
		// TODO Auto-generated constructor stub
	}
	
	    private String name;
	    private List<String> annotations;  // Liste des annotations de la classe
	    private List<Method> methods;      // Liste des méthodes
	    private List<Field> fields;        // Liste des champs (attributs)
public static Classe exploreClasses(File f) throws ClassNotFoundException {
	Classe c=new Classe();
	Class cz=Class.forName(f.getName());
	java.lang.reflect.Method[] m=cz.getMethods();
	java.lang.reflect.Field[] fi=cz.getDeclaredFields();
	Annotation[] annotations = cz.getAnnotations();

	return c;
}
	    // Constructeurs, getters et setters
}
