package view;

import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import usecase.manageprojects.getprojects.ProjectData;
import usecase.manageprojects.ManageProjectsController;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import view.services.SafeCastCollectionService;
import config.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import config.PlayVoiceServiceConfig;
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

    private final ManageProjectsController getProjectsController;
    private final MyProjectsPanelViewModel myProjectsPanelViewModel;
    private final ViewManagerModel viewManagerModel;
    private final EditProjectPanelViewModel editProjectPanelViewModel;
    private final EditProjectPanel editProjectPanel;
    private final GetLoggedInUserController getLoggedInUserController;
    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {200, 400, 100, 100};
    private final String[] columnNames = {"Project Title", "Description", "Admin",  "Edit"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

    /**
     * Constructs a MyProjectsPanel.
     *
     * @param myProjectsPanelViewModel the view model for the user's projects
     * @param viewManagerModel the view manager model
     * @param getLoggedInUserController the controller for getting the logged-in user
     * @param getProjectsController the controller for getting projects
     * @param editProjectPanelViewModel the view model for editing a project
     * @param editProjectPanel the panel for editing a project
     */
    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel,
                           ViewManagerModel viewManagerModel,
                           GetLoggedInUserController getLoggedInUserController,
                           ManageProjectsController getProjectsController,
                           EditProjectPanelViewModel editProjectPanelViewModel,
                           EditProjectPanel editProjectPanel) {
        this.viewManagerModel = viewManagerModel;
        this.getLoggedInUserController = getLoggedInUserController;
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        this.getProjectsController = getProjectsController;
        this.editProjectPanelViewModel = editProjectPanelViewModel;
        this.editProjectPanel = editProjectPanel;

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        this.playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

        myProjectsPanelViewModel.addPropertyChangeListener(this);
        editProjectPanelViewModel.addPropertyChangeListener(this);
        viewManagerModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(infoPanel);

    }

    /**
     * Adds projects to the table.
     *
     * @param projectDataSet the data of the projects
     */
    private void addProjects(HashSet<ProjectData> projectDataSet) {
        ArrayList<ButtonAction> editButtonActions = new ArrayList<>();

        Object[][] info = new Object[projectDataSet.size()][4];
        Map<Point, String> hoverSpeechMap = new HashMap<>();

        int i = 0;
        for (ProjectData projectData : projectDataSet) {
            info[i][0] = projectData.getProjectTitle();
            info[i][1] = projectData.getProjectDescription();
            info[i][2] = projectData.isProjectOwner() ? "Yes" : "No";
            info[i][3] = "Edit";

            hoverSpeechMap.put(new Point(i, 0), "Project title: " + projectData.getProjectTitle());
            hoverSpeechMap.put(new Point(i, 1), "Project description: " + projectData.getProjectDescription());
            hoverSpeechMap.put(new Point(i, 2), projectData.isProjectOwner() ? "Is admin" : "Not admin");
            hoverSpeechMap.put(new Point(i, 3), "Press to edit project");

            int projectId = projectData.getProjectId();
            String projectTitle = projectData.getProjectTitle();
            String projectDescription = projectData.getProjectDescription();
            double projectBudget = projectData.getProjectBudget();
            HashSet<String> projectTags = projectData.getProjectTags();
            int editorId = myProjectsPanelViewModel.getLoggedInUser().getUserId();

            editButtonActions.add(() -> {
                editProjectPanelViewModel.setProjectDetails(projectId, projectTitle, projectBudget,
                        projectDescription, projectTags, editorId);
                editProjectPanelViewModel.initDetails();

                // Display editProjectPanel in your application window
                JFrame editFrame = new JFrame("Edit Project");
                editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editFrame.setSize(400, 300);
                editFrame.add(editProjectPanel);

                editFrame.setVisible(true);

            });
            i++;
        }

        DefaultTableModel infoTableModel = new DefaultTableModel(info, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only the button column editable
                return column >= 3;
            }
        };
        infoTable.setModel(infoTableModel);

        hoverVoiceService.addTableHoverVoice(infoTable, hoverSpeechMap);

        ButtonColumn editColumn = new ButtonColumn(infoTable, 3);
        editColumn.setActions(editButtonActions);

        TableColumnModel columnModel = infoTable.getColumnModel();
        for (int j = 0; j < columnWidths.length; j++) {
            columnModel.getColumn(j).setPreferredWidth(columnWidths[j]);
        }
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
                getProjectsController.getProjects(myProjectsPanelViewModel.getLoggedInUser().getUserId());
            }
        }
        if (evt.getPropertyName().equals("error")) {
            String errorMessage = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(this, errorMessage);
        }
        if (evt.getPropertyName().equals("deleteProject")) {
            playVoiceService.playVoice("Successfully deleted project");
            JOptionPane.showMessageDialog(null, "Successfully deleted project");
        }
        if (evt.getPropertyName().equals("addProject") || evt.getPropertyName().equals("editSuccess")) {
            getProjectsController.getProjects(myProjectsPanelViewModel.getLoggedInUser().getUserId());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // No implementation needed
    }
}
