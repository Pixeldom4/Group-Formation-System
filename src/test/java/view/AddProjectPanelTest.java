package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageprojects.createproject.CreateProjectController;
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import view.components.NumericTextField;
import viewmodel.AddProjectPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the AddProjectPanel class.
 */
public class AddProjectPanelTest {

    private AddProjectPanel addProjectPanel;
    private AddProjectPanelViewModel addProjectPanelViewModel;
    private CreateProjectController createProjectController;
    private GetLoggedInUserController getLoggedInUserController;
    private ViewManagerModel viewManagerModel;
    private JPanel projectInfoPanel;
    private JPanel projectDataPanel;
    private JTextField projectNameField;
    private NumericTextField projectBudgetField;
    private JTextField projectDescriptionField;
    private JTextField projectTagsField;
    private JButton addTagButton;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        addProjectPanelViewModel = new AddProjectPanelViewModel();
        createProjectController = mock(CreateProjectController.class);
        getLoggedInUserController = mock(GetLoggedInUserController.class);
        viewManagerModel = new ViewManagerModel();

        addProjectPanel = new AddProjectPanel(
                viewManagerModel,
                addProjectPanelViewModel,
                createProjectController,
                getLoggedInUserController
        );

        projectInfoPanel = (JPanel) addProjectPanel.getComponent(0);
        projectDataPanel = (JPanel) projectInfoPanel.getComponent(0);

        // Set component names for easy access in tests
        setComponentNames();
    }

    /**
     * Sets the component names for easy access in tests.
     */
    private void setComponentNames() {
        projectNameField = (JTextField) projectDataPanel.getComponent(1);
        projectBudgetField = (NumericTextField) projectDataPanel.getComponent(3);
        projectDescriptionField = (JTextField) projectDataPanel.getComponent(5);
        projectTagsField = (JTextField) projectDataPanel.getComponent(7);
        addTagButton = (JButton) projectDataPanel.getComponent(8);
    }

    /**
     * Tests the initialization of the AddProjectPanel.
     */
    @Test
    public void testInitialization() {
        assertNotNull(addProjectPanel);
        assertEquals(2, addProjectPanel.getComponentCount());  // Adjust the number based on the components in your panel
    }

    /**
     * Tests the add tag functionality.
     */
    @Test
    public void testAddTag() {
        projectTagsField.setText("testTag");

        addTagButton.doClick();

        assertTrue(doesTagExist("testTag"));
    }

    /**
     * Tests the create project functionality.
     */
    @Test
    public void testCreateProject() {

        projectNameField.setText("Test Project");
        projectBudgetField.setNumber("1000.0");
        projectDescriptionField.setText("Test Description");

        // Simulate logged-in user
        int userId = 1;
        String firstName = "John";
        String lastName = "Doe";
        String userEmail = "john.doe@example.com";
        double desiredCompensation = 5000.0;
        HashSet<String> tags = new HashSet<>();

        addProjectPanelViewModel.setLoggedInUser(userId, firstName, lastName, userEmail, desiredCompensation, tags);

        JButton addProjectButton = (JButton) addProjectPanel.getComponent(1);
        addProjectButton.doClick();

        verify(createProjectController).createProject("Test Project", 1000.0, "Test Description", new HashSet<>(), userId);
    }

    /**
     * Tests the property change for success scenario.
     */
    @Test
    public void testPropertyChangeSuccess() {
        addProjectPanelViewModel.setProjectName("Test Project");
        addProjectPanelViewModel.setSuccess(true);

        // Assuming the success dialog shows up correctly
        assertTrue(addProjectPanelViewModel.isSuccess());
        assertEquals("Test Project", addProjectPanelViewModel.getProjectName());
    }

    /**
     * Tests the property change for failure scenario.
     */
    @Test
    public void testPropertyChangeFailure() {
        addProjectPanelViewModel.setErrorMessage("Error creating project");
        addProjectPanelViewModel.setSuccess(false);

        // Assuming the error dialog shows up correctly
        assertFalse(addProjectPanelViewModel.isSuccess());
        assertEquals("Error creating project", addProjectPanelViewModel.getErrorMessage());
    }

    /**
     * Checks if a tag exists in the tag panel.
     *
     * @param tagName the name of the tag to check.
     * @return true if the tag exists, false otherwise.
     */
    private boolean doesTagExist(String tagName) {
        JPanel tagPanel = (JPanel) projectInfoPanel.getComponent(1);
        for (Component component : tagPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel tag = (JPanel) component;
                for (Component tagComponent : tag.getComponents()) {
                    if (tagComponent instanceof JLabel) {
                        JLabel label = (JLabel) tagComponent;
                        if (tagName.equals(label.getText())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
