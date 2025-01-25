package org.mql.java.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.mql.java.exploration.ProjectExploration;
import org.mql.java.parse.ProjectParser;
import org.mql.java.structurememoire.Classe;
import org.mql.java.structurememoire.Projet;
import org.mql.java.structurememoire.Package;

public class applcationUml extends JFrame {

    public applcationUml() throws ClassNotFoundException {
        super("app uml");
        this.setSize(1200, 800);

        JPanel container = new JPanel();
        container.setLayout(new FlowLayout());

        JLabel jl = new JLabel("Entrer votre chemin projet :");

        JTextField jf = new JTextField();
        jf.setPreferredSize(new Dimension(400, 20));

        JButton button = new JButton("Choisir un fichier");
        JButton b = new JButton("Générer diagramme de classe");
        JButton b1 = new JButton("Générer XML");
        JButton b2 = new JButton("Générer XMI");
        JButton b3 = new JButton("parse et afficher sur console");
        JButton b4 = new JButton("generer diagramme de package");

        // Action pour choisir un répertoire
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(applcationUml.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    jf.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        // Action pour générer UML
  /*      b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectPath = jf.getText(); // Récupérer le chemin du champ de texte

                if (projectPath != null && !projectPath.isEmpty()) {
                    // Ajouter le composant UML (dessin)
                    JPanel umlPanel = new dessin(projectPath);
                    container.add(umlPanel, BorderLayout.CENTER);

                    // Rafraîchir l'interface
                    container.revalidate();
                    container.repaint();
                }
            }
        });*/
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectPath = jf.getText(); // Récupérer le chemin du champ de texte

                if (projectPath != null && !projectPath.isEmpty()) {
                    // Afficher dans la console le chemin saisi
                    System.out.println("Chemin du projet sélectionné : " + projectPath);

                    // Supprimer les anciens diagrammes
                    for (java.awt.Component comp : container.getComponents()) {
                        if (comp instanceof dessin) {
                            container.remove(comp);
                        }
                    }

                    // Créer et ajouter le nouveau diagramme
                    JPanel umlPanel = new dessin(projectPath);
                    container.add(umlPanel, BorderLayout.CENTER);

                    // Rafraîchir l'interface
                    container.revalidate();
                    container.repaint();
                } else {
                    System.out.println("Chemin invalide ou vide.");
                }
            }
        });

        
        
        
        /*b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 String projectPath = jf.getText();
            	  if (projectPath != null && !projectPath.isEmpty()) {
            		  File directory = new File(projectPath);
            		   
            		 try {
						Projet p= ProjectExploration.getPackageList(projectPath) ;
						 String xmlFilePath = projectPath + File.separator + p.getName() + ".xml";
	            	        File xmlFile = new File(xmlFilePath);
	            	        xmlFile.createNewFile();
	            	       FileWriter writer = new FileWriter(xmlFile);
	            	            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	            	            writeProject(writer, p);
	            	        

	            	        System.out.println("XML généré avec succès dans : " + xmlFilePath);
	            	    
					} catch (Exception e1) {
					
						e1.printStackTrace();
					}
            	  }
            	return ;
            }

            private void writeProject(FileWriter writer, Projet project) throws IOException {
                writer.write("<Project name=\"" + escape(project.getName()  ) + "\">\n");
                for (org.mql.java.structurememoire.Package pkg : project.getPackages()  ) {
                    writePackage(writer, pkg);
                }
                writer.write("</Project>\n");
            }
            private void writePackage(FileWriter writer, org.mql.java.structurememoire.Package pkg) throws IOException {
                writer.write("  <Package name=\"" + escape(pkg.getName() ) + "\">\n");
                for (Classe cls : pkg.getClasses() ) {
                    writeClassOrInterface(writer, cls);
                }
                writer.write("  </Package>\n");
            }

            private void writeClassOrInterface(FileWriter writer, Classe cls) throws IOException {
                writer.write("    <Class name=\"" + escape(cls.getName() ) + "\" type=\""  + "\">\n");
                for (Field rel : cls.getRelationsAggregation() ) {
                    writeRelation(writer, rel);
                }
                writer.write("    </Class>\n");
            }

            private void writeRelation(FileWriter writer, Field rel) throws IOException {
                writer.write("      <Relation type=\"" +   "\" />\n");
            }

            private String escape(String value) {
                if (value == null) return "";
                return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                            .replace("\"", "&quot;").replace("'", "&apos;");
            }
        });*/
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectPath = jf.getText();
                if (projectPath != null && !projectPath.isEmpty()) {
                    try {
                        // Exploration du projet
                        Projet p = ProjectExploration.getPackageList(projectPath);

                        // Chemin complet du fichier XML
                        String xmlFilePath = projectPath + File.separator + p.getName() + ".xml";
                        File xmlFile = new File(xmlFilePath);

                        // Créer le fichier XML
                        if (!xmlFile.exists()) {
                            xmlFile.createNewFile();
                        }

                        // Utilisation d'un try-with-resources pour garantir la fermeture
                        try (FileWriter writer = new FileWriter(xmlFile)) {
                            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                            writeProject(writer, p);
                        }

                        System.out.println("XML généré avec succès dans : " + xmlFilePath);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

            private void writeProject(FileWriter writer, Projet project) throws IOException {
                writer.write("<Project name=\"" + escape(project.getName()) + "\">\n");
                for (org.mql.java.structurememoire.Package pkg : project.getPackages()) {
                    writePackage(writer, pkg);
                }
                writer.write("</Project>\n");
            }

            private void writePackage(FileWriter writer, org.mql.java.structurememoire.Package pkg) throws IOException {
                writer.write("  <Package name=\"" + escape(pkg.getName()) + "\">\n");
                for (Classe cls : pkg.getClasses()) {
                    writeClassOrInterface(writer, cls);
                }
                writer.write("  </Package>\n");
            }

            private void writeClassOrInterface(FileWriter writer, Classe cls) throws IOException {
                writer.write("    <Class name=\"" + escape(cls.getName()) + "\" type=\"class\">\n");
                for (Field rel : cls.getRelationsAggregation()) {
                    writeRelation(writer, rel);
                }
                writer.write("    </Class>\n");
            }

            private void writeRelation(FileWriter writer, Field rel) throws IOException {
                writer.write("      <Relation type=\"aggregation\" target=\"" + escape(rel.getName()) + "\" />\n");
            }

            private String escape(String value) {
                if (value == null) return "";
                return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                            .replace("\"", "&quot;").replace("'", "&apos;");
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectPath = jf.getText();
                if (projectPath != null && !projectPath.isEmpty()) {
                    try {
                        // Exploration du projet
                        Projet p = ProjectExploration.getPackageList(projectPath);

                        // Chemin complet du fichier XMI
                        String xmiFilePath = projectPath + File.separator + p.getName() + ".xmi";
                        File xmiFile = new File(xmiFilePath);

                        // Créer le fichier XMI
                        if (!xmiFile.exists()) {
                            xmiFile.createNewFile();
                        }

                        // Utilisation d'un try-with-resources pour garantir la fermeture
                        try (FileWriter writer = new FileWriter(xmiFile)) {
                            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                            writer.write("<XMI xmlns:xmi=\"http://www.omg.org/spec/XMI/2.1\" xmlns:uml=\"http://www.omg.org/spec/UML/2.1\">\n");
                            writeProject(writer, p);
                            writer.write("</XMI>\n");
                        }

                        System.out.println("XMI généré avec succès dans : " + xmiFilePath);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

            private void writeProject(FileWriter writer, Projet project) throws IOException {
                writer.write("  <uml:Model name=\"" + escape(project.getName()) + "\" xmi:id=\"" + generateId() + "\">\n");
                for (org.mql.java.structurememoire.Package pkg : project.getPackages()) {
                    writePackage(writer, pkg);
                }
                writer.write("  </uml:Model>\n");
            }

            private void writePackage(FileWriter writer, org.mql.java.structurememoire.Package pkg) throws IOException {
                writer.write("    <uml:Package name=\"" + escape(pkg.getName()) + "\" xmi:id=\"" + generateId() + "\">\n");
                for (Classe cls : pkg.getClasses()) {
                    writeClassOrInterface(writer, cls);
                }
                writer.write("    </uml:Package>\n");
            }

            private void writeClassOrInterface(FileWriter writer, Classe cls) throws IOException {
                writer.write("      <uml:Class name=\"" + escape(cls.getName()) + "\" xmi:id=\"" + generateId() + "\">\n");
                for (Field rel : cls.getRelationsAggregation()) {
                    writeRelation(writer, rel);
                }
                writer.write("      </uml:Class>\n");
            }

            private void writeRelation(FileWriter writer, Field rel) throws IOException {
                writer.write("        <uml:Association xmi:id=\"" + generateId() + "\">\n");
                writer.write("          <uml:AssociationEnd xmi:id=\"" + generateId() + "\" name=\"" + escape(rel.getName()) + "\"/>\n");
                writer.write("        </uml:Association>\n");
            }

            private String escape(String value) {
                if (value == null) return "";
                return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                            .replace("\"", "&quot;").replace("'", "&apos;");
            }

            private String generateId() {
                return "id" + System.nanoTime(); // Génère un identifiant unique pour chaque élément
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectPath = jf.getText();
                ProjectParser parser=new ProjectParser();
                try {
					Projet p=parser.parseXML(projectPath);
					  

			          for (Package pp:p.getPackages()) {
			        	  for (Classe clazz : pp.getClasses() ) {
				                clazz.display();
				            }
			          }
			            
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                
                
            }});
     /*   b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectPath = jf.getText();
                try {
					Projet p= ProjectExploration.getPackageList(projectPath);
				container.add(new dessinpaquetage ("C:\\Users\\houss\\eclipse-workspace\\UML-Diagrams-Generator\\packageDiagram.png"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            
            }});*/
                
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectPath = jf.getText();
                try {
                    Projet p = ProjectExploration.getPackageList(projectPath);

                    // Charger l'image générée
                    dessinpaquetage umlPanel = new dessinpaquetage("C:\\Users\\houss\\eclipse-workspace\\UML-Diagrams-Generator\\packageDiagram.png");
                    
                    // Ajouter un JScrollPane pour permettre le défilement
                    JScrollPane scrollPane = new JScrollPane(umlPanel);
                    scrollPane.setPreferredSize(new Dimension(1000, 800)); // Taille par défaut
                    for (java.awt.Component comp : container.getComponents()) {
                        if (comp instanceof dessin) {
                            container.remove(comp);
                        }
                    }
                    
                    // Ajouter au conteneur principal
                    container.add(scrollPane, BorderLayout.CENTER);

                    container.revalidate();
                    container.repaint();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

                
        container.add(jl);
        container.add(jf);
        container.add(button);
        container.add(b);
        container.add(b1);
        container.add(b2);
        container.add(b3);
        container.add(b4);


        setContentPane(container);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        new applcationUml();
    }
}
