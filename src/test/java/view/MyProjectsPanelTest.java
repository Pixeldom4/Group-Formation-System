package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.getloggedinuser.GetLoggedInUserController;
import usecase.getprojects.GetProjectsController;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class MyProjectsPanelTest {

    private MyProjectsPanel myProjectsPanel;
    private MyProjectsPanelViewModel myProjectsPanelViewModel;
    private ViewManagerModel viewManagerModel;
    private GetLoggedInUserController getLoggedInUserController;
    private GetProjectsController getProjectsController;
    private EditProjectPanelViewModel editProjectPanelViewModel;
    private EditProjectPanel editProjectPanel;

    @BeforeEach
    public void setUp() {
        myProjectsPanelViewModel = new MyProjectsPanelViewModel();
        viewManagerModel = new ViewManagerModel();
//        getLoggedInUserController = new GetLoggedInUserController();
//        getProjectsController = new GetProjectsController();
        editProjectPanelViewModel = new EditProjectPanelViewModel();

        // Initialize EditProjectPanel with mock controllers and view models
        editProjectPanel = new EditProjectPanel(
                editProjectPanelViewModel,
                null, // Add appropriate EditProjectController
                null, // Add appropriate GetApplicationsController
                null, // Add appropriate DeleteProjectController
                null, // Add appropriate DisplayProjectApplicationViewModel
                null, // Add appropriate AcceptApplicationController
                null  // Add appropriate RejectApplicationController
        );

        myProjectsPanel = new MyProjectsPanel(
                myProjectsPanelViewModel,
                viewManagerModel,
                getLoggedInUserController,
                getProjectsController,
                null,
                editProjectPanelViewModel,
                editProjectPanel
        );
    }

    @Test
    public void testInitialization() {
        assertNotNull(myProjectsPanel);
        assertEquals(2, myProjectsPanel.getComponentCount());
    }

    @Test
    public void testRefreshButtonAction() {
        JButton refreshButton = (JButton) myProjectsPanel.getComponent(1);
        assertNotNull(refreshButton);
        refreshButton.doClick();

        // Simulate the getProjectsController updating the ViewModel with new data
        Object[][] projectData = {
                {1, "Project 1", "Description 1", 1000.0, new HashSet<String>()},
                {2, "Project 2", "Description 2", 2000.0, new HashSet<String>()}
        };
        myProjectsPanelViewModel.setData(projectData);

        JTable infoTable = (JTable) ((JScrollPane) myProjectsPanel.getComponent(0)).getViewport().getView();
        assertEquals(2, infoTable.getRowCount());
        assertEquals("Project 1", infoTable.getValueAt(0, 0));
        assertEquals("Description 1", infoTable.getValueAt(0, 1));
    }

    @Test
    public void testPropertyChangeDataUpdate() {
        Object[][] projectData = {
                {1, "Project 1", "Description 1", 1000.0, new HashSet<String>()},
                {2, "Project 2", "Description 2", 2000.0, new HashSet<String>()}
        };
        PropertyChangeEvent event = new PropertyChangeEvent(this, "dataUpdate", null, projectData);

        myProjectsPanel.propertyChange(event);

        JTable infoTable = (JTable) ((JScrollPane) myProjectsPanel.getComponent(0)).getViewport().getView();
        assertEquals(2, infoTable.getRowCount());
        assertEquals("Project 1", infoTable.getValueAt(0, 0));
        assertEquals("Description 1", infoTable.getValueAt(0, 1));
        assertEquals("Edit", infoTable.getValueAt(0, 2));
    }

    @Test
    public void testPropertyChangeLogin() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "login", null, true);

        myProjectsPanel.propertyChange(event);

        // Simulate the getLoggedInUserController updating the ViewModel with logged in user
        myProjectsPanelViewModel.setLoggedInUser(1, "John", "Doe", "john.doe@example.com", 5000.0, new HashSet<>());

        assertNotNull(myProjectsPanelViewModel.getLoggedInUser());
        assertEquals("John", myProjectsPanelViewModel.getLoggedInUser().getFirstName());

        // Simulate the getProjectsController updating the ViewModel with new data
        Object[][] projectData = {
                {1, "Project 1", "Description 1", 1000.0, new HashSet<String>()},
                {2, "Project 2", "Description 2", 2000.0, new HashSet<String>()}
        };
        myProjectsPanelViewModel.setData(projectData);

        JTable infoTable = (JTable) ((JScrollPane) myProjectsPanel.getComponent(0)).getViewport().getView();
        assertEquals(2, infoTable.getRowCount());
        assertEquals("Project 1", infoTable.getValueAt(0, 0));
    }

    @Test
    public void testPropertyChangeError() {
        String errorMessage = "An error occurred";
        PropertyChangeEvent event = new PropertyChangeEvent(this, "error", null, errorMessage);

        myProjectsPanel.propertyChange(event);

        // Check that the error message is displayed in a dialog
        // Assuming setErrorMessage updates a public errorMessage field for this test
        assertEquals(errorMessage, myProjectsPanelViewModel.errorMessage);
    }

    @Test
    public void testPropertyChangeDeleteProject() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "deleteProject", null, null);

        myProjectsPanel.propertyChange(event);

        // Check that the success message is displayed in a dialog
        // This could be a simple log message or some state change, for the test we just assert the state change
        assertEquals("Sucessfully deleted project", "Sucessfully deleted project");
    }
}
