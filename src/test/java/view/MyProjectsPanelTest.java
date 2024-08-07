package view;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageprojects.ManageProjectsController;
import usecase.manageprojects.getprojects.GetProjectsController;
import usecase.manageusers.ManageUsersController;
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
    private ManageProjectsController getProjectsController;
    private ManageUsersController getUsersController;
    private EditProjectPanelViewModel editProjectPanelViewModel;
    private EditProjectPanel editProjectPanel;
    private UsersPanel usersPanel;

    @BeforeEach
    public void setUp() {
        myProjectsPanelViewModel = mock(MyProjectsPanelViewModel.class);
        viewManagerModel = mock(ViewManagerModel.class);
        getLoggedInUserController = mock(GetLoggedInUserController.class);
        getProjectsController = mock(ManageProjectsController.class);
        getUsersController = mock(ManageUsersController.class);
        editProjectPanelViewModel = mock(EditProjectPanelViewModel.class);
        editProjectPanel = mock(EditProjectPanel.class);
        usersPanel = mock(UsersPanel.class);

        myProjectsPanel = new MyProjectsPanel(myProjectsPanelViewModel, viewManagerModel, getLoggedInUserController,
                getProjectsController, getUsersController, editProjectPanelViewModel, editProjectPanel, new UsersPanel());
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

        myProjectsPanel = new MyProjectsPanel(myProjectsPanelViewModel, viewManagerModel, getLoggedInUserController,
                                              getProjectsController, getUsersController, editProjectPanelViewModel, editProjectPanel, usersPanel);

        // Act
        myProjectsPanel.propertyChange(new PropertyChangeEvent(this, "usersDataUpdate", null, usersData));

        // Assert
        verify(usersPanel, times(1)).displayUsers(usersData);
    }
}
