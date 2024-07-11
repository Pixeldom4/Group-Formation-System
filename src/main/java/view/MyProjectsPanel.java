package view;

import viewmodel.MyProjectsPanelViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MyProjectsPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final MyProjectsPanelViewModel myProjectsPanelViewModel;

    private final JLabel title = new JLabel("My Projects");

    public MyProjectsPanel(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
        myProjectsPanelViewModel.addPropertyChangeListener(this);

        String[] columnTitles = {"Project", "Description", "Users"};
        Object[][] data = {{"project1", "test desc", "bob, john, tim"}};

        JTable table = new JTable(data, columnTitles);
//        table.getTableHeader().setBounds(50, 30, 700, 20);
//        table.setCellSelectionEnabled(true);
        this.add(title);
        this.add(table.getTableHeader());
        this.add(table);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
