package org.mql.java.structurememoire;

import java.io.File;
import java.util.*;

public class Package {
	private String name;
	private List<Classe> classes;


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
	public static Package exploreFile(File f) throws ClassNotFoundException {
		Package p=new Package();
		p.name=f.getName();
		File ff[] = f.listFiles();
		
		for (File fg:ff) {
			if (fg.isFile() && fg.getName().endsWith(".java")) {
				
			Classe c=Classe.exploreClasses(fg);
			p.classes.add(c);
			}}
		return p;
	}

}
