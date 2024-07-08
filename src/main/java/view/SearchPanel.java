package view;

import Entities.ProjectInterface;
import view_model.SearchPanelViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SearchPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final SearchPanelViewModel searchPanelModel;
    private final JTextField searchBar = new JTextField();
    private final JButton searchButton = new JButton();
    private final JPanel searchPanel = new JPanel();
    private final JPanel infoArea = new JPanel();
    private final JScrollPane infoPanel = new JScrollPane(infoArea);

    public SearchPanel(SearchPanelViewModel searchPanelModel) {
        this.searchPanelModel = searchPanelModel;
        searchPanelModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        searchBar.setPreferredSize(new Dimension(600, 40));

        searchButton.setPreferredSize(new Dimension(40, 40));
        searchButton.setIcon(new ImageIcon("path/to/search-icon.png")); // Use a suitable search icon image

        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchBar, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Add the search panel to the top of the main panel
        this.add(searchPanel, BorderLayout.NORTH);
        this.add(infoPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("rankProjects")) {
            infoArea.removeAll();
            ProjectInterface[] projectRankingList = (ProjectInterface[]) evt.getNewValue();
            infoArea.setLayout(new GridLayout(projectRankingList.length, 3));
            for (ProjectInterface project : projectRankingList) {
                infoArea.add(new JLabel(project.getProjectTitle()));
                infoArea.add(new JTextArea(cutString(project.getProjectDescription(), 50)));
                JButton viewDetailsButton = new JButton("View Details");
                viewDetailsButton.addActionListener(e -> {
                    // TODO: Implement view details functionality
                });
                infoArea.add(viewDetailsButton);
            }
            infoArea.revalidate();
            infoArea.repaint();
        }
    }

    private String cutString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }
}
