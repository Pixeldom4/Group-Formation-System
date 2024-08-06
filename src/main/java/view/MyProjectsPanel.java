package view;

import usecase.getapplications.GetApplicationsController;
import usecase.getloggedinuser.GetLoggedInUserController;
import usecase.getprojects.GetProjectsController;
import usecase.getprojects.ProjectData;
import usecase.getusers.GetUsersController;
import usecase.getusers.UserData;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A panel for displaying and managing the user's projects.
 */
public class MyProjectsPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final GetProjectsController getProjectsController;
    private final MyProjectsPanelViewModel myProjectsPanelViewModel;
    private final ViewManagerModel viewManagerModel;
    private final EditProjectPanelViewModel editProjectPanelViewModel;
    private final EditProjectPanel editProjectPanel;
    private final GetLoggedInUserController getLoggedInUserController;
    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {200, 400, 100, 100, 50};
    private final String[] columnNames = {"Project ID", "Project Title", "Description", "Admin", "Edit"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);
    private final GetUsersController getUsersController;
    private JButton getUsersButton;
    private UsersPanel usersPanel;

    /**
     * Constructs a MyProjectsPanel.
     *
     * @param myProjectsPanelViewModel the view model for the user's projects
     * @param viewManagerModel the view manager model
     * @param getLoggedInUserController the controller for getting the logged-in user
     * @param getProjectsController the controller for getting projects
     * @param getApplicationsController the controller for getting applications
     * @param editProjectPanelViewModel the view model for editing a project
     * @param editProjectPanel the panel for editing a project
     * @param getUsersController the controller for getting users
     */
    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel,
                           ViewManagerModel viewManagerModel,
                           GetLoggedInUserController getLoggedInUserController,
                           GetProjectsController getProjectsController,
                           GetApplicationsController getApplicationsController,
                           EditProjectPanelViewModel editProjectPanelViewModel,
                           EditProjectPanel editProjectPanel,
                           GetUsersController getUsersController) {
        this.viewManagerModel = viewManagerModel;
        this.getLoggedInUserController = getLoggedInUserController;
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        this.getProjectsController = getProjectsController;
        this.editProjectPanelViewModel = editProjectPanelViewModel;
        this.editProjectPanel = editProjectPanel;
        this.getUsersController = getUsersController;

        myProjectsPanelViewModel.addPropertyChangeListener(this);
        editProjectPanelViewModel.addPropertyChangeListener(this);
        viewManagerModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(infoPanel);

        // Initialize and add the Get Users button
        getUsersButton = new JButton("Get Users");
        getUsersButton.addActionListener(this);
        this.add(getUsersButton);

        // Initialize UsersPanel
        usersPanel = new UsersPanel();

        // Add a selection listener to the table to update the selected project ID
        infoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = infoTable.getSelectedRow();
                if (selectedRow != -1) {
                    int projectId = (int) infoTable.getValueAt(selectedRow, 0);
                    myProjectsPanelViewModel.setSelectedProjectId(projectId);
                }
            }
        });
    }

    /**
     * Adds projects to the table.
     *
     * @param projectDataSet the data of the projects
     */
    private void addProjects(HashSet<ProjectData> projectDataSet) {
        ArrayList<ButtonAction> editButtonActions = new ArrayList<>();

        Object[][] info = new Object[projectDataSet.size()][5];
        int i = 0;
        for (ProjectData projectData : projectDataSet) {
            info[i][0] = projectData.getProjectId();
            info[i][1] = projectData.getProjectTitle();
            info[i][2] = projectData.getProjectDescription();
            info[i][3] = projectData.isProjectOwner() ? "Yes" : "No";
            info[i][4] = "Edit";
            int projectId = projectData.getProjectId();
            String projectTitle = projectData.getProjectTitle();
            String projectDescription = projectData.getProjectDescription();
            double projectBudget = projectData.getProjectBudget();
            HashSet<String> projectTags = projectData.getProjectTags();
            int editorId = myProjectsPanelViewModel.getLoggedInUser().getUserId();

            editButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    editProjectPanelViewModel.setProjectDetails(projectId, projectTitle, projectBudget,
                            projectDescription, projectTags, editorId);
                    editProjectPanelViewModel.initDetails();

                    // Display editProjectPanel in your application window
                    JFrame editFrame = new JFrame("Edit Project");
                    editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    editFrame.setSize(400, 300);
                    editFrame.add(editProjectPanel);

                    editFrame.setVisible(true);

                }
            });
            i++;
        }

        DefaultTableModel infoTableModel = new DefaultTableModel(info, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only the button column editable
                return column >= 4;
            }
        };
        infoTable.setModel(infoTableModel);

        ButtonColumn editColumn = new ButtonColumn(infoTable, 4);
        editColumn.setActions(editButtonActions);

        TableColumnModel columnModel = infoTable.getColumnModel();
        for (int j = 0; j < columnWidths.length; j++) {
            columnModel.getColumn(j).setPreferredWidth(columnWidths[j]);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("dataUpdate")) {
            HashSet<ProjectData> data = (HashSet<ProjectData>) evt.getNewValue();
            addProjects(data);
        }
        if (evt.getPropertyName().equals("login")) {
            getLoggedInUserController.getLoggedInUser();
            boolean login = (boolean) evt.getNewValue();
            if (login) {
                getProjectsController.getProjects(myProjectsPanelViewModel.getLoggedInUser().getUserId());
            }
        }
        if (evt.getPropertyName().equals("error")) {
            String errorMessage = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(this, errorMessage);
        }
        if (evt.getPropertyName().equals("deleteProject")) {
            JOptionPane.showMessageDialog(null, "Successfully deleted project");
        }
        if (evt.getPropertyName().equals("addProject") || evt.getPropertyName().equals("editSuccess")) {
            getProjectsController.getProjects(myProjectsPanelViewModel.getLoggedInUser().getUserId());
        }
        if (evt.getPropertyName().equals("usersDataUpdate")) {
            HashSet<UserData> usersData = (HashSet<UserData>) evt.getNewValue();
            usersPanel.displayUsers(usersData);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getUsersButton) {
            // Retrieve the selected project ID
            int projectId = myProjectsPanelViewModel.getSelectedProjectId();
            getUsersController.getUsers(projectId);

            // Display usersPanel in a new JFrame
            JFrame usersFrame = new JFrame("Users");
            usersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            usersFrame.setSize(800, 600);
            usersFrame.add(usersPanel);
            usersFrame.setVisible(true);
        }
    }

    public UsersPanel getUsersPanel() {
        return usersPanel;
    }
}
