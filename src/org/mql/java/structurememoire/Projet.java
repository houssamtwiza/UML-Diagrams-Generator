package org.mql.java.structurememoire;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// classe Java qui sert de structure de données en mémoire pour organiser les informations extraites
public class Projet {
	   private static final String JAVA_EXTENSION = ".java";
	   private static final Pattern IMPORT_PATTERN = Pattern.compile("import\\s+([a-zA-Z0-9_.]+);");
	   private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([a-zA-Z0-9_.]+);");
	   // Structure pour stocker les relations entre packages
   
	   private static final Map<String, Set<String>> packageDependencies = new HashMap<>();
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
public void explorerecursivepackgesinprojet(File f,String s) throws ClassNotFoundException, MalformedURLException {
	if (f.isFile() || packages==null || f==null ) {
	}
	else{
		Package p= new Package();
		Package p1=	p.explorePackages(f,s);//cette methode statique va explorer le fichier et va en quelque sorte le transformer en package equivalent
	
		packages.add(p1);
		File fr[]=f.listFiles();
		for(File ff:fr){
		
			explorerecursivepackgesinprojet(ff,s);
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
// Parcours des fichiers source et analyse des packages et des imports avec Streams
public static void analyzeSourceCode(Path folder) throws IOException {
    // Utilisation de Files.walk pour lister tous les fichiers dans le dossier
    // On filtre uniquement les fichiers Java avec ".java" et on les traite en Streams
    Files.walk(folder)
            .filter(path -> path.toString().endsWith(JAVA_EXTENSION))  // Filtre les fichiers ".java"
            .map(Projet::processJavaFile)  // On applique le traitement de chaque fichier
            .forEach(result -> result.forEach((key, value) -> packageDependencies.merge(key, value, (existing, newValues) -> {
                existing.addAll(newValues);
                return existing;
            })));
}
// Analyse un fichier Java pour extraire son package et ses imports
private static Map<String, Set<String>> processJavaFile(Path file) {
    try (BufferedReader reader = Files.newBufferedReader(file)) {
        String packageName = null;
        Set<String> imports = new HashSet<>();
        String line;

        // Parcours du fichier ligne par ligne en utilisant Streams
        while ((line = reader.readLine()) != null) {
            Matcher packageMatcher = PACKAGE_PATTERN.matcher(line);
            if (packageMatcher.find()) {
                packageName = packageMatcher.group(1);
            }

            Matcher importMatcher = IMPORT_PATTERN.matcher(line);
            if (importMatcher.find()) {
                String importedPackage = importMatcher.group(1);
                // Ne garder que le nom du package (sans la classe)
                int lastDot = importedPackage.lastIndexOf('.');
                if (lastDot > 0) {
                    imports.add(importedPackage.substring(0, lastDot));
                }
            }
        }

        // Retourner les dépendances trouvées dans un Map
        if (packageName != null && !imports.isEmpty()) {
            Map<String, Set<String>> result = new HashMap<>();
            result.put(packageName, imports);
            return result;
        }

    } catch (IOException e) {
        System.err.println("Error reading file: " + file);
    }
    return Collections.emptyMap();  // Retourne une Map vide si aucune donnée n'est trouvée
}
public static void generateDotFile(String outputFileName) {
    try (PrintWriter writer = new PrintWriter(outputFileName)) {
        // En-têtes pour le fichier DOT
        writer.println("digraph PackageDiagram {");
        writer.println("    rankdir=LR;");  // Orientation gauche-droite
    
        writer.println("    node [shape=box];");
    

        // Utilisation de Streams pour écrire les relations entre packages
        packageDependencies.forEach((fromPackage, toPackages) -> 
            toPackages.stream()  // Transforme le Set en Stream
                      .forEach(toPackage -> 
                          writer.printf("    \"%s\" -> \"%s\";%n", fromPackage, toPackage)
                      )
        );

        writer.println("}");
    } catch (IOException e) {
        System.err.println("Error writing DOT file: " + outputFileName);
    }
}
public static void generateImageFromDot(String dotFileName, String imageFileName) {
    try {
        // Utilisation de ProcessBuilder pour appeler Graphviz et générer l'image
        ProcessBuilder processBuilder = new ProcessBuilder("C:\\Users\\houss\\Downloads\\windows_10_cmake_Release_Graphviz-12.2.1-win64\\Graphviz-12.2.1-win64\\bin\\dot", "-Tpng", dotFileName, "-o", imageFileName);
        Process process = processBuilder.start();  // Démarre le processus
        int exitCode = process.waitFor();  // Attendre la fin du processus
        if (exitCode == 0) {
            System.out.println("Image generated successfully: " + imageFileName);
        } else {
            System.err.println("Error generating image. Graphviz may not be installed.");
        }
    } catch (IOException | InterruptedException e) {
        System.err.println("Error running Graphviz: " + e.getMessage());
    }
}
}
