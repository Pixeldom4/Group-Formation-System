package view;

import entities.ProjectInterface;
import usecase.createapplication.CreateApplicationController;
import usecase.getloggedinuser.GetLoggedInUserController;
import usecase.searchforproject.SearchProjectController;
import usecase.searchforuser.SearchUserController;
import usecase.searchprojectbyid.SearchProjectByIdController;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import view.services.hovervoice.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import view.services.playvoice.PlayVoiceServiceConfig;
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
import java.util.HashMap;
import java.util.Map;

/**
 * A panel for searching and displaying projects.
 */
public class SearchPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final SearchPanelViewModel searchPanelModel;
    private SearchUserController searchUserController;
    private SearchProjectController searchProjectController;
    private SearchProjectByIdController searchProjectByIdController;
    private GetLoggedInUserController getLoggedInUserController;
    private ViewManagerModel viewManagerModel;
    private CreateApplicationController createApplicationController;

    private DisplayCreateApplicationView displayView;

    private final JLabel panelLabel = new JLabel("Search for projects here: ");
    private final JTextField searchBar = new JTextField();
    private final JButton searchButton = new JButton("Search");
    private final JPanel searchPanel = new JPanel();
    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {200, 400, 100};
    private final String[] columnNames = {"Project Title", "Description", "View Details", "Request joining"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

    /**
     * Constructs a SearchPanel for searching users.
     *
     * @param viewManagerModel the view manager model
     * @param searchPanelModel the search panel model
     * @param searchUserController the controller for searching users
     * @param getLoggedInUserController the controller for getting the logged-in user
     * @param createApplicationController the controller for creating applications
     */
    public SearchPanel(ViewManagerModel viewManagerModel,
                       SearchPanelViewModel searchPanelModel,
                       SearchUserController searchUserController,
                       GetLoggedInUserController getLoggedInUserController,
                       CreateApplicationController createApplicationController) {
        this(viewManagerModel, searchPanelModel, getLoggedInUserController, createApplicationController);
        this.searchUserController = searchUserController;
        searchButton.addActionListener(e -> {
            searchUserController.searchUserByEmail(searchBar.getText());
        });
    }

    /**
     * Constructs a SearchPanel for searching projects.
     *
     * @param viewManagerModel the view manager model
     * @param searchPanelModel the search panel model
     * @param searchProjectController the controller for searching projects
     * @param getLoggedInUserController the controller for getting the logged-in user
     * @param createApplicationController the controller for creating applications
     */
    public SearchPanel(ViewManagerModel viewManagerModel,
                       SearchPanelViewModel searchPanelModel,
                       SearchProjectController searchProjectController,
                       GetLoggedInUserController getLoggedInUserController,
                       CreateApplicationController createApplicationController) {
        this(viewManagerModel, searchPanelModel, getLoggedInUserController, createApplicationController);
        this.searchProjectController = searchProjectController;
        searchButton.addActionListener(e -> {
            searchProjectController.searchProjects(searchBar.getText());
        });
    }

    /**
     * Constructs a SearchPanel for searching projects by ID.
     *
     * @param viewManagerModel the view manager model
     * @param searchPanelModel the search panel model
     * @param searchProjectByIdController the controller for searching projects by ID
     * @param getLoggedInUserController the controller for getting the logged-in user
     * @param createApplicationController the controller for creating applications
     */
    public SearchPanel(ViewManagerModel viewManagerModel,
                       SearchPanelViewModel searchPanelModel,
                       SearchProjectByIdController searchProjectByIdController,
                       GetLoggedInUserController getLoggedInUserController,
                       CreateApplicationController createApplicationController) {
        this(viewManagerModel,searchPanelModel, getLoggedInUserController, createApplicationController);
        this.searchProjectByIdController = searchProjectByIdController;
        searchButton.addActionListener(e -> {
            searchProjectByIdController.searchProjectById(Integer.parseInt(searchBar.getText()));
        });
    }

    /**
     * Used to initialize common components.
     *
     * @param viewManagerModel the view manager model
     * @param searchPanelModel the search panel model
     * @param getLoggedInUserController the controller for getting the logged-in user
     * @param createApplicationController the controller for creating applications
     */
    private SearchPanel(ViewManagerModel viewManagerModel,
                        SearchPanelViewModel searchPanelModel,
                        GetLoggedInUserController getLoggedInUserController,
                        CreateApplicationController createApplicationController) {
        this.viewManagerModel = viewManagerModel;
        viewManagerModel.addPropertyChangeListener(this);
        this.searchPanelModel = searchPanelModel;
        searchPanelModel.addPropertyChangeListener(this);

        this.getLoggedInUserController = getLoggedInUserController;
        this.createApplicationController = createApplicationController;

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        this.playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

        this.setLayout(new BorderLayout());

        searchBar.setPreferredSize(new Dimension(600, 40));

        searchButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setIcon(new ImageIcon("path/to/search-icon.png")); // Use a suitable search icon image

        hoverVoiceService.addHoverVoice(searchBar, "Enter search text here");
        hoverVoiceService.addHoverVoice(searchButton, "Press to search");

        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchBar, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Add the search panel to the top of the main panel
        this.add(searchPanel, BorderLayout.NORTH);

        this.add(infoPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No implementation needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("rankProjects")) {
            ArrayList<ProjectInterface> projectRankingList = (ArrayList<ProjectInterface>) evt.getNewValue();
            displaySearchResult(projectRankingList);

        }
        if (evt.getPropertyName().equals("login")) {
            getLoggedInUserController.getLoggedInUser();
            searchBar.setText("");
            searchPanelModel.setProjects(new ArrayList<ProjectInterface>());
            displaySearchResult(new ArrayList<ProjectInterface>());
        }
        if (evt.getPropertyName().equals("application")) {
            boolean success = (boolean) evt.getNewValue();
            if (success) {
                playVoiceService.playVoice("Application submitted");
                JOptionPane.showMessageDialog(null, "Application submitted");
                displayView.dispose();
            }
            else {
                playVoiceService.playVoice("Error submitting application : " + searchPanelModel.getErrorApplicationMessage());
                JOptionPane.showMessageDialog(null, searchPanelModel.getErrorApplicationMessage());
            }
        }
    }

    /**
     * Cuts a string to a maximum length.
     *
     * @param str the string to cut
     * @return the cut string
     */
    private String cutString(String str) {
        int maxLength = 100;
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }

    /**
     * Displays the search results in the table.
     *
     * @param projectRankingList the list of projects to display
     */
    private void displaySearchResult(ArrayList<ProjectInterface> projectRankingList) {
        ArrayList<ButtonAction> detailButtonActions = new ArrayList<>();
        ArrayList<ButtonAction> requestToJoinButtonActions = new ArrayList<>();

        Object[][] info = new Object[projectRankingList.size()][columnNames.length];
        Map<Point, String> hoverSpeechMap = new HashMap<>();

        for (int i = 0; i < projectRankingList.size(); i++) {
            info[i][0] = projectRankingList.get(i).getProjectTitle();
            info[i][1] = cutString(projectRankingList.get(i).getProjectDescription());
            info[i][2] = "View Details";
            info[i][3] = "Request to join";

            hoverSpeechMap.put(new Point(i, 0), "Project title: " + projectRankingList.get(i).getProjectTitle());
            hoverSpeechMap.put(new Point(i, 1), "Project description: " + cutString(projectRankingList.get(i).getProjectDescription()));
            hoverSpeechMap.put(new Point(i, 2), "Press to view project details");
            hoverSpeechMap.put(new Point(i, 3), "Press to request to join project");

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
                    displayView = new DisplayCreateApplicationView(searchPanelModel.getLoggedInUser().getUserId(),
                            projectId,
                            createApplicationController);
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
        hoverVoiceService.addTableHoverVoice(infoTable, hoverSpeechMap);

        ButtonColumn detailColumn = new ButtonColumn(infoTable, 2);
        detailColumn.setActions(detailButtonActions);
        ButtonColumn requestToJoinColumn = new ButtonColumn(infoTable, 3);
        requestToJoinColumn.setActions(requestToJoinButtonActions);

        TableColumnModel columnModel = infoTable.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }
}
