package view;

import usecase.loginuser.LoginUserController;
import viewmodel.LoginPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginPanel extends JPanel implements ActionListener, PropertyChangeListener {
    private final LoginPanelViewModel loginPanelViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginUserController loginUserController;

    private final JPanel loginPanel = new JPanel();
    private final JLabel emailLabel = new JLabel("Email: ");
    private final JLabel passwordLabel = new JLabel("Password: ");
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginButton = new JButton("Login");

    public LoginPanel(ViewManagerModel viewManagerModel, LoginPanelViewModel loginPanelViewModel, LoginUserController loginUserController) {
        this.loginUserController = loginUserController;
        this.loginPanelViewModel = loginPanelViewModel;
        loginPanelViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        loginPanel.setLayout(new GridLayout(0, 2));

        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        loginButton.addActionListener(e -> {
            loginUserController.loginUser(emailField.getText(), new String(passwordField.getPassword()));
        });

        this.add(loginPanel);
        this.add(loginButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("loginUser")) {
            Boolean success = (Boolean) evt.getNewValue();
            if (success) {
                login();
                JOptionPane.showMessageDialog(null, "Login successful as " + loginPanelViewModel.getLoginName());
                emailField.setText("");
                passwordField.setText("");
            }
            else {
                JOptionPane.showMessageDialog(null, loginPanelViewModel.getErrorMessage());
                passwordField.setText("");
            }
        }
    }

    private void login(){
        viewManagerModel.setActiveView("SearchPanelView");
        viewManagerModel.login();
        viewManagerModel.firePropertyChanged();
    }
}
