package view;

import entities.ProjectInterface;
import usecase.getloggedinuser.GetLoggedInUserController;
import usecase.searchforproject.SearchProjectController;
import usecase.searchforuser.SearchUserController;
import usecase.searchprojectbyid.SearchProjectByIdController;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import viewmodel.SearchPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class SearchPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final SearchPanelViewModel searchPanelModel;
    private SearchUserController searchUserController;
    private SearchProjectController searchProjectController;
    private SearchProjectByIdController searchProjectByIdController;
    private GetLoggedInUserController getLoggedInUserController;
    private ViewManagerModel viewManagerModel;

    private final JLabel panelLabel = new JLabel("Search for projects here: ");
    private final JTextField searchBar = new JTextField();
    private final JButton searchButton = new JButton("Search");
    private final JPanel searchPanel = new JPanel();
    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {200, 400, 100};
    private final String[] columnNames = {"Project Title", "Description", "View Details", "Request joining"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);

    public SearchPanel(ViewManagerModel viewManagerModel,
                       SearchPanelViewModel searchPanelModel,
                       SearchUserController searchUserController,
                       GetLoggedInUserController getLoggedInUserController) {
        this(viewManagerModel, searchPanelModel, getLoggedInUserController);
        this.searchUserController = searchUserController;
        searchButton.addActionListener(e -> {
            searchUserController.searchUserByEmail(searchBar.getText());
        });
    }

    public SearchPanel(ViewManagerModel viewManagerModel,
                       SearchPanelViewModel searchPanelModel,
                       SearchProjectController searchProjectController,
                       GetLoggedInUserController getLoggedInUserController) {
        this(viewManagerModel, searchPanelModel, getLoggedInUserController);
        this.searchProjectController = searchProjectController;
        searchButton.addActionListener(e -> {
            searchProjectController.searchProjects(searchBar.getText());
        });
    }

    public SearchPanel(ViewManagerModel viewManagerModel,
                       SearchPanelViewModel searchPanelModel,
                       SearchProjectByIdController searchProjectByIdController,
                       GetLoggedInUserController getLoggedInUserController) {
        this(viewManagerModel,searchPanelModel, getLoggedInUserController);
        this.searchProjectByIdController = searchProjectByIdController;
        searchButton.addActionListener(e -> {
            searchProjectByIdController.searchProjectById(Integer.parseInt(searchBar.getText()));
        });
    }

    /**
     * Used to initialize common components
     * @param searchPanelModel the search panel model
     */
    private SearchPanel(ViewManagerModel viewManagerModel,
                        SearchPanelViewModel searchPanelModel,
                        GetLoggedInUserController getLoggedInUserController) {
        this.viewManagerModel = viewManagerModel;
        viewManagerModel.addPropertyChangeListener(this);
        this.searchPanelModel = searchPanelModel;
        searchPanelModel.addPropertyChangeListener(this);

        this.getLoggedInUserController = getLoggedInUserController;

        this.setLayout(new BorderLayout());

        searchBar.setPreferredSize(new Dimension(600, 40));

        searchButton.setPreferredSize(new Dimension(100, 40));
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

            ArrayList<ProjectInterface> projectRankingList = (ArrayList<ProjectInterface>) evt.getNewValue();
            ArrayList<ButtonAction> detailButtonActions = new ArrayList<>();
            ArrayList<ButtonAction> requestToJoinButtonActions = new ArrayList<>();

            Object[][] info = new Object[projectRankingList.size()][columnNames.length];
            for (int i = 0; i < projectRankingList.size(); i++) {
                info[i][0] = projectRankingList.get(i).getProjectTitle();
                info[i][1] = cutString(projectRankingList.get(i).getProjectDescription());
                info[i][2] = "View Details";
                info[i][3] = "Request to join";
                int finalI = i;
                detailButtonActions.add(new ButtonAction() {
                    @Override
                    public void onClick() {
                        DisplayIndividualProjectView projectView = new DisplayIndividualProjectView(projectRankingList.get(finalI)); // Use this line when want to display project
                    }
                });
                requestToJoinButtonActions.add(new ButtonAction() {
                    @Override
                    public void onClick() {
                        int projectId = projectRankingList.get(finalI).getProjectId();
                        System.out.println("Requesting to join project: " + projectId);
                        DisplayCreateApplicationView displayView = new DisplayCreateApplicationView(projectId);
                        // TODO: Implement request to join
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
            ButtonColumn requestToJoinColumn = new ButtonColumn(infoTable, 3);
            requestToJoinColumn.setActions(requestToJoinButtonActions);

            TableColumnModel columnModel = infoTable.getColumnModel();
            for (int i = 0; i < columnWidths.length; i++) {
                columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
            }

        }
        if (evt.getPropertyName().equals("login")) {
            getLoggedInUserController.getLoggedInUser();
        }
    }

    private String cutString(String str) {
        int maxLength = 100;
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }


}
