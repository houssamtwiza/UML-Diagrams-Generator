package org.mql.java.structurememoire;

import java.io.File;
import java.net.MalformedURLException;
import java.util.*;

public class Package {
	private String name;
	private List<Classe> classes=new ArrayList<Classe>();


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
	public  Package explorePackages(File f) throws ClassNotFoundException, MalformedURLException {
		Package p=new Package();
		p.name=f.getName();
		File ff[] = f.listFiles();
		
		for (File fg:ff) {
			if (fg.isFile() && fg.getName().endsWith(".class")) {
				

			Classe c=new Classe();
			Classe c1=		c.exploreClasses(fg,p.name);
			p.classes.add(c1);
			}}
		return p;
	}

}
