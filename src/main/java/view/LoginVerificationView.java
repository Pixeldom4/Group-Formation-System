package view;

import view.components.ImagePanel;
import viewmodel.LoginVerificationViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class LoginVerificationView extends JFrame implements PropertyChangeListener {

    private LoginVerificationViewModel viewModel;
    private JLabel hintLabel = new JLabel("Rotate the image till upright", SwingConstants.CENTER);
    private ImagePanel imagePanel;
    private final String imagePath;

    public LoginVerificationView(LoginVerificationViewModel viewModel) {
        // Set the frame properties
        setTitle("Test verification view");
        setSize(450, 500);  // Increased height to accommodate the slider
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.imagePath = viewModel.getVerifyImageLocation();
        viewModel.addPropertyChangeListener(this);

        // Add the label
        add(hintLabel, BorderLayout.NORTH);

        // Create and add the custom panel
        imagePanel = new ImagePanel(imagePath, getWidth() - 50, getWidth() - 50);
        add(imagePanel, BorderLayout.CENTER);

        // Create and add the slider
        JSlider slider = new JSlider(0, 360, 0);  // Minimum 0, maximum 360, initial value 0
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int angle = slider.getValue();
                viewModel.setSliderAngle(angle);
            }
        });
        add(slider, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
        viewModel.setSliderAngle(0);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("imageAngle")) {
            int angle = (int) evt.getNewValue();
            imagePanel.setAngle(angle);
        }
    }
}


