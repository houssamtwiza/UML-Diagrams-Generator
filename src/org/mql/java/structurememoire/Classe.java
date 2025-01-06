package org.mql.java.structurememoire;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Classe {
	public Classe() {
		// TODO Auto-generated constructor stub
	}
	
	    private static String name;
	  
	    private static  List<java.lang.reflect.Method> methods; 
	    private static  List<Field> relationsagregation;    // Liste les classses qui ont une relation d'agreagation avec notre class
	    private static List<java.lang.reflect.Field> fields;        // Liste des champs (attributs)
	    private static  Class relationsheritage;  
public static Classe exploreClasses(File f) throws ClassNotFoundException {//a partir dun fichier qui sera !!une classe!! on donne en retour une class Classe qui contient tous les infos necesraire sur cette classe ses methodes ses relation dehreitage...
	Classe c=new Classe();
	c.name=f.getName().replaceAll(".java" + "$", "");;
	/*Class cz=Class.forName(c.name);
	Class<?> superClass = cz.getSuperclass();
	relationsheritage= superClass;
	
	java.lang.reflect.Method[] m=cz.getMethods();
	java.lang.reflect.Field[] fi=cz.getDeclaredFields();
	  for (Field field : fi) {
          if (field.getType().isPrimitive()) {
        	  relationsagregation.add(field);
          }
	methods =Arrays.asList(m);
    fields=Arrays.asList(fi);
    
	
}*/
	  return c;    // Constructeurs, getters et setters
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public static List<java.lang.reflect.Method> getMethods() {
	return methods;
}
public static void setMethods(List<java.lang.reflect.Method> methods) {
	Classe.methods = methods;
}
public static List<Field> getRelationsagregation() {
	return relationsagregation;
}
public static void setRelationsagregation(List<Field> relationsagregation) {
	Classe.relationsagregation = relationsagregation;
}
public static List<java.lang.reflect.Field> getFields() {
	return fields;
}
public static void setFields(List<java.lang.reflect.Field> fields) {
	Classe.fields = fields;
}
public static Class getRelationsheritage() {
	return relationsheritage;
}
public static void setRelationsheritage(Class relationsheritage) {
	Classe.relationsheritage = relationsheritage;
}}
