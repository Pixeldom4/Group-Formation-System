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
    private final JPanel loginPanel = new JPanel();
    private final JLabel infoLabel = new JLabel("Info: ");
    private final JTextField infoField = new JTextField();
    private final String applicationText = "Application: ";
    private final JLabel applicationLabel = new JLabel(applicationText);
    private final JButton applicationButton = new JButton("Upload file");
    private final JButton submitButton = new JButton("Submit");

    public DisplayCreateApplicationView(int projectId) {
//        this.loginUserController = loginUserController;
//        this.loginPanelViewModel = loginPanelViewModel;
//        loginPanelViewModel.addPropertyChangeListener(this);
//        this.viewManagerModel = viewManagerModel;

        IApplicationRepository applicationRepository = DataAccessConfig.getApplicationRepository();
        ILoginUserDetails userDetails = DataAccessConfig.getLoginUserDetails();
        CreateApplicationPresenter applicationPresenter = new CreateApplicationPresenter(this);
        CreateApplicationInteractor createApplicationInteractor = new CreateApplicationInteractor(applicationRepository, applicationPresenter);
        CreateApplicationController createApplicationController = new CreateApplicationController(createApplicationInteractor);

        setTitle("Create Application");
        setSize(400, 200);

        JPanel panel = new JPanel();

//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
//            loginUserController.loginUser(emailField.getText(), new String(passwordField.getPassword()));
        });
        panel.add(applicationButton);

        submitButton.addActionListener(e -> {
            int userId = userDetails.getUserId();
            String infoText = infoLabel.getText();
            try {
                InputStream input = new FileInputStream(applicationLabel.getText().substring(applicationText.length()));
                createApplicationController.createApplication(userId, projectId, infoText, input);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            System.out.println();
//            loginUserController.loginUser(emailField.getText(), new String(passwordField.getPassword()));
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
//        if(evt.getPropertyName().equals("displayDetailProject")){
//            ProjectInterface project = (ProjectInterface) evt.getNewValue();
//            projectTitleField.setText(project.getProjectTitle());
//        }
    }
}
