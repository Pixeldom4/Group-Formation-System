package view;

import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.createproject.CreateProjectController;
import usecase.getloggedinuser.GetLoggedInUserController;
import view.components.NumericTextField;
import viewmodel.AddProjectPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddProjectPanelTest {

    private AddProjectPanel addProjectPanel;
    private AddProjectPanelViewModel addProjectPanelViewModel;
    private CreateProjectController createProjectController;
    private GetLoggedInUserController getLoggedInUserController;
    private ViewManagerModel viewManagerModel;

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

        // Set component names for easy access in tests
        setComponentNames();
    }

    private void setComponentNames() {
        ((JTextField) getComponentByName(addProjectPanel, "projectNameField")).setName("projectNameField");
        ((NumericTextField) getComponentByName(addProjectPanel, "projectBudgetField")).setName("projectBudgetField");
        ((JTextField) getComponentByName(addProjectPanel, "projectDescriptionField")).setName("projectDescriptionField");
        ((JTextField) getComponentByName(addProjectPanel, "projectTagsField")).setName("projectTagsField");
        ((JButton) getComponentByName(addProjectPanel, "addTagButton")).setName("addTagButton");
        ((JButton) getComponentByName(addProjectPanel, "addProjectButton")).setName("addProjectButton");
    }

    @Test
    public void testInitialization() {
        assertNotNull(addProjectPanel);
        assertEquals(2, addProjectPanel.getComponentCount());  // Adjust the number based on the components in your panel
    }

    @Test
    public void testAddTag() {
        JTextField projectTagsField = (JTextField) getComponentByName(addProjectPanel, "projectTagsField");
        projectTagsField.setText("testTag");

        JButton addTagButton = (JButton) getComponentByName(addProjectPanel, "addTagButton");
        addTagButton.doClick();

        assertTrue(doesTagExist("testTag"));
    }

    @Test
    public void testCreateProject() {
        JTextField projectNameField = (JTextField) getComponentByName(addProjectPanel, "projectNameField");
        NumericTextField projectBudgetField = (NumericTextField) getComponentByName(addProjectPanel, "projectBudgetField");
        JTextField projectDescriptionField = (JTextField) getComponentByName(addProjectPanel, "projectDescriptionField");

        projectNameField.setText("Test Project");
        projectBudgetField.setText("1000.0");
        projectDescriptionField.setText("Test Description");

        // Simulate logged-in user
        int userId = 1;
        String firstName = "John";
        String lastName = "Doe";
        String userEmail = "john.doe@example.com";
        double desiredCompensation = 5000.0;
        HashSet<String> tags = new HashSet<>();

        addProjectPanelViewModel.setLoggedInUser(userId, firstName, lastName, userEmail, desiredCompensation, tags);

        JButton addProjectButton = (JButton) getComponentByName(addProjectPanel, "addProjectButton");
        addProjectButton.doClick();

        verify(createProjectController).createProject("Test Project", 1000.0, "Test Description", new HashSet<>(), userId);
    }

    @Test
    public void testPropertyChangeSuccess() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "success", false, true);
        addProjectPanelViewModel.setProjectName("Test Project");
        addProjectPanel.propertyChange(event);

        // Assuming the success dialog shows up correctly
        assertTrue(addProjectPanelViewModel.isSuccess());
        assertEquals("Test Project", addProjectPanelViewModel.getProjectName());
    }

    @Test
    public void testPropertyChangeFailure() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "success", true, false);
        addProjectPanelViewModel.setErrorMessage("Error creating project");
        addProjectPanel.propertyChange(event);

        // Assuming the error dialog shows up correctly
        assertFalse(addProjectPanelViewModel.isSuccess());
        assertEquals("Error creating project", addProjectPanelViewModel.getErrorMessage());
    }

    private JComponent getComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return (JComponent) component;
            }
            if (component instanceof Container) {
                JComponent result = getComponentByName((Container) component, name);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private boolean doesTagExist(String tagName) {
        JPanel tagPanel = (JPanel) getComponentByName(addProjectPanel, "tagPanel");
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
