package org.mql.java.ui;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class dessinpaquetage extends JPanel {
    private BufferedImage image;

    public dessinpaquetage (String imagePath) {
        try {
            // Charger l'image depuis le chemin
            image = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Calculer les dimensions pour maintenir le ratio de l'image
            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            // Maintenir les proportions
            double imgRatio = (double) imgWidth / imgHeight;
            double panelRatio = (double) panelWidth / panelHeight;

            int drawWidth, drawHeight;
            if (imgRatio > panelRatio) {
                drawWidth = panelWidth;
                drawHeight = (int) (panelWidth / imgRatio);
            } else {
                drawHeight = panelHeight;
                drawWidth = (int) (panelHeight * imgRatio);
            }

            // Dessiner l'image centrée
            int x = (panelWidth - drawWidth) / 2;
            int y = (panelHeight - drawHeight) / 2;
            g.drawImage(image, x, y, drawWidth, drawHeight, null);
        }
    }


  
}
