package org.mql.java.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.mql.java.exploration.ProjectExploration;
import org.mql.java.structurememoire.Classe;
import org.mql.java.structurememoire.Package;
import org.mql.java.structurememoire.Projet;

public class dessin extends JPanel {

    private String projectPath;
    private Map<Classe, Point> classPositions = new HashMap<>();

    public dessin(String projectPath) {
        this.projectPath = projectPath;
        this.setPreferredSize(new Dimension(800, 800));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        try {
            Projet projet = ProjectExploration.getPackageList(projectPath);

            // Positionnement initial
            int x = 50;
            int y = 50;
            int classWidth = 200;
            int classHeight = 150;
            int horizontalSpacing = 250;
            int verticalSpacing = 200;

            for (Package pkg : projet.getPackages()) {
                for (Classe clazz : pkg.getClasses()) {
                    // Sauvegarder la position de chaque classe
                    classPositions.put(clazz, new Point(x, y));

                    // Dessiner la classe
                    drawClass(g2d, clazz, x, y);

                    // Mise à jour de la position pour la prochaine classe
                    x += horizontalSpacing;
                    if (x + classWidth > getWidth()) {
                        x = 50;
                        y += verticalSpacing;
                    }
                }
            }

            // Dessiner les relations après les classes
            for (Package pkg : projet.getPackages()) {
                for (Classe clazz : pkg.getClasses()) {
                    drawRelations(g2d, clazz);
                }
            }

        } catch (ClassNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void drawClass(Graphics2D g2d, Classe clazz, int x, int y) {
        int width = 200;
        int height = 100 + clazz.getFields().size() * 20 + clazz.getMethods().size() * 20;

        // Dessiner le rectangle de la classe
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);

        // Dessiner le nom de la classe
        g2d.drawLine(x, y + 30, x + width, y + 30);
        g2d.drawString(clazz.getName(), x + 10, y + 20);

        // Dessiner les attributs
        int currentY = y + 40;
        for (Field field : clazz.getFields()) {
            g2d.drawString(field.getName(), x + 10, currentY);
            currentY += 20;
        }

        // Dessiner les méthodes
        g2d.drawLine(x, currentY - 10, x + width, currentY - 10);
        for (Method method : clazz.getMethods()) {
            g2d.drawString(method.getName(), x + 10, currentY);
            currentY += 20;
        }
    }

    private void drawRelations(Graphics2D g2d, Classe clazz) {
        Point start = classPositions.get(clazz);
        if (start == null) return;

        // Dessiner la relation d'héritage
        if (clazz.getRelationsHeritage() != null) {
            Classe superClass = new Classe(); // Simulez la récupération de la classe par le nom
            Point end = classPositions.get(superClass);
            if (end != null) {
                g2d.setColor(Color.BLUE);
                drawArrow(g2d, start.x + 100, start.y, end.x + 100, end.y + 150);
            }
        }

        // Dessiner les relations d'agrégation
        for (Field field : clazz.getRelationsAggregation()) {
            Classe relatedClass = new Classe(); // Simulez la récupération de la classe par le type du champ
            Point end = classPositions.get(relatedClass);
            if (end != null) {
                g2d.setColor(Color.GREEN);
                g2d.drawLine(start.x + 200, start.y + 50, end.x, end.y + 50);
            }
        }
    }

    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.drawLine(x1, y1, x2, y2);

        // Dessiner la tête de flèche
        int arrowSize = 10;
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int xArrow1 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int yArrow1 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
        int xArrow2 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int yArrow2 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));
        g2d.drawLine(x2, y2, xArrow1, yArrow1);
        g2d.drawLine(x2, y2, xArrow2, yArrow2);
    }
}
