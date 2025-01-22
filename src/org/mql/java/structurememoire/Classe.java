package org.mql.java.structurememoire;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Classe {
    private String name;
    private List<java.lang.reflect.Method> methods = new ArrayList<>();
    private List<Field> relationsAggregation = new ArrayList<>(); // Agrégation
    private List<java.lang.reflect.Field> fields = new ArrayList<>();
    private Class relationsHeritage; // Héritage

    public Classe() {
    }

    // Getters et setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<java.lang.reflect.Method> getMethods() {
        return methods;
    }

    public void setMethods(List<java.lang.reflect.Method> methods) {
        this.methods = methods;
    }

    public List<Field> getRelationsAggregation() {
        return relationsAggregation;
    }

    public void setRelationsAggregation(List<Field> relationsAggregation) {
        this.relationsAggregation = relationsAggregation;
    }

    public List<java.lang.reflect.Field> getFields() {
        return fields;
    }

    public void setFields(List<java.lang.reflect.Field> fields) {
        this.fields = fields;
    }

    public Class<?> getRelationsHeritage() {
        return relationsHeritage;
    }

    public void setRelationsHeritage(Class<?> relationsHeritage) {
        this.relationsHeritage = relationsHeritage;
    }

    // Méthode d'exploration d'une classe
    public  Classe exploreClasses(File f, String packageName) throws ClassNotFoundException, MalformedURLException {
        if (f == null || !f.isFile() || !f.getName().endsWith(".class")) {
            return null;
        }

        Classe clazz = new Classe();
        clazz.setName(f.getName().replace(".class", ""));
      //  System.out.println(f.getName().replace(".class", ""));
     /*   String classPath = System.getProperty("java.class.path");
        File classPathDir = new File(classPath);

        // Calculez le chemin relatif pour construire le nom de classe complet
        String relativePath = f.getAbsolutePath()
                                .replace(classPathDir.getAbsolutePath() + File.separator, "")
                                .replace(File.separator, ".")
                                .replace(".java", "");

        System.out.println("Full class name: " + relativePath);*/

        // Charger la classe
        
       File otherProjectDir = new File("C:\\Users\\houss\\eclipse-workspace\\p02_generics/bin");  // Remplacez par le chemin correct
        
        // Créer une URL à partir du chemin du dossier
        URL[] classPath = { otherProjectDir.toURI().toURL() };
        
        // Créer un chargeur de classes avec cette URL
        URLClassLoader classLoader = new URLClassLoader(classPath);
        
        // Nom qualifié de la classe à charger (avec le package complet)
        String className = packageName+  "."+clazz.getName();  // Remplacez par la classe exacte

        // Charger la classe à partir de son nom
        Class<?> cz = classLoader.loadClass(className);
       
        
      //  Class<?> cz = Class.forName( packageName+"."+f.getName().replace(".java", ""));
    	
    	Class<?> superClass = cz.getSuperclass();
    	if ( ! superClass.getName().equals( "java.lang.Object")  ) {
    		clazz.setRelationsHeritage(superClass);
    	}
    
    	//this.relationsHeritage= superClass;
    	
    	java.lang.reflect.Method[] m=cz.getDeclaredMethods();
    	java.lang.reflect.Field[] fi=cz.getDeclaredFields();
    	  for (Field field : fi) {
    		//   System.out.println(field.getName());
              if (!field.getType().isPrimitive() && !(field.getType() ==String.class ) ) {
            	  relationsAggregation.add(field);
            	  clazz.setRelationsAggregation(relationsAggregation);
              }
           /*  for (java.lang.reflect.Field M : fi) {
       		   System.out.println(M.getName());}
    	
         for (java.lang.reflect.Method mm : methods) {
  		// System.out.println(mm.getName());
  		  }*/
       // fields=Arrays.asList(fi);
       // methods =Arrays.asList(m);
              clazz.setMethods(Arrays.asList(m));
              clazz.setFields(Arrays.asList(fi));
        
    	
    }
        // Vous pouvez ajouter plus de logique pour extraire d'autres détails si nécessaire
        return clazz;
        
    }

    public void display() {
        // Afficher le nom de la classe
        System.out.println("Classe: " + getName());

        // Afficher les informations sur l'héritage
        if (relationsHeritage != null) {
            System.out.println("Héritage: " + relationsHeritage.getName());
        } else {
            System.out.println("Aucun héritage");
        }

        // Afficher les relations d'agrégation
        if (!relationsAggregation.isEmpty()) {
            System.out.println("Relations d'agrégation:");
            for (Field field : relationsAggregation) {
                System.out.println("  - " + field.getName());
            }
        } else {
            System.out.println("Pas de relations d'agrégation.");
        }

        // Afficher les champs de la classe
        if (!fields.isEmpty()) {
            System.out.println("Champs de la classe:");
            for (java.lang.reflect.Field field : fields) {
                System.out.println("  - " + field.getName() + " : " + field.getType().getName());
            }
        } else {
            System.out.println("Pas de champs définis.");
        }

        // Afficher les méthodes de la classe
        if (!methods.isEmpty()) {
            System.out.println("Méthodes de la classe:");
            for (java.lang.reflect.Method method : methods) {
                System.out.println("  - " + method.getName() + " : " + method.getReturnType().getName());
            }
        } else {
            System.out.println("Pas de méthodes définies.");
        }
    }

}
