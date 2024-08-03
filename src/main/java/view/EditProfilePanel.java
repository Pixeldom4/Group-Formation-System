package view;

import entities.User;
import usecase.edituser.EditUserController;
import usecase.getloggedinuser.GetLoggedInUserController;
import view.components.TagPanel;
import view.services.hovervoice.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import view.services.playvoice.PlayVoiceServiceConfig;
import viewmodel.EditProfileViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A panel for editing the user's profile.
 */
@SuppressWarnings("FieldCanBeLocal")
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
    private final JLabel emailLabel = new JLabel("Email");
    private final JLabel desiredCompensationLabel = new JLabel("Desired Compensation");
    private final JLabel projectTagsLabel = new JLabel("User Tags To Add");

    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField desiredCompensationField = new NumericTextField();
    private final JTextField projectTagsField = new JTextField();

    private final JButton addTagButton = new JButton("Add Tag");
    private final GridLayout tagPanelLayout = new GridLayout(0, 1);
    private final TagPanel tagPanel = new TagPanel();

    private final JLabel tagPanelLabel = new JLabel("User tags (Press add tag to add): ");
    private final JButton saveButton = new JButton("Save");

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

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

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        this.playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));

        addTagButton.addActionListener(_ -> {
            String tagText = projectTagsField.getText();
            if (!tagText.isEmpty()) {
                tagPanel.addTagToPanel(tagText);
                projectTagsField.setText("");
            }
        });

        userDataPanel.setLayout(userDataGridLayout);

        hoverVoiceService.addHoverVoice(firstNameField, "Enter new first name here");
        hoverVoiceService.addHoverVoice(lastNameField, "Enter new last name here");
        hoverVoiceService.addHoverVoice(emailField, "Enter new email here");
        hoverVoiceService.addHoverVoice(desiredCompensationField, "Enter new desired compensation here");
        hoverVoiceService.addHoverVoice(projectTagsField, "Enter new tags here");
        hoverVoiceService.addHoverVoice(addTagButton, "Press to add tag");

        userDataPanel.add(firstNameLabel);
        userDataPanel.add(firstNameField);
        userDataPanel.add(lastNameLabel);
        userDataPanel.add(lastNameField);
        userDataPanel.add(emailLabel);
        userDataPanel.add(emailField);
        userDataPanel.add(desiredCompensationLabel);
        userDataPanel.add(desiredCompensationField);
        userDataPanel.add(projectTagsLabel);
        userDataPanel.add(projectTagsField);
        userDataPanel.add(addTagButton);

        userInfoPanel.add(userDataPanel);

        tagPanel.add(tagPanelLabel);
        tagPanel.setLayout(tagPanelLayout);
        tagPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        userInfoPanel.add(tagPanel);

        this.add(userInfoPanel);

        hoverVoiceService.addHoverVoice(saveButton, "Press to save profile");
        saveButton.addActionListener(_ -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            double desiredCompensation = Double.parseDouble(desiredCompensationField.getText());

            // Call the EditUserController to save the user information
            editUserController.editUser(editProfileViewModel.getUserId(), firstName, lastName, email, desiredCompensation, tagPanel.getTags());
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
            getLoggedInUserController.getLoggedInUser();
        }

        if (evt.getPropertyName().equals("userUpdate")) {
            User loggedInUser = (User) evt.getNewValue();
            if (loggedInUser != null) {
                firstNameField.setText(loggedInUser.getFirstName());
                lastNameField.setText(loggedInUser.getLastName());
                emailField.setText(loggedInUser.getUserEmail());
                desiredCompensationField.setText(String.valueOf(loggedInUser.getDesiredCompensation()));
                for (String tag : loggedInUser.getTags()) {
                    tagPanel.addTagToPanel(tag);
                }
            } else {
                firstNameField.setText("");
                lastNameField.setText("");
                emailField.setText("");
                desiredCompensationField.setText("");
                projectTagsField.setText("");
                tagPanel.clearPanel();
            }
        }

        if (evt.getPropertyName().equals("saveUser")) {
            Boolean success = (Boolean) evt.getNewValue();
            if (success) {
                getLoggedInUserController.getLoggedInUser();
                playVoiceService.playVoice("Profile updated successfully!");
                JOptionPane.showMessageDialog(null, "Profile updated successfully!");
            } else {
                playVoiceService.playVoice("Failed to update profile: " + editProfileViewModel.getErrorMessage());
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
