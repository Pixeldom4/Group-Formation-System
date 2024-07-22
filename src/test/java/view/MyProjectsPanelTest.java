package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.getloggedinuser.GetLoggedInUserController;
import usecase.getloggedinuser.GetLoggedInUserInteractor;
import usecase.getprojects.GetProjectsController;
import usecase.getprojects.GetProjectsInteractor;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the MyProjectsPanel class.
 */
public class MyProjectsPanelTest {

    private MyProjectsPanel myProjectsPanel;
    private MyProjectsPanelViewModel myProjectsPanelViewModel;
    private ViewManagerModel viewManagerModel;
    private GetLoggedInUserController getLoggedInUserController;
    private GetProjectsController getProjectsController;
    private EditProjectPanelViewModel editProjectPanelViewModel;
    private EditProjectPanel editProjectPanel;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        myProjectsPanelViewModel = new MyProjectsPanelViewModel();
        viewManagerModel = new ViewManagerModel();
        getLoggedInUserController = mock(GetLoggedInUserController.class);
        getProjectsController = mock(GetProjectsController.class);
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

    /**
     * Tests the initialization of the MyProjectsPanel.
     */
    @Test
    public void testInitialization() {
        assertNotNull(myProjectsPanel);
        assertEquals(2, myProjectsPanel.getComponentCount());
    }

    /**
     * Tests the action of the refresh button in the MyProjectsPanel.
     */
    @Test
    public void testRefreshButtonAction() {
        JButton refreshButton = (JButton) myProjectsPanel.getComponent(1);
        assertNotNull(refreshButton);

        // Simulate the getProjectsController updating the ViewModel with new data
        Object[][] projectData = {
                {1, "Project 1", "Description 1", 1000.0, new HashSet<String>()},
                {2, "Project 2", "Description 2", 2000.0, new HashSet<String>()}
        };
        myProjectsPanelViewModel.setData(projectData);

        // Simulate the getLoggedInUserController updating the ViewModel with logged in user
        myProjectsPanelViewModel.setLoggedInUser(1, "John", "Doe", "john.doe@example.com", 5000.0, new HashSet<>());

        refreshButton.doClick();
        verify(getProjectsController).getProjects(myProjectsPanelViewModel.getLoggedInUser().getUserId());

        JTable infoTable = (JTable) ((JScrollPane) myProjectsPanel.getComponent(0)).getViewport().getView();
        assertEquals(2, infoTable.getRowCount());
        assertEquals("Project 1", infoTable.getValueAt(0, 0));
        assertEquals("Description 1", infoTable.getValueAt(0, 1));
    }

    /**
     * Tests handling of data update property change in the MyProjectsPanel.
     */
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

    /**
     * Tests handling of login property change in the MyProjectsPanel.
     */
    @Test
    public void testPropertyChangeLogin() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "login", null, true);

        // Simulate the getLoggedInUserController updating the ViewModel with logged in user
        myProjectsPanelViewModel.setLoggedInUser(1, "John", "Doe", "john.doe@example.com", 5000.0, new HashSet<>());

        myProjectsPanel.propertyChange(event);
        verify(getLoggedInUserController).getLoggedInUser();


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

    /**
     * Tests handling of error property change in the MyProjectsPanel.
     */
    @Test
    public void testPropertyChangeError() {
        String errorMessage = "An error occurred";

        myProjectsPanelViewModel.setErrorMessage(errorMessage);

        // Check that the error message is displayed in a dialog
        // Assuming setErrorMessage updates a public errorMessage field for this test
        assertEquals(errorMessage, myProjectsPanelViewModel.getErrorMessage());
    }
}
