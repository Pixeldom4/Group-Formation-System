package view;

import usecase.manageapplications.createapplication.CreateApplicationController;
import config.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

/**
 * A view for creating an application.
 */
@SuppressWarnings("FieldCanBeLocal")
public class DisplayCreateApplicationView extends JFrame implements ActionListener, PropertyChangeListener {
    private final CreateApplicationController createApplicationController;

    private final JPanel loginPanel = new JPanel();
    private final JLabel infoLabel = new JLabel("Info: ");
    final JTextField infoField = new JTextField();
    private final String applicationText = "Application: ";
    final JLabel applicationLabel = new JLabel(applicationText);
    private final JButton applicationButton = new JButton("Upload file");
    final JButton submitButton = new JButton("Submit");

    IHoverVoiceService hoverVoiceService;

    /**
     * Constructs a DisplayCreateApplicationView.
     *
     * @param loginUserId the ID of the logged-in user
     * @param projectId the ID of the project
     * @param createApplicationController the controller for creating applications
     */
    public DisplayCreateApplicationView(int loginUserId, int projectId, CreateApplicationController createApplicationController) {
        this.createApplicationController = createApplicationController;

        setTitle("Create Application");
        setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();

        hoverVoiceService.addHoverVoice(infoField, "Enter info here");

        panel.add(infoLabel);
        panel.add(infoField);
        panel.add(applicationLabel);

        applicationButton.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                applicationLabel.setText(applicationText + selectedFile.getAbsolutePath());
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            }
        });
        hoverVoiceService.addHoverVoice(applicationButton, "Press to upload file");
        panel.add(applicationButton);

        hoverVoiceService.addHoverVoice(submitButton, "Press to submit application");
        submitButton.addActionListener(_ -> {
            String infoText = infoLabel.getText();
            try {
                InputStream input = new FileInputStream(applicationLabel.getText().substring(applicationText.length()));
                createApplicationController.createApplication(loginUserId, projectId, infoText, input);
            } catch (FileNotFoundException ex) {
                // pass in an empty input stream
                createApplicationController.createApplication(loginUserId, projectId, infoText, new ByteArrayInputStream(new byte[0]));
            }

        });

        panel.add(new JLabel());
        panel.add(submitButton);
        this.add(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No implementation needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // No implementation needed
    }
}
