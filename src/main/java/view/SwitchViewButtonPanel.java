package view;

import viewmodel.SwitchViewButtonPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SwitchViewButtonPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel;

    private final JButton addProjectButton = new JButton("Add Project");
    private final JButton searchProjectButton = new JButton("Search Project");
    private final JButton getProjectsButton = new JButton("My Projects");

    public SwitchViewButtonPanel(ViewManagerModel viewManagerModel, SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel) {
        this.switchViewButtonPanelViewModel = switchViewButtonPanelViewModel;
        switchViewButtonPanelViewModel.addPropertyChangeListener(this);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addProjectButton.addActionListener(e -> {
            viewManagerModel.setActiveView("AddProjectView");
            viewManagerModel.firePropertyChanged();
        });

        searchProjectButton.addActionListener(e -> {
            viewManagerModel.setActiveView("SearchPanelView");
            viewManagerModel.firePropertyChanged();
        });

        getProjectsButton.addActionListener(e -> {
            viewManagerModel.setActiveView("GetProjectsView");
            viewManagerModel.firePropertyChanged();
        });

        this.add(addProjectButton);
        this.add(searchProjectButton);
        this.add(getProjectsButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
