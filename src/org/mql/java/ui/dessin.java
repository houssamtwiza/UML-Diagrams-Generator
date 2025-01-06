package org.mql.java.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JComponent;

import org.mql.java.exploration.ProjectExploration;
import org.mql.java.structurememoire.Classe;
import org.mql.java.structurememoire.Package;
import org.mql.java.structurememoire.Projet;

public class dessin extends JComponent{

	/**
	 * 
	 */
private String  projectpath;
	public dessin( String p) {
		this.projectpath=p;
	}
	
	private static final long serialVersionUID = 1L;
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Projet p;
		try {
			p = ProjectExploration.getPackageList(projectpath);
			 for(Package pp: p.getPackages()) {
				 for(Classe cc:pp.getClasses()) {
				drawClass(g2d,cc,300,50);
			 }}}
		 catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void drawClass(Graphics2D g2d,Classe c,int x,int y) {
		 int width = 200; // Largeur du rectangle
	     int height = 30 + c.getFields().size()*20+c.getMethods().size()*20; // Hauteur calculée
	     int currentY = y;// est une variable utilisée pour suivre la position verticale actuelle dans le rectangle représentant une classe. Cela permet de dessiner chaque élément (nom, attributs, méthodes) l'un en dessous de l'autre à la bonne position.
	     // Dessiner le contour du rectangle principal
	     g2d.setColor(Color.BLACK);
	     g2d.drawRect(x, y, width, height);

	     // Dessiner le nom de la classe
	    
	     g2d.drawString(c.getName(), x + 10, currentY + 20);
	     currentY += 30;

	     // Dessiner les attributs
	     g2d.drawLine(x, currentY, x + width, currentY); // Ligne de séparation
	     
	     for (Field attribute : c.getFields()) {
	         g2d.drawString(attribute.getName(), x + 10, currentY + 20);
	         currentY += 20;
	         // Dessiner les méthodes
	         g2d.drawLine(x, currentY, x + width, currentY); // Ligne de séparation
	         for (Method method : c.getMethods()) {
	             g2d.drawString(method.getName(), x + 10, currentY + 20);
	             currentY += 20;
	         }
	     
	}
     
}
	
}


