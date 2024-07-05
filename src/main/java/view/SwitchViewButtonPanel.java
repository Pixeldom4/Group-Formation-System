package view;

import view_model.SwitchViewButtonPanelViewModel;
import view_model.ViewManagerModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SwitchViewButtonPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel;

    private final JButton addProjectButton = new JButton("Add Project");
    private final JButton searchProjectButton = new JButton("Search Project");

    public SwitchViewButtonPanel(ViewManagerModel viewManagerModel, SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel) {
        this.switchViewButtonPanelViewModel = switchViewButtonPanelViewModel;
        switchViewButtonPanelViewModel.addPropertyChangeListener(this);

        addProjectButton.addActionListener(e -> {
            viewManagerModel.setActiveView("AddProjectView");
            viewManagerModel.firePropertyChanged();
        });

        searchProjectButton.addActionListener(e -> {
            viewManagerModel.setActiveView("SearchPanelView");
            viewManagerModel.firePropertyChanged();
        });

        this.add(addProjectButton);
        this.add(searchProjectButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
