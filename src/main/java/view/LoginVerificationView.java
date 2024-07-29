package view;

import view.components.ImagePanel;
import viewmodel.LoginVerificationViewModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginVerificationView extends JFrame implements PropertyChangeListener {

    private LoginVerificationViewModel viewModel;
    private JLabel hintLabel = new JLabel("Rotate the image till upright", SwingConstants.CENTER);
    JLabel timeLabel = new JLabel("a", SwingConstants.LEFT);
    JPanel southPanel = new JPanel();
    private ImagePanel imagePanel;
    private final String imagePath;

    public LoginVerificationView(LoginVerificationViewModel viewModel) {
        // Set the frame properties
        setTitle("Verify that you are not a robot");
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


        southPanel.setLayout(new BorderLayout());

        // Add the time label and set it to invisible
        timeLabel.setVisible(false);
        southPanel.add(timeLabel, BorderLayout.CENTER);

        // Create and add the slider
        JSlider slider = new JSlider(0, 360, 0);  // Minimum 0, maximum 360, initial value 0
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    // Slider has stopped changing
                    viewModel.displayThenVerify();
                }
                else {
                    int angle = slider.getValue();
                    viewModel.setSliderAngle(angle);
                    viewModel.updateTimer();
                }
            }
        });
        southPanel.add(slider, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);

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

        if (evt.getPropertyName().equals("displayTime")) {
            long time = (long) evt.getNewValue();
            timeLabel.setText("Time taken: " + time + " ms");
            timeLabel.setOpaque(true);
            timeLabel.setBackground(Color.WHITE);
            timeLabel.setVisible(true);
            timeLabel.revalidate();
            timeLabel.repaint();
            System.out.println(timeLabel.getText());
        }
    }
}


