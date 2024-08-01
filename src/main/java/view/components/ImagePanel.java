package view.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    private BufferedImage croppedImage;
    private double angle = 0;
    private final int width;
    private final int height;

    public ImagePanel(String imagePath, int width, int height) {

        this.width = width;
        this.height = height;

        // Load the image
        try {
            image = ImageIO.read(new File(imagePath));
            cropAndScaleImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cropAndScaleImage() {
        int diameter = Math.min(width, height);
        croppedImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = croppedImage.createGraphics();
        g2d.setClip(new Ellipse2D.Double(0, 0, diameter, diameter));
        g2d.drawImage(image, 0, 0, diameter, diameter, this);
        g2d.dispose();
    }

    public void setAngle(double angle) {
        this.angle = angle;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Paint everything white first
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (croppedImage != null) {
            // Calculate the center of the image
            int cx = croppedImage.getWidth() / 2;
            int cy = croppedImage.getHeight() / 2;

            // Create a transform to rotate the image around its center
            AffineTransform transform = new AffineTransform();
            transform.translate((getWidth() - croppedImage.getWidth()) / 2, (getHeight() - croppedImage.getHeight()) / 2);
            transform.rotate(Math.toRadians(angle), cx, cy);

            // Draw the rotated image
            g2d.setClip(new Ellipse2D.Double((getWidth() - croppedImage.getWidth()) / 2, (getHeight() - croppedImage.getHeight()) / 2, croppedImage.getWidth(), croppedImage.getHeight()));
            g2d.drawImage(croppedImage, transform, this);

            // Draw the circle frame
            g2d.setClip(null);  // Clear the clipping region
            int circleX = (getWidth() - croppedImage.getWidth()) / 2;
            int circleY = (getHeight() - croppedImage.getHeight()) / 2;
            g2d.setColor(Color.RED);  // Set the color of the circle
            g2d.drawOval(circleX, circleY, croppedImage.getWidth(), croppedImage.getHeight());
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (image != null) {
            cropAndScaleImage();
        }
    }
}

