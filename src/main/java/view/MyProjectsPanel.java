package view;

import api.EmbeddingAPIInterface;
import dataaccess.IProjectRepository;
import usecase.acceptapplication.AcceptApplicationController;
import usecase.deleteproject.DeleteProjectController;
import usecase.deleteproject.DeleteProjectInputData;
import usecase.deleteproject.DeleteProjectInteractor;
import usecase.deleteproject.DeleteProjectPresenter;
import usecase.editproject.EditProjectController;
import usecase.editproject.EditProjectInteractor;
import usecase.editproject.EditProjectPresenter;
import usecase.getapplications.GetApplicationsController;
import usecase.getprojects.GetProjectsController;
import usecase.getprojects.GetProjectsInputData;
import usecase.getprojects.GetProjectsInteractor;
import usecase.getprojects.GetProjectsPresenter;
import usecase.rejectapplication.RejectApplicationController;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.DisplayProjectApplicationViewModel;
import viewmodel.MyProjectsPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;

public class MyProjectsPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final GetProjectsController getProjectsController;
    private final DeleteProjectController deleteProjectController;

    private final DisplayProjectApplicationViewModel displayProjectApplicationViewModel;
    private final GetApplicationsController getApplicationsController;
    private final AcceptApplicationController acceptApplicationController;
    private final RejectApplicationController rejectApplicationController;

    private final MyProjectsPanelViewModel myProjectsPanelViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {200, 400, 100, 100, 100};
    private final String[] columnNames = {"Project Title", "Description", "Edit", "Applications", "Delete"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);

    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel,
                           ViewManagerModel viewManagerModel,
                           GetProjectsController getProjectsController,
                           DeleteProjectController deleteProjectController,
                           DisplayProjectApplicationViewModel displayProjectApplicationViewModel,
                           GetApplicationsController getApplicationsController,
                           AcceptApplicationController acceptApplicationController,
                           RejectApplicationController rejectApplicationController) {
        this.viewManagerModel = viewManagerModel;
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        this.getProjectsController = getProjectsController;
        this.deleteProjectController = deleteProjectController;

        this.displayProjectApplicationViewModel = displayProjectApplicationViewModel;
        this.getApplicationsController = getApplicationsController;
        this.acceptApplicationController = acceptApplicationController;
        this.rejectApplicationController = rejectApplicationController;

        myProjectsPanelViewModel.addPropertyChangeListener(this);
        viewManagerModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(infoPanel);

        JButton refreshButton = new JButton("Refresh");
        this.add(refreshButton);

        refreshButton.addActionListener(e -> {
            getProjectsController.getProjects();
        });
    }

    private void addProjects(Object[][] projectData){
        ArrayList<ButtonAction> editButtonActions = new ArrayList<>();
        ArrayList<ButtonAction> applicationButtonActions = new ArrayList<>();
        ArrayList<ButtonAction> deleteButtonActions = new ArrayList<>();

        Object[][] info = new Object[projectData.length][5];
        for (int i = 0; i < projectData.length; i++) {
            info[i][0] = projectData[i][1];
            info[i][1] = projectData[i][2];
            info[i][2] = "Edit";
            info[i][3] = "View Applications";
            info[i][4] = "Delete";
            int finalI = i;
            editButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    int projectId = (int) projectData[finalI][0];
                    String projectTitle = (String) projectData[finalI][1];
                    String projectDescription = (String) projectData[finalI][2];
                    double projectBudget = (double) projectData[finalI][3];
                    HashSet<String> projectTags = (HashSet<String>) projectData[finalI][4];
                    int editorId = viewManagerModel.getCurrentUserId();

                    EditProjectPanelViewModel viewModel = new EditProjectPanelViewModel(projectId, projectTitle, projectBudget, projectDescription, projectTags);
                    EditProjectController controller = new EditProjectController(new EditProjectInteractor(projectRepository, new EditProjectPresenter(viewModel), embeddingAPI));
                    EditProjectPanel editProjectPanel = new EditProjectPanel(viewModel, controller);

                    // Display editProjectPanel in your application window
                    JFrame editFrame = new JFrame("Edit Project");
                    editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    editFrame.setSize(400, 300);
                    editFrame.add(editProjectPanel);
                    editFrame.setVisible(true);
                }
            });
            applicationButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    new DisplayProjectApplicationView((int)projectData[finalI][0],
                                                      displayProjectApplicationViewModel,
                                                      getApplicationsController,
                                                      acceptApplicationController,
                                                      rejectApplicationController);
                }
            });
            deleteButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    int dialogResult = JOptionPane.showConfirmDialog (null,
                                                                      "Are you sure you would like to delete " + projectData[finalI][0] + "?",
                                                                      "Warning",
                                                                      JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        deleteProjectController.deleteProject((int) projectData[finalI][0]);
                    }
                }
            });
        }

        DefaultTableModel infoTableModel = new DefaultTableModel(info, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only the button column editable
                return column >= 2;
            }
        };
        infoTable.setModel(infoTableModel);

        ButtonColumn editColumn = new ButtonColumn(infoTable, 2);
        editColumn.setActions(editButtonActions);

        ButtonColumn applicationColumn = new ButtonColumn(infoTable, 3);
        applicationColumn.setActions(applicationButtonActions);

        ButtonColumn deleteColumn = new ButtonColumn(infoTable, 4);
        deleteColumn.setActions(deleteButtonActions);

        TableColumnModel columnModel = infoTable.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("dataUpdate")){
            Object[][] data = (Object[][]) evt.getNewValue();
            addProjects(data);
        }
        if (evt.getPropertyName().equals("login")) {
            boolean login = (boolean) evt.getNewValue();
            if (login) {
                getProjectsController.getProjects();
            }
        }
        if (evt.getPropertyName().equals("error")) {
            String errorMessage = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(this, errorMessage);
        }
        if (evt.getPropertyName().equals("deleteProject")) {
            JOptionPane.showMessageDialog(null, "Sucessfully deleted project");
        }
    }
}
