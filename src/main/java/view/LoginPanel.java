package view;

import usecase.createverification.CreateVerificationController;
import usecase.loginuser.LoginUserController;
import config.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import config.PlayVoiceServiceConfig;
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
@SuppressWarnings("FieldCanBeLocal")
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

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

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

        hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        loginPanel.setLayout(new GridLayout(0, 2));

        hoverVoiceService.addHoverVoice(emailField, "Enter email");
        hoverVoiceService.addHoverVoice(passwordField, "Enter password");

        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        loginButton.addActionListener(e -> createVerificationController.createVerification());

        hoverVoiceService.addHoverVoice(loginButton, "Press to login");

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
                String message = "Login successful as " + loginPanelViewModel.getLoginName();
                playVoiceService.playVoice(message);
                JOptionPane.showMessageDialog(this, message);
                emailField.setText("");
                passwordField.setText("");
            }
            else {
                playVoiceService.playVoice("Login failed, " + loginPanelViewModel.getErrorMessage());
                JOptionPane.showMessageDialog(this, loginPanelViewModel.getErrorMessage());
                passwordField.setText("");
            }
        }

        if (evt.getPropertyName().equals("displayVerify")) {
            loginVerificationView = new LoginVerificationView(loginVerificationViewModel);
            loginVerificationView.setLocationRelativeTo(this);
        }

        if (evt.getPropertyName().equals("verificationSuccess")) {
            showVerificationResult(true);
        }

        if (evt.getPropertyName().equals("verificationFailure")) {
            showVerificationResult(false);
        }
    }

    private void showVerificationResult(boolean success) {
        Thread t = new Thread(() -> {
            new VerifyResultWindow(this, success);
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                System.err.println("Verification window thread interrupted");
            }
            loginVerificationView.dispose();
            loginUserController.loginUser(emailField.getText(), String.valueOf(passwordField.getPassword()));
        });
        t.start();
    }


    /**
     * Handles the login process by updating the view manager model.
     */
    private void login(){
        viewManagerModel.login();
    }

    private static class VerifyResultWindow extends JFrame {
        private final Color green = new Color(161, 212, 150);
        private final Color red = new Color(207, 154, 147);

        public VerifyResultWindow(JPanel parent, boolean success) {
            setTitle("Verification Result");
            setSize(200, 100);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(parent);

            JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
            messageLabel.setOpaque(true);
            if (success) {
                messageLabel.setText("Verification successful");
                messageLabel.setBackground(green);
                setBackground(Color.GREEN);
            } else {
                messageLabel.setText("Verification failed");
                messageLabel.setBackground(red);
                setBackground(Color.RED);
            }
            add(messageLabel, BorderLayout.CENTER);

            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("Verification result window thread interrupted");
                }
                this.dispose();
            });

            setVisible(true);
            t.start();
        }
    }
}
