package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageapplications.ManageApplicationsController;
import usecase.manageprojects.ManageProjectsController;
import usecase.manageprojects.editproject.EditProjectInputBoundary;
import usecase.manageprojects.editproject.EditProjectInputData;
import viewmodel.DisplayProjectApplicationViewModel;
import viewmodel.EditProjectPanelViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the EditProjectPanel class.
 */
@SuppressWarnings("FieldCanBeLocal")
public class EditProjectPanelTest {

    private EditProjectPanel editProjectPanel;
    private EditProjectPanelViewModel editProjectViewModel;
    private DisplayProjectApplicationViewModel displayProjectApplicationViewModel;
    private ManageProjectsController manageProjectsController;
    private ManageApplicationsController manageApplicationsController;
    private EditProjectInputBoundary editProjectInteractor;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        editProjectViewModel = new EditProjectPanelViewModel();

        // Mock dependencies
        editProjectInteractor = mock(EditProjectInputBoundary.class);
        manageApplicationsController = mock(ManageApplicationsController.class);
        manageProjectsController = new ManageProjectsController(null, null,
                                                                editProjectInteractor, null);
        displayProjectApplicationViewModel = mock(DisplayProjectApplicationViewModel.class);

        editProjectPanel = new EditProjectPanel(
                editProjectViewModel,
                manageApplicationsController,
                manageProjectsController,
                displayProjectApplicationViewModel
        );
    }

    /**
     * Tests the initialization of the EditProjectPanel.
     */
    @Test
    public void testInitialization() {
        assertNotNull(editProjectPanel);
        assertEquals(12, editProjectPanel.getComponentCount());
    }

    /**
     * Tests setting project details in the EditProjectPanel.
     */
    @Test
    public void testSetProjectDetails() {
        int projectId = 1;
        int editorId = 123;
        String title = "New Title";
        double budget = 5000.0;
        String description = "New Description";
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");

        editProjectPanel.setProjectDetails(projectId, editorId, title, budget, description, tags);

        assertEquals(title, ((JTextField) editProjectPanel.getComponent(1)).getText());
        assertEquals(String.valueOf(budget), ((JTextField) editProjectPanel.getComponent(3)).getText());
        assertEquals(description, ((JTextArea) ((JScrollPane) editProjectPanel.getComponent(5)).getViewport().getView()).getText());
        assertEquals("tag1, tag2", ((JTextArea) ((JScrollPane) editProjectPanel.getComponent(7)).getViewport().getView()).getText());
    }

    /**
     * Tests saving a project in the EditProjectPanel.
     */
    @Test
    public void testSaveProject() {
        // Set initial project details
        int projectId = 1;
        int editorId = 123;
        String title = "New Title";
        double budget = 5000.0;
        String description = "New Description";
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");

        editProjectPanel.setProjectDetails(projectId, editorId, title, budget, description, tags);

        // Change some fields
        ((JTextField) editProjectPanel.getComponent(1)).setText("Updated Title");
        ((JTextField) editProjectPanel.getComponent(3)).setText("6000.0");
        ((JTextArea) ((JScrollPane) editProjectPanel.getComponent(5)).getViewport().getView()).setText("Updated Description");
        ((JTextArea) ((JScrollPane) editProjectPanel.getComponent(7)).getViewport().getView()).setText("tag1, tag2, tag3");

        // Simulate save button click
        ((JButton) editProjectPanel.getComponent(8)).doClick();

        // Verify that the editProjectController's editProject method was called
        verify(editProjectInteractor).editProject(any(EditProjectInputData.class));
    }

    /**
     * Tests property change handling in the EditProjectPanel.
     */
    @Test
    public void testPropertyChange() {
        // Test detailInit property change
        PropertyChangeEvent detailInitEvent = new PropertyChangeEvent(this, "detailInit", null, null);
        editProjectViewModel.setTitle("Test Title");
        editProjectViewModel.setBudget(1000.0);
        editProjectViewModel.setDescription("Test Description");
        HashSet<String> tags = new HashSet<>();
        tags.add("testTag");
        editProjectViewModel.setTags(tags);
        editProjectViewModel.setEditorId(1);
        editProjectViewModel.setProjectId(1);

        editProjectPanel.propertyChange(detailInitEvent);
        assertEquals("Test Title", ((JTextField) editProjectPanel.getComponent(1)).getText());

    }
}
