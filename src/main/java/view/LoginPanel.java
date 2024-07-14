package view;

import viewmodel.LoginPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginPanel extends JPanel implements ActionListener, PropertyChangeListener {
    private final LoginPanelViewModel loginPanelViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JPanel loginPanel = new JPanel();
    private final JLabel tempLabel = new JLabel("Login system coming soon please press button to continue");
    private final JLabel emailLabel = new JLabel("Email: ");
    private final JLabel passwordLabel = new JLabel("Password: ");
    private final JTextField emailField = new JTextField();
    private final JTextField passwordField = new JTextField();
    private final JButton loginButton = new JButton("Login");

    public LoginPanel(ViewManagerModel viewManagerModel, LoginPanelViewModel loginPanelViewModel) {
        this.loginPanelViewModel = loginPanelViewModel;
        loginPanelViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        loginPanel.add(tempLabel);

        loginButton.addActionListener(e -> login(100));

        this.add(tempLabel);
        this.add(loginButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    private void login(int userId){
        viewManagerModel.setLogin(true);
        viewManagerModel.setUserId(userId);
        viewManagerModel.setActiveView("SearchPanelView");
        viewManagerModel.firePropertyChanged();
    }
}
