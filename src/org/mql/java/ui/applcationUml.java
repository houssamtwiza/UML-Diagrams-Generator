package org.mql.java.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.mql.java.exploration.ProjectExploration;
import org.mql.java.structurememoire.Classe;
import org.mql.java.structurememoire.Package;
import org.mql.java.structurememoire.Projet;





public class applcationUml extends JFrame{
public applcationUml (String projectpath) throws ClassNotFoundException {
	super("app uml");
/*	this.setSize(1200, 800);
	
	 JPanel container = new JPanel();
	 container.setLayout(new FlowLayout());
	 JLabel jl=new  JLabel("Entrer votre chemin projet :");
	
	 JTextField jf=new JTextField();
	jf.setPreferredSize(new Dimension(400,20));
	/* JTextField jf1=new JTextField();
	jf1.setPreferredSize(new Dimension(1100,700));
	 JButton b=new JButton("generer UML");

	 container.add(jl);
	 container.add(jf);
	 container.add(b);*/
	// container.add(jf1);
	add(new dessin(projectpath),BorderLayout.CENTER);
	//setContentPane( container);
	 setSize(800, 600);
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	setLocationRelativeTo(null);
	setVisible(true);
}

public static void main(String[] args) throws ClassNotFoundException {
	new applcationUml ("C:\\Users\\houss\\eclipse-workspace\\p02_generics");
}

}
