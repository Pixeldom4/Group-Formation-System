package view;

import entities.User;
import usecase.edituser.EditUserController;
import usecase.getloggedinuser.GetLoggedInUserController;
import viewmodel.EditProfileViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

/**
 * A panel for editing the user's profile.
 */
public class EditProfilePanel extends JPanel implements ActionListener, PropertyChangeListener {
    private final EditUserController editUserController;
    private final ViewManagerModel viewManagerModel;
    private final GetLoggedInUserController getLoggedInUserController;
    private final EditProfileViewModel editProfileViewModel;

    private final JPanel userInfoPanel = new JPanel();
    private final JPanel userDataPanel = new JPanel();
    private final GridLayout userDataGridLayout = new GridLayout(0, 2);

    private final JLabel firstNameLabel = new JLabel("First Name");
    private final JLabel lastNameLabel = new JLabel("Last Name");
    private final JLabel desiredCompensationLabel = new JLabel("Desired Compensation");
    private final JLabel projectTagsLabel = new JLabel("User Tags To Add");

    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField desiredCompensationField = new NumericTextField();
    private final JTextField projectTagsField = new JTextField();

    private final GridLayout tagPanelLayout = new GridLayout(0, 1);
    private final JPanel tagPanel = new JPanel();
    private final JButton addTagButton = new JButton("Add Tag");
    private final JLabel tagPanelLabel = new JLabel("User tags (Press add tag to add): ");
    private final HashSet<String> tags = new HashSet<>();
    private final JButton myCurrentProfileButton = new JButton("Current Profile");
    private final JButton saveButton = new JButton("Save");

    /**
     * Constructs an EditProfilePanel.
     *
     * @param viewManagerModel the view manager model
     * @param editUserController the controller for editing user information
     * @param getLoggedInUserController the controller for getting the logged-in user
     * @param editProfileViewModel the view model for editing the profile
     */
    public EditProfilePanel(ViewManagerModel viewManagerModel, EditUserController editUserController, GetLoggedInUserController getLoggedInUserController, EditProfileViewModel editProfileViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getLoggedInUserController = getLoggedInUserController;
        this.editProfileViewModel = editProfileViewModel;
        viewManagerModel.addPropertyChangeListener(this);
        editProfileViewModel.addPropertyChangeListener(this);
        this.editUserController = editUserController;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));

        userDataPanel.setLayout(userDataGridLayout);

        userDataPanel.add(firstNameLabel);
        userDataPanel.add(firstNameField);
        userDataPanel.add(lastNameLabel);
        userDataPanel.add(lastNameField);
        userDataPanel.add(desiredCompensationLabel);
        userDataPanel.add(desiredCompensationField);
        userDataPanel.add(projectTagsLabel);
        userDataPanel.add(projectTagsField);

        userInfoPanel.add(userDataPanel);

        tagPanel.add(tagPanelLabel);
        tagPanel.setLayout(tagPanelLayout);
        tagPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addTagButton.addActionListener(e -> {
            String tagText = projectTagsField.getText();
            if (!tagText.isEmpty()) {
                addTagToPanel(tagText);
                tags.add(tagText);
                projectTagsField.setText("");
            }
        });

        userInfoPanel.add(projectTagsField);
        userInfoPanel.add(addTagButton);
        userInfoPanel.add(tagPanel);

        this.add(userInfoPanel);

        saveButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            double desiredCompensation = Double.parseDouble(desiredCompensationField.getText());
            String email = editProfileViewModel.getUserEmail();

            // Call the EditUserController to save the user information
            editUserController.editUser(editProfileViewModel.getUserId(), firstName, lastName, email, desiredCompensation, tags);
        });

        this.add(saveButton);
        this.add(myCurrentProfileButton);

        myCurrentProfileButton.addActionListener(e -> {
            User loggedInUser = editProfileViewModel.getLoggedInUser();
            new DisplayMyProfileView(loggedInUser); // Use this line when want to display project
        });
    }

    /**
     * Adds a tag to the tag panel.
     *
     * @param text the text of the tag to add
     */
    private void addTagToPanel(String text) {
        if (text.isEmpty()) {
            return;
        }
        if (tags.contains(text)) {
            return;
        }

        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JButton removeButton = new JButton("x");

        JPanel tag = new JPanel();
        tag.setBorder(new EmptyBorder(0, 10, 0, 10));
        tag.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;

        constraints.gridx = 0;
        constraints.weightx = 0.8;
        tag.add(label, constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.2;
        tag.add(removeButton, constraints);

        removeButton.addActionListener(e -> {
            tags.remove(text);
            tagPanel.remove(tag);
            tagPanel.revalidate();
            tagPanel.repaint();
        });

        tagPanel.add(tag);
        tagPanel.revalidate();
        tagPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Implement any additional action events if needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("login")) {
            getLoggedInUserController.getLoggedInUser();
        }

        if (evt.getPropertyName().equals("userUpdate")) {
            User loggedInUser = (User) evt.getNewValue();
            if (loggedInUser != null) {
                firstNameField.setText(loggedInUser.getFirstName());
                lastNameField.setText(loggedInUser.getLastName());
                desiredCompensationField.setText(String.valueOf(loggedInUser.getDesiredCompensation()));
                for (String tag : loggedInUser.getTags()) {
                    addTagToPanel(tag);
                    tags.add(tag);
                }
            }
            else {
                firstNameField.setText("");
                lastNameField.setText("");
                desiredCompensationField.setText("");
                projectTagsField.setText("");
                tags.clear();
                tagPanel.removeAll();
                tagPanel.revalidate();
                tagPanel.repaint();
            }
        }

        if (evt.getPropertyName().equals("saveUser")) {
            Boolean success = (Boolean) evt.getNewValue();
            if (success) {
                getLoggedInUserController.getLoggedInUser();
                JOptionPane.showMessageDialog(null, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update profile: " + editProfileViewModel.getErrorMessage());
            }
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
