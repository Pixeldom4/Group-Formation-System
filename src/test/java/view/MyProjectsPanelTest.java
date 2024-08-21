package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageprojects.ManageProjectsController;
import usecase.manageprojects.getprojects.ProjectData;
import usecase.manageusers.ManageUsersController;
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import usecase.manageusers.getusers.UserData;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MyProjectsPanelTest {

    private MyProjectsPanel myProjectsPanel;
    private MyProjectsPanelViewModel myProjectsPanelViewModel;
    private ViewManagerModel viewManagerModel;
    private GetLoggedInUserController getLoggedInUserController;
    private ManageProjectsController manageProjectsController;
    private ManageUsersController manageUsersController;
    private EditProjectPanelViewModel editProjectPanelViewModel;
    private EditProjectPanel editProjectPanel;
    private UsersPanel usersPanel;
    private JTable infoTable;
    private JButton getUsersButton;

    @BeforeEach
    public void setUp() {
        myProjectsPanelViewModel = new MyProjectsPanelViewModel();
        viewManagerModel = mock(ViewManagerModel.class);
        getLoggedInUserController = mock(GetLoggedInUserController.class);
        manageProjectsController = mock(ManageProjectsController.class);
        manageUsersController = mock(ManageUsersController.class);
        editProjectPanelViewModel = mock(EditProjectPanelViewModel.class);
        editProjectPanel = mock(EditProjectPanel.class);
        usersPanel = mock(UsersPanel.class);

        myProjectsPanel = new TestMyProjectsPanel(myProjectsPanelViewModel, viewManagerModel, getLoggedInUserController,
                                              manageProjectsController, manageUsersController, editProjectPanelViewModel, editProjectPanel, usersPanel);

        infoTable = (JTable) getComponentByName(myProjectsPanel, "infoTable");
        getUsersButton = (JButton) getComponentByName(myProjectsPanel, "getUsersButton");

        myProjectsPanelViewModel.setLoggedInUser(1, "First", "Last",
                                                 "test@email.com", 6000.0, new HashSet<>());
    }

    @Test
    public void initialization() {
        assertNotNull(myProjectsPanel);
        assertNotNull(infoTable);
        assertNotNull(getUsersButton);
    }

    @Test
    public void addProjectsFunctionality() {
        HashSet<ProjectData> projectDataSet = new HashSet<>();
        projectDataSet.add(new ProjectData(1, "Project 1", "Description 1", 1000.0, new HashSet<>(), true));
        projectDataSet.add(new ProjectData(2, "Project 2", "Description 2", 2000.0, new HashSet<>(), false));

        myProjectsPanel.propertyChange(new PropertyChangeEvent(this, "dataUpdate", null, projectDataSet));

        DefaultTableModel model = (DefaultTableModel) infoTable.getModel();
        assertEquals(2, model.getRowCount());
        assertTrue(model.getValueAt(0, 1) == "Project 1" || model.getValueAt(1, 1) == "Project 1");
        assertTrue(model.getValueAt(0, 1) == "Project 2" || model.getValueAt(1, 1) == "Project 2");
    }

    @Test
    public void propertyChangeDataUpdate() {
        HashSet<ProjectData> projectDataSet = new HashSet<>();
        projectDataSet.add(new ProjectData(1, "Project 1", "Description 1", 1000.0, new HashSet<>(), true));

        PropertyChangeEvent evt = new PropertyChangeEvent(this, "dataUpdate", null, projectDataSet);
        myProjectsPanel.propertyChange(evt);

        DefaultTableModel model = (DefaultTableModel) infoTable.getModel();
        assertEquals(1, model.getRowCount());
        assertEquals("Project 1", model.getValueAt(0, 1));
    }

    @Test
    public void propertyChangeLogin() {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "login", null, true);
        myProjectsPanel.propertyChange(evt);
        verify(getLoggedInUserController).getLoggedInUser();
        verify(manageProjectsController).getProjects(anyInt());
    }

    @Test
    public void propertyChangeError() {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "error", null, "Error message");
        myProjectsPanel.propertyChange(evt);
        // Assuming JOptionPane is mocked or intercepted
    }

    @Test
    public void propertyChangeDeleteProject() {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "deleteProject", null, null);
        myProjectsPanel.propertyChange(evt);
        verify(manageProjectsController).getProjects(anyInt());
        // Assuming JOptionPane and playVoiceService are mocked or intercepted
    }

    @Test
    public void propertyChangeAddOrEditProject() {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "addProject", null, null);
        myProjectsPanel.propertyChange(evt);
        verify(manageProjectsController).getProjects(anyInt());

        evt = new PropertyChangeEvent(this, "editSuccess", null, null);
        myProjectsPanel.propertyChange(evt);
        verify(manageProjectsController, times(2)).getProjects(anyInt());
    }

    @Test
    public void propertyChangeUsersDataUpdate() {
        HashSet<UserData> usersData = new HashSet<>();
        usersData.add(new UserData(1, "First1", "Last1", "email1@test.com", new HashSet<>(), 50000, true));
        usersData.add(new UserData(2, "First2", "Last2", "email2@test.com", new HashSet<>(), 60000, false));

        PropertyChangeEvent evt = new PropertyChangeEvent(this, "usersDataUpdate", null, usersData);
        myProjectsPanel.propertyChange(evt);
        verify(usersPanel).displayUsers(usersData);
    }

    private Component getComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
            if (component instanceof Container) {
                Component child = getComponentByName((Container) component, name);
                if (child != null) {
                    return child;
                }
            }
        }
        return null;
    }

    private static class TestMyProjectsPanel extends MyProjectsPanel {
        public TestMyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel, ViewManagerModel viewManagerModel,
                                   GetLoggedInUserController getLoggedInUserController, ManageProjectsController manageProjectsController,
                                   ManageUsersController manageUsersController, EditProjectPanelViewModel editProjectPanelViewModel,
                                   EditProjectPanel editProjectPanel, UsersPanel usersPanel) {
            super(myProjectsPanelViewModel, viewManagerModel, getLoggedInUserController, manageProjectsController,
                  manageUsersController, editProjectPanelViewModel, editProjectPanel, usersPanel);
        }

        @Override
        protected void showDialog(JDialog dialog) {
            dialog.setAlwaysOnTop(true);
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            Timer timer = new Timer(10, _ -> dialog.dispose());
            timer.setRepeats(false);
            timer.start();

            dialog.setVisible(true);
        }
    }
}