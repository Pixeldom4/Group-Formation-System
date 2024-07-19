package view;

import usecase.getloggedinuser.GetLoggedInUserController;
import usecase.getprojects.GetProjectsController;
import usecase.getprojects.GetProjectsInputData;
import usecase.getprojects.GetProjectsInteractor;
import usecase.getprojects.GetProjectsPresenter;
import usecase.getloggedinuser.GetLoggedInUserPresenter;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import viewmodel.MyProjectsPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MyProjectsPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final MyProjectsPanelViewModel myProjectsPanelViewModel;
    private final GetProjectsController getProjectsController;
    private final GetProjectsPresenter getProjectsPresenter;
    private final ViewManagerModel viewManagerModel;

    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {200, 400, 100, 100};
    private final String[] columnNames = {"Project Title", "Description", "Edit", "Applications"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);

    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel, ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        this.getProjectsPresenter = new GetProjectsPresenter(myProjectsPanelViewModel);
        this.getProjectsController = new GetProjectsController(new GetProjectsInteractor(this.getProjectsPresenter));

        myProjectsPanelViewModel.addPropertyChangeListener(this);
        viewManagerModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(infoPanel);

        JButton refreshButton = new JButton("Refresh");
        this.add(refreshButton);

        refreshButton.addActionListener(e -> {
            getProjectsController.getProjects(new GetProjectsInputData());
        });
    }

    private void addProjects(Object[][] data){
        ArrayList<ButtonAction> editButtonActions = new ArrayList<>();
        ArrayList<ButtonAction> applicationButtonActions = new ArrayList<>();

        Object[][] info = new Object[data.length][4];
        for (int i = 0; i < data.length; i++) {
            info[i][0] = data[i][0];
            info[i][1] = data[i][1];
            info[i][2] = "Edit";
            info[i][3] = "View Applications";
            int finalI = i;
            editButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    System.out.println("clicked on edit for " + data[finalI][0]);
//                    System.out.println("Viewing details for project: " + projectRankingList.get(finalI).getProjectId());
//                    DisplayIndividualProjectView projectView = new DisplayIndividualProjectView(projectRankingList.get(finalI)); // Use this line when want to display project
                }
            });
            applicationButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    System.out.println("clicked on application for " + data[finalI][0]);
                    DisplayProjectApplicationView appView = new DisplayProjectApplicationView((int)data[finalI][2]);
                }
            });
        }

        DefaultTableModel infoTableModel = new DefaultTableModel(info, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only the button column editable
                return column == 2 || column == 3;
            }
        };
        infoTable.setModel(infoTableModel);

        ButtonColumn editColumn = new ButtonColumn(infoTable, 2);
        editColumn.setActions(editButtonActions);

        ButtonColumn applicationColumn = new ButtonColumn(infoTable, 3);
        applicationColumn.setActions(applicationButtonActions);

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
                getProjectsController.getProjects(new GetProjectsInputData());
            }
        }
        if (evt.getPropertyName().equals("error")) {
            String errorMessage = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(this, errorMessage);
        }
    }
}
