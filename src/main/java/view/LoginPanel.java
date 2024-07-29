package view;

import usecase.createverification.CreateVerificationController;
import usecase.createverification.CreateVerificationViewModel;
import usecase.loginuser.LoginUserController;
import viewmodel.LoginPanelViewModel;
import viewmodel.LoginVerificationViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A panel for logging in the user.
 */
public class LoginPanel extends JPanel implements ActionListener, PropertyChangeListener {
    private final LoginPanelViewModel loginPanelViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginUserController loginUserController;
    private final LoginVerificationViewModel loginVerificationViewModel;
    private final CreateVerificationController createVerificationController;
    private LoginVerificationView loginVerificationView;

    private final JPanel loginPanel = new JPanel();
    private final JLabel emailLabel = new JLabel("Email: ");
    private final JLabel passwordLabel = new JLabel("Password: ");
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginButton = new JButton("Login");

    /**
     * Constructs a LoginPanel.
     *
     * @param viewManagerModel the view manager model
     * @param loginPanelViewModel the view model for the login panel
     * @param loginUserController the controller for logging in the user
     */
    public LoginPanel(ViewManagerModel viewManagerModel,
                      LoginPanelViewModel loginPanelViewModel,
                      LoginUserController loginUserController,
                      LoginVerificationViewModel loginVerificationViewModel,
                      CreateVerificationController createVerificationController) {
        this.loginUserController = loginUserController;
        this.loginPanelViewModel = loginPanelViewModel;
        loginPanelViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;
        this.loginVerificationViewModel = loginVerificationViewModel;
        loginVerificationViewModel.addPropertyChangeListener(this);
        this.createVerificationController = createVerificationController;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        loginPanel.setLayout(new GridLayout(0, 2));

        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        loginButton.addActionListener(e -> {
            createVerificationController.createVerification();
        });

        this.add(loginPanel);
        this.add(loginButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No implementation needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("loginUser")) {
            Boolean success = (Boolean) evt.getNewValue();
            if (success) {
                login();
                JOptionPane.showMessageDialog(this, "Login successful as " + loginPanelViewModel.getLoginName());
                emailField.setText("");
                passwordField.setText("");
            }
            else {
                JOptionPane.showMessageDialog(this, loginPanelViewModel.getErrorMessage());
                passwordField.setText("");
            }
        }

        if (evt.getPropertyName().equals("displayVerify")) {
            loginVerificationView = new LoginVerificationView(loginVerificationViewModel);
        }

        if (evt.getPropertyName().equals("verificationSuccess")) {
            loginVerificationView.dispose();
            JOptionPane.showMessageDialog(this, "Verification successful");
            loginUserController.loginUser(emailField.getText(), new String(passwordField.getPassword()));
        }

        if (evt.getPropertyName().equals("verificationFailure")) {
            loginVerificationView.dispose();
            JOptionPane.showMessageDialog(this, "Verification failed");
        }
    }

    /**
     * Handles the login process by updating the view manager model.
     */
    private void login(){
        viewManagerModel.login();
    }
}
