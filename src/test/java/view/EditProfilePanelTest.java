package view;

import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageusers.ManageUsersController;
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import view.components.TagPanel;
import viewmodel.EditProfileViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditProfilePanelTest {

    private EditProfilePanel editProfilePanel;
    private ManageUsersController manageUsersController;
    private GetLoggedInUserController getLoggedInUserController;
    private EditProfileViewModel editProfileViewModel;
    private ViewManagerModel viewManagerModel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField desiredCompensationField;
    private JTextField projectTagsField;
    private TagPanel tagPanel;
    private JButton addTagButton;
    private JButton saveButton;

    @BeforeEach
    public void setUp() {
        manageUsersController = mock(ManageUsersController.class);
        getLoggedInUserController = mock(GetLoggedInUserController.class);
        editProfileViewModel = new EditProfileViewModel();
        viewManagerModel = new ViewManagerModel();

        editProfilePanel = new TestEditProfilePanel(viewManagerModel, manageUsersController, getLoggedInUserController, editProfileViewModel);

        firstNameField = (JTextField) getComponentByName(editProfilePanel, "firstNameField");
        lastNameField = (JTextField) getComponentByName(editProfilePanel, "lastNameField");
        emailField = (JTextField) getComponentByName(editProfilePanel, "emailField");
        desiredCompensationField = (JTextField) getComponentByName(editProfilePanel, "desiredCompensationField");
        projectTagsField = (JTextField) getComponentByName(editProfilePanel, "projectTagsField");
        tagPanel = (TagPanel) getComponentByName(editProfilePanel, "tagPanel");
        addTagButton = (JButton) getComponentByName(editProfilePanel, "addTagButton");
        saveButton = (JButton) getComponentByName(editProfilePanel, "saveButton");
    }

    @Test
    public void initialization() {
        assertNotNull(editProfilePanel);
        assertNotNull(firstNameField);
        assertNotNull(lastNameField);
        assertNotNull(emailField);
        assertNotNull(desiredCompensationField);
        assertNotNull(projectTagsField);
        assertNotNull(tagPanel);
        assertNotNull(addTagButton);
        assertNotNull(saveButton);
    }

    @Test
    public void addTagFunctionality() {
        projectTagsField.setText("testTag");
        addTagButton.doClick();
        assertTrue(tagPanel.getTags().contains("testTag"));
    }

    @Test
    public void saveFunctionality() {
        editProfileViewModel.setLoggedInUser(1, "Jerry", "Eod",
                                             "email@email.com", 1234.0, new HashSet<>());

        firstNameField.setText("John");
        lastNameField.setText("Doe");
        emailField.setText("john.doe@example.com");
        desiredCompensationField.setText("5000.0");
        tagPanel.addTagToPanel("testTag");

        saveButton.doClick();

        verify(manageUsersController).editUser(
                editProfileViewModel.getUserId(),
                "John",
                "Doe",
                "john.doe@example.com",
                5000.0,
                new HashSet<>(tagPanel.getTags())
        );
    }

    @Test
    public void propertyChangeLogin() {
        PropertyChangeEvent evt = new PropertyChangeEvent(viewManagerModel, "login", null, null);
        editProfilePanel.propertyChange(evt);
        verify(getLoggedInUserController).getLoggedInUser();
    }

    @Test
    public void propertyChangeUserUpdate() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", new HashSet<>(), 5000.0);
        PropertyChangeEvent evt = new PropertyChangeEvent(viewManagerModel, "userUpdate", null, user);
        editProfilePanel.propertyChange(evt);

        assertEquals("John", firstNameField.getText());
        assertEquals("Doe", lastNameField.getText());
        assertEquals("john.doe@example.com", emailField.getText());
        assertEquals("5000.0", desiredCompensationField.getText());
    }

    @Test
    public void propertyChangeSaveUserSuccess() {
        PropertyChangeEvent evt = new PropertyChangeEvent(editProfileViewModel, "saveUser", null, true);
        editProfilePanel.propertyChange(evt);
        verify(getLoggedInUserController).getLoggedInUser();
    }

    @Test
    public void propertyChangeSaveUserFailure() {
        editProfileViewModel.saveFail("Error updating profile");
        PropertyChangeEvent evt = new PropertyChangeEvent(editProfileViewModel, "saveUser", null, false);
        editProfilePanel.propertyChange(evt);
        assertEquals("Error updating profile", editProfileViewModel.getErrorMessage());
    }

    private Component getComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
            if (component instanceof Container) {
                Component child = getComponentByName((Container) component, name);
                if (child != null) {
                    return child;
                }
            }
        }
        return null;
    }

    private static class TestEditProfilePanel extends EditProfilePanel {
        public TestEditProfilePanel(ViewManagerModel viewManagerModel, ManageUsersController manageUsersController, GetLoggedInUserController getLoggedInUserController, EditProfileViewModel editProfileViewModel) {
            super(viewManagerModel, manageUsersController, getLoggedInUserController, editProfileViewModel);
        }

        @Override
        protected void showDialog(JDialog dialog) {
            dialog.setAlwaysOnTop(true);
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            Timer timer = new Timer(10, _ -> dialog.dispose());
            timer.setRepeats(false);
            timer.start();

            dialog.setVisible(true);
        }
    }
}