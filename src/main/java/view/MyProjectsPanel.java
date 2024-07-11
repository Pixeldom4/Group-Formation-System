package view;

import viewmodel.MyProjectsPanelViewModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MyProjectsPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final MyProjectsPanelViewModel myProjectsPanelViewModel;

//    private final JLabel title = new JLabel("My Projects");

    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        myProjectsPanelViewModel.addPropertyChangeListener(this);

        String[] columnTitles = {"Project", "Description", "Users"};
        Object[][] data = {{"project1", "test desc", "bob, john, tim"}};

        JTable table = new JTable(data, columnTitles);

        table.setCellSelectionEnabled(true);
        ListSelectionModel select = table.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        select.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String data = null;

                for (int row : table.getSelectedRows()){
                    for (int column : table.getSelectedColumns()){
                        data = (String)table.getValueAt(row, column);
                    }
                }

                System.out.println(data);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        TableColumn column = null;
        for(int i = 0; i < 3; i++){
            column = table.getColumnModel().getColumn(i);
            if(i == 0){
                column.setPreferredWidth(100);
            }else{
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
