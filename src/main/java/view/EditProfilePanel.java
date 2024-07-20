package view;

import java.util.HashSet;

import usecase.edituser.EditUserController;
import usecase.getloggedinuser.GetLoggedInUserController;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditProfilePanel extends JPanel implements ActionListener, PropertyChangeListener {
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

    private final JLabel projectTagsLabel = new JLabel("Project Tags");
    private final JTextField projectTagsField = new JTextField();
    private final GridLayout tagPanelLayout = new GridLayout(0, 1);
    private final JPanel tagPanel = new JPanel();
    private final JButton addTagButton = new JButton("Add Tag");
    private final JLabel tagPanelLabel = new JLabel("Project tags: ");
    private final HashSet<String> tags = new HashSet<>();

    private final JButton saveButton = new JButton("Save");




    public EditProfilePanel(ViewManagerModel viewManagerModel, EditUserController editUserController, GetLoggedInUserController getLoggedInUserController) {
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

        addTagButton.addActionListener(e -> {
            String tagText = projectTagsField.getText();
            if (!tagText.isEmpty()) {
                addTagToPanel(tagText);
                tags.add(tagText);
                projectTagsField.setText("");
            }
        });

        saveButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            double desiredCompensation = Double.parseDouble(desiredCompensationField.getText());
            HashSet<String> tags = null;

            // Call the EditUserController to save the user information
            // The userid is just for testing
            //LoginPanelViewModel loginPanelViewModel = null;
            editUserController.editUser(//loginPanelViewModel.getLoginUser()
                    12323 , firstName, lastName, desiredCompensation,tags);
        });

        this.add(saveButton);
    }

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
//        if (evt.getPropertyName().equals("login")) {
//            User user =
//            firstNameField.setText(user.getFirstName());
//            lastNameField.setText(user.getLastName());
//            desiredCompensationField.setText(String.valueOf(user.getDesiredCompensation()));
//        }
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
