package view;

import usecase.createuser.CreateUserController;
import view.components.NumericTextField;
import view.services.hovervoice.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import view.services.playvoice.PlayVoiceServiceConfig;
import viewmodel.CreateUserPanelViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

/**
 * Panel for creating a new user.
 */
public class CreateUserPanel extends JPanel implements ActionListener, PropertyChangeListener {
    private final CreateUserPanelViewModel createUserPanelViewModel;
    private final CreateUserController createUserController;

    private final JPanel createUserInfo = new JPanel();
    private final JLabel firstNameLabel = new JLabel("First Name: ");
    private final JLabel lastNameLabel = new JLabel("Last Name: ");
    private final JLabel emailLabel = new JLabel("Email: ");
    private final JLabel passwordLabel = new JLabel("Password: ");
    private final JLabel compensationLabel = new JLabel("Desired compensation: ");
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final NumericTextField compensationField = new NumericTextField();
    private final JButton createUserButton = new JButton("Create User");

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

    /**
     * Constructs a CreateUserPanel.
     *
     * @param createUserPanelViewModel the view model for the create user panel
     * @param createUserController the controller for creating users
     */
    public CreateUserPanel(CreateUserPanelViewModel createUserPanelViewModel, CreateUserController createUserController) {
        this.createUserPanelViewModel = createUserPanelViewModel;
        createUserPanelViewModel.addPropertyChangeListener(this);
        this.createUserController = createUserController;
        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        this.playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

        hoverVoiceService.addHoverVoice(firstNameField, "Enter first name here");
        hoverVoiceService.addHoverVoice(lastNameField, "Enter last name here");
        hoverVoiceService.addHoverVoice(emailField, "Enter email here");
        hoverVoiceService.addHoverVoice(passwordField, "Enter password here");
        hoverVoiceService.addHoverVoice(compensationField, "Enter desired compensation here");

        createUserInfo.setLayout(new GridLayout(0,2));
        createUserInfo.add(firstNameLabel);
        createUserInfo.add(firstNameField);
        createUserInfo.add(lastNameLabel);
        createUserInfo.add(lastNameField);
        createUserInfo.add(emailLabel);
        createUserInfo.add(emailField);
        createUserInfo.add(passwordLabel);
        createUserInfo.add(passwordField);
        createUserInfo.add(compensationLabel);
        createUserInfo.add(compensationField);

        createUserButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            double compensation = Double.parseDouble(compensationField.getText());
            createUserController.createUser(firstName, lastName, email, compensation, new HashSet<>(), password);
        });

        hoverVoiceService.addHoverVoice(createUserButton, "Press to create user");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(createUserInfo);
        this.add(createUserButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No implementation needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("success")) {
            boolean success = (boolean) evt.getNewValue();
            if (success) {
                String message = createUserPanelViewModel.getSuccessMessage();
                firstNameField.setText("");
                lastNameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                compensationField.clear();
                playVoiceService.playVoice(message);
                JOptionPane.showMessageDialog(null, message);
            }
            else {
                String message = createUserPanelViewModel.getErrorMessage();
                playVoiceService.playVoice(message);
                JOptionPane.showMessageDialog(null, message);
            }
        }
    }
}
