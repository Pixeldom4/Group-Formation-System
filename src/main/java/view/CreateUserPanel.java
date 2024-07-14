package view;

import viewmodel.CreateUserPanelViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreateUserPanel extends JPanel implements ActionListener, PropertyChangeListener {
    private final CreateUserPanelViewModel createUserPanelViewModel;

    private final JPanel createUserInfo = new JPanel();
    private final JLabel firstNameLabel = new JLabel("First Name: ");
    private final JLabel lastNameLabel = new JLabel("Last Name: ");
    private final JLabel emailLabel = new JLabel("Email: ");
    private final JLabel passwordLabel = new JLabel("Password: ");
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField passwordField = new JTextField();
    private final JButton createUserButton = new JButton("Create User");


    public CreateUserPanel(CreateUserPanelViewModel createUserPanelViewModel) {
        this.createUserPanelViewModel = createUserPanelViewModel;
        createUserPanelViewModel.addPropertyChangeListener(this);

        createUserInfo.setLayout(new GridLayout(0,2));
        createUserInfo.add(firstNameLabel);
        createUserInfo.add(firstNameField);
        createUserInfo.add(lastNameLabel);
        createUserInfo.add(lastNameField);
        createUserInfo.add(emailLabel);
        createUserInfo.add(emailField);
        createUserInfo.add(passwordLabel);
        createUserInfo.add(passwordField);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(createUserInfo);
        this.add(createUserButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
