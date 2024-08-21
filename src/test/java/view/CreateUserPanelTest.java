package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageusers.ManageUsersController;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import viewmodel.CreateUserPanelViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class CreateUserPanelTest {

    private CreateUserPanelViewModel mockViewModel;
    private ManageUsersController mockController;
    private IHoverVoiceService mockHoverVoiceService;
    private IPlayVoiceService mockPlayVoiceService;
    private CreateUserPanel createUserPanel;

    @BeforeEach
    void setUp() {
        mockViewModel = mock(CreateUserPanelViewModel.class);
        mockController = mock(ManageUsersController.class);
        mockHoverVoiceService = mock(IHoverVoiceService.class);
        mockPlayVoiceService = mock(IPlayVoiceService.class);

        // Initialize the panel using a separate method or factory
        createUserPanel = createTestPanel();
    }

    @Test
    void testCreateUserSuccess() {
        // Simulate entering user data
        JTextField firstNameField = (JTextField) findComponentByName(createUserPanel, "firstNameField");
        JTextField lastNameField = (JTextField) findComponentByName(createUserPanel, "lastNameField");
        JTextField emailField = (JTextField) findComponentByName(createUserPanel, "emailField");
        JPasswordField passwordField = (JPasswordField) findComponentByName(createUserPanel, "passwordField");
        JTextField compensationField = (JTextField) findComponentByName(createUserPanel, "compensationField");

        firstNameField.setText("John");
        lastNameField.setText("Doe");
        emailField.setText("john.doe@example.com");
        passwordField.setText("password123");
        compensationField.setText("50000");

        // Simulate clicking the "Create User" button
        JButton createUserButton = (JButton) findComponentByName(createUserPanel, "createUserButton");
        createUserButton.doClick();

        // Verify that the controller's createUser method was called with the correct parameters
        verify(mockController, times(1)).createUser(
                eq("John"),
                eq("Doe"),
                eq("john.doe@example.com"),
                eq(50000.0),
                any(HashSet.class),
                eq("password123")
        );

        // Simulate a successful creation response
        when(mockViewModel.getSuccessMessage()).thenReturn("User created successfully");
        createUserPanel.propertyChange(new PropertyChangeEvent(this, "success", false, true));

        // Verify that the success message was shown and the fields were cleared
        verify(mockPlayVoiceService, times(1)).playVoice("User created successfully");
        assertEmptyFields(firstNameField, lastNameField, emailField, passwordField, compensationField);
    }

    @Test
    void testCreateUserFailure() {
        // Simulate entering user data
        JTextField firstNameField = (JTextField) findComponentByName(createUserPanel, "firstNameField");
        JTextField lastNameField = (JTextField) findComponentByName(createUserPanel, "lastNameField");
        JTextField emailField = (JTextField) findComponentByName(createUserPanel, "emailField");
        JPasswordField passwordField = (JPasswordField) findComponentByName(createUserPanel, "passwordField");
        JTextField compensationField = (JTextField) findComponentByName(createUserPanel, "compensationField");

        firstNameField.setText("John");
        lastNameField.setText("Doe");
        emailField.setText("john.doe@example.com");
        passwordField.setText("password123");
        compensationField.setText("50000");

        // Simulate clicking the "Create User" button
        JButton createUserButton = (JButton) findComponentByName(createUserPanel, "createUserButton");
        createUserButton.doClick();

        // Simulate a failed creation response
        when(mockViewModel.getErrorMessage()).thenReturn("Error creating user");
        createUserPanel.propertyChange(new PropertyChangeEvent(this, "success", false, false));

        // Verify that the error message was shown
        verify(mockPlayVoiceService, times(1)).playVoice("Error creating user");
    }

    // Method to create the panel with mocked services injected
    private CreateUserPanel createTestPanel() {
        return new TestCreatePanel(mockViewModel, mockController,
                                   mockHoverVoiceService, mockPlayVoiceService);
    }

    // Utility method to find a component by its name
    private Component findComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
            if (component instanceof Container) {
                Component child = findComponentByName((Container) component, name);
                if (child != null) {
                    return child;
                }
            }
        }
        return null;
    }

    // Utility method to verify that all the text fields are cleared
    private void assertEmptyFields(JTextField... fields) {
        for (JTextField field : fields) {
            assertEquals("", field.getText());
        }
    }

    private static class TestCreatePanel extends CreateUserPanel {
        public TestCreatePanel(CreateUserPanelViewModel viewModel, ManageUsersController controller,
                               IHoverVoiceService hoverVoiceService, IPlayVoiceService playVoiceService) {
            super(viewModel, controller);
            this.hoverVoiceService = hoverVoiceService;
            this.playVoiceService = playVoiceService;
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
