package org.mql.java.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        JButton b = new JButton("Générer UML");

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
        b.addActionListener(new ActionListener() {
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
        });

        container.add(jl);
        container.add(jf);
        container.add(button);
        container.add(b);

        setContentPane(container);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        new applcationUml();
    }
}
