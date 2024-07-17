package view;

import viewmodel.MyProjectsPanelViewModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

public class MyProjectsPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final MyProjectsPanelViewModel myProjectsPanelViewModel;

    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        myProjectsPanelViewModel.addPropertyChangeListener(this);

        String[] columnTitles = {"Project", "Owner", "Users", "Actions"};

        HashSet<Integer> testProjects = new HashSet<>();
        testProjects.add(1);
        testProjects.add(6);
        testProjects.add(3);

        for (Object projectID : testProjects) {
            System.out.println(projectID);
        }

        Object[][] data = {
                {"project1", "test", "bob, john, tim", "Edit"},
                {"project2", "test", "bob, john, tim", "Edit"},
                {"project3", "test", "bob, john, tim", "Edit"},
        };

        JTable table = new JTable(data, columnTitles);

        table.setCellSelectionEnabled(true);
        ListSelectionModel select = table.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        select.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String data = null;

                for (int row : table.getSelectedRows()) {
                    for (int column : table.getSelectedColumns()) {
                        data = (String) table.getValueAt(row, column);
                    }
                }

                System.out.println(data);
            }
        });

        TableColumn actionColumn = table.getColumnModel().getColumn(3);
        actionColumn.setCellRenderer(new ButtonEditorRenderer());
        actionColumn.setCellEditor(new ButtonEditorRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        //Set size of columns
        for (int i = 0; i < 3; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else {
                column.setPreferredWidth(50);
            }
        }

        this.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
