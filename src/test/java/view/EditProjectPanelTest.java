package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.editproject.EditProjectController;
import usecase.editproject.EditProjectInputBoundary;
import usecase.editproject.EditProjectInputData;
import usecase.getapplications.GetApplicationsController;
import usecase.deleteproject.DeleteProjectController;
import usecase.acceptapplication.AcceptApplicationController;
import usecase.rejectapplication.RejectApplicationController;
import viewmodel.DisplayProjectApplicationViewModel;
import viewmodel.EditProjectPanelViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the EditProjectPanel class.
 */
public class EditProjectPanelTest {

    private EditProjectPanel editProjectPanel;
    private EditProjectPanelViewModel editProjectViewModel;
    private EditProjectController editProjectController;
    private GetApplicationsController getApplicationsController;
    private DeleteProjectController deleteProjectController;
    private DisplayProjectApplicationViewModel displayProjectApplicationViewModel;
    private AcceptApplicationController acceptApplicationController;
    private RejectApplicationController rejectApplicationController;
    private EditProjectInputBoundary editProjectInteractor;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        editProjectViewModel = new EditProjectPanelViewModel();

        // Mock dependencies
        editProjectInteractor = mock(EditProjectInputBoundary.class);
        editProjectController = new EditProjectController(editProjectInteractor);
        getApplicationsController = mock(GetApplicationsController.class);
        deleteProjectController = mock(DeleteProjectController.class);
        displayProjectApplicationViewModel = mock(DisplayProjectApplicationViewModel.class);
        acceptApplicationController = mock(AcceptApplicationController.class);
        rejectApplicationController = mock(RejectApplicationController.class);

        editProjectPanel = new EditProjectPanel(
                editProjectViewModel,
                editProjectController,
                getApplicationsController,
                deleteProjectController,
                displayProjectApplicationViewModel,
                acceptApplicationController,
                rejectApplicationController
        );
    }

    /**
     * Tests the initialization of the EditProjectPanel.
     */
    @Test
    public void testInitialization() {
        assertNotNull(editProjectPanel);
        assertEquals(10, editProjectPanel.getComponentCount());
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
        assertEquals("tag1, tag2", ((JTextField) editProjectPanel.getComponent(7)).getText());
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
        ((JTextField) editProjectPanel.getComponent(7)).setText("tag1, tag2, tag3");

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

        // Test editSuccess property change
        PropertyChangeEvent editSuccessEvent = new PropertyChangeEvent(this, "editSuccess", null, true);
        editProjectPanel.propertyChange(editSuccessEvent);
        assertEquals("Test Title", ((JTextField) editProjectPanel.getComponent(1)).getText());
    }
}
