package view;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;
import entities.ProjectInterface;
import usecase.createapplication.CreateApplicationController;
import usecase.createapplication.CreateApplicationInteractor;
import usecase.createapplication.CreateApplicationOutputBoundary;
import usecase.createapplication.CreateApplicationPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DisplayCreateApplicationView extends JFrame implements ActionListener, PropertyChangeListener {
    private final CreateApplicationController createApplicationController;

    private final JPanel loginPanel = new JPanel();
    private final JLabel infoLabel = new JLabel("Info: ");
    private final JTextField infoField = new JTextField();
    private final String applicationText = "Application: ";
    private final JLabel applicationLabel = new JLabel(applicationText);
    private final JButton applicationButton = new JButton("Upload file");
    private final JButton submitButton = new JButton("Submit");

    public DisplayCreateApplicationView(int loginUserId, int projectId, CreateApplicationController createApplicationController) {

        this.createApplicationController = createApplicationController;

        setTitle("Create Application");
        setSize(400, 200);

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 2));

        panel.add(infoLabel);
        panel.add(infoField);
        panel.add(applicationLabel);

        applicationButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                applicationLabel.setText(applicationText + selectedFile.getAbsolutePath());
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            }
        });
        panel.add(applicationButton);

        submitButton.addActionListener(e -> {
            String infoText = infoLabel.getText();
            try {
                InputStream input = new FileInputStream(applicationLabel.getText().substring(applicationText.length()));
                createApplicationController.createApplication(loginUserId, projectId, infoText, input);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            System.out.println();
        });

        panel.add(new JLabel());
        panel.add(submitButton);
        this.add(panel);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
