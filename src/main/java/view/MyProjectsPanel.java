package view;

import usecase.getloggedinuser.GetLoggedInUserController;
import usecase.getprojects.GetProjectsController;
import usecase.getprojects.GetProjectsInputData;
import usecase.getprojects.GetProjectsPresenter;
import usecase.getloggedinuser.GetLoggedInUserPresenter;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import viewmodel.MyProjectsPanelViewModel;

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

    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {200, 400, 100};
    private final String[] columnNames = {"Project Title", "Description", "Edit"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);

    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel, GetLoggedInUserController getLoggedInUserController) {
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        this.getProjectsController = new GetProjectsController();
        this.getProjectsPresenter = new GetProjectsPresenter();

        int tempUserId = 123;
        getProjectsController.getProjects(new GetProjectsInputData(tempUserId));

        myProjectsPanelViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridLayout(0, 1));

        Object[][] data = {
                {"project1", "test"},
                {"project2", "test"},
                {"project3", "test"},
        };

        ArrayList<ButtonAction> detailButtonActions = new ArrayList<>();

        Object[][] info = new Object[data.length][3];
        for (int i = 0; i < data.length; i++) {
            info[i][0] = data[i][0];
            info[i][1] = data[i][1];
            info[i][2] = "Edit";
            detailButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    System.out.println("clicked on edit for ");
//                    System.out.println("Viewing details for project: " + projectRankingList.get(finalI).getProjectId());
//                    DisplayIndividualProjectView projectView = new DisplayIndividualProjectView(projectRankingList.get(finalI)); // Use this line when want to display project
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

        TableColumn actionColumn = infoTable.getColumnModel().getColumn(2);
        actionColumn.setCellRenderer(new ButtonEditorRenderer());
        actionColumn.setCellEditor(new ButtonEditorRenderer());

        TableColumnModel columnModel = infoTable.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        this.add(infoPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
