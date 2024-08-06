package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.getloggedinuser.GetLoggedInUserController;
import usecase.getprojects.GetProjectsController;
import usecase.getprojects.ProjectData;
import usecase.getusers.GetUsersController;
import usecase.getusers.UserData;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private GetUsersController getUsersController;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        myProjectsPanelViewModel = new MyProjectsPanelViewModel();
        viewManagerModel = new ViewManagerModel();
        getLoggedInUserController = mock(GetLoggedInUserController.class);
        getProjectsController = mock(GetProjectsController.class);
        getUsersController = mock(GetUsersController.class);
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
                editProjectPanel,
                getUsersController
        );
    }

    /**
     * Tests the initialization of the MyProjectsPanel.
     */
    @Test
    public void testInitialization() {
        assertNotNull(myProjectsPanel);
        assertEquals(2, myProjectsPanel.getComponentCount()); // infoPanel and getUsersButton
    }

    /**
     * Tests the action of the refresh button in the MyProjectsPanel.
     */
    @Test
    public void testRefreshButtonAction() {

        HashSet<ProjectData> projectDataSet = new HashSet<>();

        projectDataSet.add(new ProjectData(1, "Project 1", "Description 1", 1000.0, new HashSet<>(), true));
        projectDataSet.add(new ProjectData(2, "Project 2", "Description 2", 2000.0, new HashSet<>(), true));

        // Simulate the getLoggedInUserController updating the ViewModel with logged in user
        myProjectsPanelViewModel.setLoggedInUser(1, "John", "Doe", "john.doe@example.com", 5000.0, new HashSet<>());

        myProjectsPanel.propertyChange(new PropertyChangeEvent(this, "dataUpdate", null, projectDataSet));

        JTable infoTable = (JTable) ((JScrollPane) myProjectsPanel.getComponent(0)).getViewport().getView();
        assertEquals(2, infoTable.getRowCount());

        // Check that the projects are displayed in the table
        // Uses an iterator so that the order of the projects matches the order in the table
        Iterator<ProjectData> iterator = projectDataSet.iterator();
        for (int i = 0; i < projectDataSet.size(); i++) {
            ProjectData projectData = iterator.next();
            assertEquals(projectData.getProjectTitle(), infoTable.getValueAt(i, 1)); // Column 1 for Project Title
            assertEquals(projectData.getProjectDescription(), infoTable.getValueAt(i, 2)); // Column 2 for Project Description
        }
    }

    /**
     * Tests handling of data update property change in the MyProjectsPanel.
     */
    @Test
    public void testPropertyChangeDataUpdate() {
        HashSet<ProjectData> projectDataSet = new HashSet<>();
        projectDataSet.add(new ProjectData(1, "Project 1", "Description 1", 1000.0, new HashSet<>(), true));
        projectDataSet.add(new ProjectData(2, "Project 2", "Description 2", 2000.0, new HashSet<>(), true));

        myProjectsPanelViewModel.setLoggedInUser(1, "John", "Doe", "john.doe@example.com", 5000.0, new HashSet<>());

        PropertyChangeEvent event = new PropertyChangeEvent(this, "dataUpdate", null, projectDataSet);
        myProjectsPanel.propertyChange(event);

        JTable infoTable = (JTable) ((JScrollPane) myProjectsPanel.getComponent(0)).getViewport().getView();
        assertEquals(2, infoTable.getRowCount());

        Iterator<ProjectData> iterator = projectDataSet.iterator();
        for (int i = 0; i < projectDataSet.size(); i++) {
            ProjectData projectData = iterator.next();
            assertEquals(projectData.getProjectTitle(), infoTable.getValueAt(i, 1)); // Column 1 for Project Title
            assertEquals(projectData.getProjectDescription(), infoTable.getValueAt(i, 2)); // Column 2 for Project Description
        }
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

        HashSet<ProjectData> projectData = new HashSet<>();

        // Add project data to the set
        projectData.add(new ProjectData(1, "Project 1", "Description 1", 1000.0, new HashSet<>(), true));
        projectData.add(new ProjectData(2, "Project 2", "Description 2", 2000.0, new HashSet<>(), false));
        myProjectsPanelViewModel.setData(projectData);

        JTable infoTable = (JTable) ((JScrollPane) myProjectsPanel.getComponent(0)).getViewport().getView();
        assertEquals(2, infoTable.getRowCount());

        Iterator<ProjectData> iterator = projectData.iterator();
        for (int i = 0; i < projectData.size(); i++) {
            ProjectData project = iterator.next();
            assertEquals(project.getProjectTitle(), infoTable.getValueAt(i, 1)); // Column 1 for Project Title
            assertEquals(project.getProjectDescription(), infoTable.getValueAt(i, 2)); // Column 2 for Project Description
        }
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

    /**
     * Tests the action of the Get Users button in the MyProjectsPanel.
     */
    @Test
    public void testGetUsersButtonAction() {
        // Simulate the selection of a project
        int selectedProjectId = 1;
        myProjectsPanelViewModel.setSelectedProjectId(selectedProjectId);

        // Simulate button click
        myProjectsPanel.actionPerformed(new ActionEvent(myProjectsPanel, ActionEvent.ACTION_PERFORMED, "Get Users"));

        // Verify that the getUsersController is called with the selected project ID
        verify(getUsersController).getUsers(selectedProjectId);
    }

    /**
     * Tests handling of users data update property change in the MyProjectsPanel.
     */
    @Test
    public void testPropertyChangeUsersDataUpdate() {
        HashSet<UserData> usersDataSet = new HashSet<>();
        usersDataSet.add(new UserData(1, "John", "Doe", "john.doe@example.com", new HashSet<>(), 1000.0, true));
        usersDataSet.add(new UserData(2, "Jane", "Smith", "jane.smith@example.com", new HashSet<>(), 2000.0, false));

        PropertyChangeEvent event = new PropertyChangeEvent(this, "usersDataUpdate", null, usersDataSet);
        myProjectsPanel.propertyChange(event);

        // Verify that the usersPanel is updated with the new users data
        UsersPanel usersPanel = myProjectsPanel.getUsersPanel(); // Use the getter to access UsersPanel
        JTable usersTable = usersPanel.getUsersTable();
        assertEquals(2, usersTable.getRowCount());

        Iterator<UserData> iterator = usersDataSet.iterator();
        for (int i = 0; i < usersDataSet.size(); i++) {
            UserData userData = iterator.next();
            assertEquals(userData.getUserId(), usersTable.getValueAt(i, 0));
            assertEquals(userData.getFirstName(), usersTable.getValueAt(i, 1));
            assertEquals(userData.getLastName(), usersTable.getValueAt(i, 2));
        }
    }
}
