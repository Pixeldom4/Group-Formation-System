package view;

import entities.User;
import usecase.edituser.EditUserController;
import usecase.getloggedinuser.GetLoggedInUserController;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MyProfilePanel extends JPanel implements ActionListener, PropertyChangeListener {
    private final GetLoggedInUserController getLoggedInUserController;
    private final ViewManagerModel viewManagerModel;
    private final EditUserController editUserController;

    private final JPanel userInfoPanel = new JPanel();
    private final JPanel userDataPanel = new JPanel();
    private final GridLayout userDataGridLayout = new GridLayout(0, 2);

    private final JLabel firstNameLabel = new JLabel("First Name");
    private final JLabel lastNameLabel = new JLabel("Last Name");
    private final JLabel desiredCompensationLabel = new JLabel("Desired Compensation");

    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField desiredCompensationField = new NumericTextField();

    private final JButton saveButton = new JButton("Save");

    public MyProfilePanel(ViewManagerModel viewManagerModel, EditUserController editUserController, GetLoggedInUserController getLoggedInUserController) {
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
        this.editUserController = editUserController;
        this.getLoggedInUserController = getLoggedInUserController;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));

        userDataPanel.setLayout(userDataGridLayout);

        userDataPanel.add(firstNameLabel);
        userDataPanel.add(firstNameField);
        userDataPanel.add(lastNameLabel);
        userDataPanel.add(lastNameField);
        userDataPanel.add(desiredCompensationLabel);
        userDataPanel.add(desiredCompensationField);

        userInfoPanel.add(userDataPanel);

        this.add(userInfoPanel);

        saveButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            double desiredCompensation = Double.parseDouble(desiredCompensationField.getText());

            // Call the EditUserController to save the user information
            editUserController.editUser(firstName, lastName, desiredCompensation);
        });

        this.add(saveButton);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // Implement any additional action events if needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("login")) {
            User user = (User) evt.getNewValue();
            firstNameField.setText(user.getFirstName());
            lastNameField.setText(user.getLastName());
            desiredCompensationField.setText(String.valueOf(user.getDesiredCompensation()));
        }
        if (evt.getPropertyName().equals("login")) {
            getLoggedInUserController.getLoggedInUser();
        }
    }

    /**
     * A helper class to ensure only numeric input is allowed in a text field.
     */
    static class NumericTextField extends JTextField {
        public NumericTextField() {
            super();
            setDocument(new PlainDocument() {
                @Override
                public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                    if (str == null) {
                        return;
                    }
                    for (int i = 0; i < str.length(); i++) {
                        if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != '.') {
                            return;
                        }
                    }
                    super.insertString(offs, str, a);
                }
            });
        }
    }
}
