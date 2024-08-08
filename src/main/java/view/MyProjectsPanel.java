package view;

import config.HoverVoiceServiceConfig;
import config.PlayVoiceServiceConfig;
import usecase.manageprojects.ManageProjectsController;
import usecase.manageprojects.getprojects.ProjectData;
import usecase.manageusers.ManageUsersController;
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import usecase.manageusers.getusers.UserData;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import view.services.SafeCastCollectionService;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * A panel for displaying and managing the user's projects.
 */
@SuppressWarnings("FieldCanBeLocal")
public class MyProjectsPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final MyProjectsPanelViewModel myProjectsPanelViewModel;
    private final ViewManagerModel viewManagerModel;
    private final EditProjectPanelViewModel editProjectPanelViewModel;
    private final EditProjectPanel editProjectPanel;
    private final GetLoggedInUserController getLoggedInUserController;
    private final ManageProjectsController manageProjectsController;
    private final ManageUsersController manageUsersController;
    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {0, 100, 400, 100, 50};
    private final String[] columnNames = {"id", "Project Title", "Description", "Admin", "Details"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);
    private final JButton getUsersButton;
    private final UsersPanel usersPanel;

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

    /**
     * Constructs a MyProjectsPanel.
     *
     * @param myProjectsPanelViewModel the view model for the user's projects
     * @param viewManagerModel the view manager model
     * @param getLoggedInUserController the controller for getting the logged-in user
     * @param manageProjectsController the controller for getting projects
     * @param manageUsersController the controller for getting users
     * @param editProjectPanelViewModel the view model for editing a project
     * @param editProjectPanel the panel for editing a project
     */
    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel,
                           ViewManagerModel viewManagerModel,
                           GetLoggedInUserController getLoggedInUserController,
                           ManageProjectsController manageProjectsController,
                           ManageUsersController manageUsersController,
                           EditProjectPanelViewModel editProjectPanelViewModel,
                           EditProjectPanel editProjectPanel,
                           UsersPanel usersPanel) {
        this.viewManagerModel = viewManagerModel;
        this.getLoggedInUserController = getLoggedInUserController;
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        this.editProjectPanelViewModel = editProjectPanelViewModel;
        this.editProjectPanel = editProjectPanel;
        this.manageProjectsController = manageProjectsController;
        this.manageUsersController = manageUsersController;

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        this.playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

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
        this.usersPanel = usersPanel;

        // Add a selection listener to the table to update the selected project ID
        infoTable.getSelectionModel().addListSelectionListener(_ -> {
            int selectedRow = infoTable.getSelectedRow();
            if (selectedRow != -1) {
                int projectId = (int) infoTable.getValueAt(selectedRow, 0);
                myProjectsPanelViewModel.setSelectedProjectId(projectId);
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
        Map<Point, String> hoverSpeechMap = new HashMap<>();

        int i = 0;
        for (ProjectData projectData : projectDataSet) {
            info[i][0] = projectData.getProjectId();
            info[i][1] = projectData.getProjectTitle();
            info[i][2] = projectData.getProjectDescription();
            info[i][3] = projectData.isProjectOwner() ? "Yes" : "No";
            info[i][4] = projectData.isProjectOwner() ? "Edit": "View";

            hoverSpeechMap.put(new Point(i, 1), "Project title: " + projectData.getProjectTitle());
            hoverSpeechMap.put(new Point(i, 2), "Project description: " + projectData.getProjectDescription());
            hoverSpeechMap.put(new Point(i, 3), projectData.isProjectOwner() ? "Is admin" : "Not admin");
            hoverSpeechMap.put(new Point(i, 4), projectData.isProjectOwner() ? "Press to edit project" : "Press to view details");

            int projectId = projectData.getProjectId();
            String projectTitle = projectData.getProjectTitle();
            String projectDescription = projectData.getProjectDescription();
            double projectBudget = projectData.getProjectBudget();
            HashSet<String> projectTags = projectData.getProjectTags();
            int editorId = myProjectsPanelViewModel.getLoggedInUser().getUserId();

            editButtonActions.add(() -> {
                editProjectPanelViewModel.setProjectDetails(projectId, projectTitle, projectBudget,
                        projectDescription, projectTags, editorId, projectData.isProjectOwner());
                editProjectPanelViewModel.initDetails();

                // Display editProjectPanel in your application window
                JFrame editFrame = new JFrame("Edit Project");
                editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editFrame.setSize(400, 400);
                editFrame.add(editProjectPanel);
                editFrame.setVisible(true);

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

        hoverVoiceService.addTableHoverVoice(infoTable, hoverSpeechMap);

        ButtonColumn editColumn = new ButtonColumn(infoTable, 4);
        editColumn.setActions(editButtonActions);

        TableColumnModel columnModel = infoTable.getColumnModel();
        for (int j = 0; j < columnWidths.length; j++) {
            columnModel.getColumn(j).setPreferredWidth(columnWidths[j]);
        }
        // Set table width to 0 to hide the column. Column existence is necessary for other methods
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("dataUpdate")) {
            HashSet<ProjectData> data = SafeCastCollectionService.convertToCollection(evt.getNewValue(), ProjectData.class, HashSet::new);
            addProjects(data);
        }
        if (evt.getPropertyName().equals("login")) {
            getLoggedInUserController.getLoggedInUser();
            boolean login = (boolean) evt.getNewValue();
            if (login) {
                manageProjectsController.getProjects(myProjectsPanelViewModel.getLoggedInUser().getUserId());
            }
        }
        if (evt.getPropertyName().equals("error")) {
            String errorMessage = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(this, errorMessage);
        }
        if (evt.getPropertyName().equals("deleteProject")) {
            playVoiceService.playVoice("Successfully deleted project");
            manageProjectsController.getProjects(myProjectsPanelViewModel.getLoggedInUser().getUserId());
            JOptionPane.showMessageDialog(null, "Successfully deleted project");
        }
        if (evt.getPropertyName().equals("addProject") || evt.getPropertyName().equals("editSuccess")) {
            manageProjectsController.getProjects(myProjectsPanelViewModel.getLoggedInUser().getUserId());
        }
        if (evt.getPropertyName().equals("usersDataUpdate")) {
            HashSet<UserData> usersData = SafeCastCollectionService.convertToCollection(evt.getNewValue(),
                                                                                        UserData.class, HashSet::new);
            usersPanel.displayUsers(usersData);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getUsersButton) {
            // Retrieve the selected project ID
            int projectId = myProjectsPanelViewModel.getSelectedProjectId();
            manageUsersController.getUsers(projectId);

            // Display usersPanel in a new JFrame. Note that table size is large to accommodate large names/ groups of ppl
            JFrame usersFrame = new JFrame("Users");
            usersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            usersFrame.setSize(800, 300);
            usersFrame.add(usersPanel);
            usersFrame.setVisible(true);
        }
    }

}
