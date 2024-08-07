package view;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageprojects.getprojects.GetProjectsController;
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import usecase.manageusers.getusers.GetUsersController;
import usecase.manageusers.getusers.UserData;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;
import viewmodel.ViewManagerModel;
import entities.User;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

class MyProjectsPanelTest {

    private MyProjectsPanel myProjectsPanel;
    private MyProjectsPanelViewModel myProjectsPanelViewModel;
    private ViewManagerModel viewManagerModel;
    private GetLoggedInUserController getLoggedInUserController;
    private GetProjectsController getProjectsController;
    private GetUsersController getUsersController;
    private EditProjectPanelViewModel editProjectPanelViewModel;
    private EditProjectPanel editProjectPanel;

    @BeforeEach
    public void setUp() {
        myProjectsPanelViewModel = mock(MyProjectsPanelViewModel.class);
        viewManagerModel = mock(ViewManagerModel.class);
        getLoggedInUserController = mock(GetLoggedInUserController.class);
        getProjectsController = mock(GetProjectsController.class);
        getUsersController = mock(GetUsersController.class);
        editProjectPanelViewModel = mock(EditProjectPanelViewModel.class);
        editProjectPanel = mock(EditProjectPanel.class);

        myProjectsPanel = new MyProjectsPanel(myProjectsPanelViewModel, viewManagerModel, getLoggedInUserController,
                getProjectsController, null, editProjectPanelViewModel, editProjectPanel, getUsersController);
    }

    @Test
    void actionPerformed_GetUsersButtonClicked_ExecutesControllerAndChangesActiveView() {
        // Arrange
        JButton getUsersButton = (JButton) myProjectsPanel.getComponent(1);
        when(myProjectsPanelViewModel.getSelectedProjectId()).thenReturn(1);

        // Act
        getUsersButton.doClick();

        // Assert
        verify(getUsersController, times(1)).getUsers(1);
    }

    @Test
    void propertyChange_ErrorPropertySet_UpdatesErrorLabel() {
        // Act
        myProjectsPanel.propertyChange(new PropertyChangeEvent(this, "error", null, "An error occurred"));

        // Assert
        JOptionPane.showMessageDialog(myProjectsPanel, "An error occurred");
    }

    @Test
    void propertyChange_DeleteProject_ShowsSuccessMessage() {
        // Act
        myProjectsPanel.propertyChange(new PropertyChangeEvent(this, "deleteProject", null, null));

        // Assert
        JOptionPane.showMessageDialog(null, "Successfully deleted project");
    }

    @Test
    void propertyChange_AddOrEditProject_RequestsProjects() {
        // Arrange
        User loggedInUser = new User(1, "First", "Last", "email@test.com", new HashSet<>(), 50000);
        when(myProjectsPanelViewModel.getLoggedInUser()).thenReturn(loggedInUser);

        // Act
        myProjectsPanel.propertyChange(new PropertyChangeEvent(this, "addProject", null, null));
        myProjectsPanel.propertyChange(new PropertyChangeEvent(this, "editSuccess", null, null));

        // Assert
        verify(getProjectsController, times(2)).getProjects(loggedInUser.getUserId());
    }

    @Test
    void propertyChange_UsersDataUpdate_DisplaysUsers() {
        // Arrange
        HashSet<UserData> usersData = new HashSet<>();
        UserData user1 = new UserData(1, "First1", "Last1", "email1@test.com", new HashSet<>(), 50000, true);
        UserData user2 = new UserData(2, "First2", "Last2", "email2@test.com", new HashSet<>(), 60000, false);
        usersData.add(user1);
        usersData.add(user2);

        UsersPanel usersPanel = mock(UsersPanel.class);
        when(myProjectsPanel.getUsersPanel()).thenReturn(usersPanel);

        // Act
        myProjectsPanel.propertyChange(new PropertyChangeEvent(this, "usersDataUpdate", null, usersData));

        // Assert
        verify(usersPanel, times(1)).displayUsers(usersData);
    }
}
