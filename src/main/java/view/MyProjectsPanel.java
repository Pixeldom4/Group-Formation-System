package view;

import interfaceadapter.GetProjectsPresenter;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import viewmodel.MyProjectsPanelViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MyProjectsPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final MyProjectsPanelViewModel myProjectsPanelViewModel;
    private final GetProjectsPresenter getProjectsPresenter;

    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {200, 400, 100};
    private final String[] columnNames = {"Project Title", "Description", "View Details"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);

    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        System.out.println("enabled2");
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        this.getProjectsPresenter = new GetProjectsPresenter();
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
            info[i][2] = "View Details";
            detailButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    System.out.println("viewing details for project");
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
        ButtonColumn detailColumn = new ButtonColumn(infoTable, 2);
        detailColumn.setActions(detailButtonActions);

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
        System.out.println("enabled");
    }
}
