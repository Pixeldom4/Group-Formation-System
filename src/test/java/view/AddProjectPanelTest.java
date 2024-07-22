package view;

import static org.junit.jupiter.api.Assertions.*;

import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.createproject.CreateProjectController;
import usecase.getloggedinuser.GetLoggedInUserController;
import view.components.NumericTextField;
import viewmodel.AddProjectPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

public class AddProjectPanelTest {

    private AddProjectPanel addProjectPanel;
    private ViewManagerModel viewManagerModel;
    private AddProjectPanelViewModel addProjectPanelViewModel;
    private TestCreateProjectController createProjectController;
    private TestGetLoggedInUserController getLoggedInUserController;

    @BeforeEach
    public void setUp() {
        viewManagerModel = new ViewManagerModel();
        addProjectPanelViewModel = new AddProjectPanelViewModel();
        createProjectController = new TestCreateProjectController();
        getLoggedInUserController = new TestGetLoggedInUserController();

        addProjectPanel = new AddProjectPanel(viewManagerModel, addProjectPanelViewModel, createProjectController, getLoggedInUserController);
    }

    @Test
    public void testAddTagToPanel() {
        JTextField projectTagsField = getFieldValue(addProjectPanel, "projectTagsField");
        JButton addTagButton = getFieldValue(addProjectPanel, "addTagButton");

        projectTagsField.setText("NewTag");
        addTagButton.doClick();

        JPanel tagPanel = getFieldValue(addProjectPanel, "tagPanel");
        assertEquals(2, tagPanel.getComponentCount()); // One JLabel and one tag panel
    }

    @Test
    public void testAddProjectSuccess() {
        JTextField projectNameField = getFieldValue(addProjectPanel, "projectNameField");
        NumericTextField projectBudgetField = getFieldValue(addProjectPanel, "projectBudgetField");
        JTextField projectDescriptionField = getFieldValue(addProjectPanel, "projectDescriptionField");
        JButton addProjectButton = getFieldValue(addProjectPanel, "addProjectButton");

        projectNameField.setText("Test Project");
        projectBudgetField.setText("1000");
        projectDescriptionField.setText("Test Description");

        User testUser = new User(1, "Test", "User", "test@example.com", new HashSet<>(), 1000.0);
        addProjectPanelViewModel.setLoggedInUser(testUser);

        addProjectButton.doClick();

        assertTrue(createProjectController.isProjectCreated());
        assertEquals("Test Project", createProjectController.getTitle());
        assertEquals(1000.0, createProjectController.getBudget());
        assertEquals("Test Description", createProjectController.getDescription());
        assertEquals(testUser.getUserId(), createProjectController.getUserId());
    }

    @Test
    public void testAddProjectWithoutLogin() {
        JTextField projectNameField = getFieldValue(addProjectPanel, "projectNameField");
        NumericTextField projectBudgetField = getFieldValue(addProjectPanel, "projectBudgetField");
        JTextField projectDescriptionField = getFieldValue(addProjectPanel, "projectDescriptionField");
        JButton addProjectButton = getFieldValue(addProjectPanel, "addProjectButton");

        projectNameField.setText("Test Project");
        projectBudgetField.setText("1000");
        projectDescriptionField.setText("Test Description");

        addProjectPanelViewModel.setLoggedInUser(null);

        addProjectButton.doClick();

        assertFalse(createProjectController.isProjectCreated());
    }

    @Test
    public void testPropertyChangeSuccess() {
        addProjectPanelViewModel.setProjectName("Test Project");
        addProjectPanelViewModel.setSuccess(true);

        PropertyChangeEvent event = new PropertyChangeEvent(this, "success", false, true);
        addProjectPanel.propertyChange(event);

        assertEquals("", getFieldValue(addProjectPanel, "projectNameField").getClass());
        assertEquals("", getFieldValue(addProjectPanel, "projectBudgetField").getClass());
        assertEquals("", getFieldValue(addProjectPanel, "projectDescriptionField").getClass());
        assertEquals("", getFieldValue(addProjectPanel, "projectTagsField").getClass());
    }

    @Test
    public void testPropertyChangeLogin() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "login", null, true);

        addProjectPanel.propertyChange(event);

        assertTrue(getLoggedInUserController.isGetLoggedInUserCalled());
    }

    @Test
    public void testViewManagerModelInteraction() {
        viewManagerModel.login();
        assertEquals("EditMyProfile", viewManagerModel.getActiveView());
    }

    // Utility method to get private fields from AddProjectPanel for testing
    @SuppressWarnings("unchecked")
    private <T> T getFieldValue(Object object, String fieldName) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get field value", e);
        }
    }

    // Test implementation of CreateProjectController
    private static class TestCreateProjectController implements CreateProjectController {

        private boolean projectCreated = false;
        private String title;
        private double budget;
        private String description;
        private int userId;

        @Override
        public void createProject(String title, double budget, String description, HashSet<String> tags, int userId) {
            this.projectCreated = true;
            this.title = title;
            this.budget = budget;
            this.description = description;
            this.userId = userId;
        }

        public boolean isProjectCreated() {
            return projectCreated;
        }

        public String getTitle() {
            return title;
        }

        public double getBudget() {
            return budget;
        }

        public String getDescription() {
            return description;
        }

        public int getUserId() {
            return userId;
        }
    }

    // Test implementation of GetLoggedInUserController
    private static class TestGetLoggedInUserController implements GetLoggedInUserController {

        private boolean getLoggedInUserCalled = false;

        @Override
        public void getLoggedInUser() {
            this.getLoggedInUserCalled = true;
        }

        public boolean isGetLoggedInUserCalled() {
            return getLoggedInUserCalled;
        }
    }
}
