package view;

import viewmodel.AddProjectPanelViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddProjectPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final AddProjectPanelViewModel addProjectPanelViewModel;

    private final JLabel testLabel = new JLabel("Test label");

    public AddProjectPanel(AddProjectPanelViewModel addProjectPanelViewModel) {
        this.addProjectPanelViewModel = addProjectPanelViewModel;
        addProjectPanelViewModel.addPropertyChangeListener(this);

        this.add(testLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
