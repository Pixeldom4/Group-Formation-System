package view;

import entities.ProjectInterface;
import usecase.searchforproject.SearchProjectController;
import viewmodel.SearchPanelViewModel;

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
    private final SearchProjectController searchProjectController;

    private final JLabel panelLabel = new JLabel("Search for projects here: ");
    private final JTextField searchBar = new JTextField();
    private final JButton searchButton = new JButton("Search");
    private final JPanel searchPanel = new JPanel();
    private final JTable infoTable = new JTable();
    private final int[] columnWidths = {200, 400, 100};
    private final String[] columnNames = {"Project Title", "Description", "View Details"};
    private final JScrollPane infoPanel = new JScrollPane(infoTable);

    public SearchPanel(SearchPanelViewModel searchPanelModel, SearchProjectController searchProjectController) {
        this.searchProjectController = searchProjectController;
        this.searchPanelModel = searchPanelModel;
        searchPanelModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        searchBar.setPreferredSize(new Dimension(600, 40));

        searchButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setIcon(new ImageIcon("path/to/search-icon.png")); // Use a suitable search icon image
        searchButton.addActionListener(e -> {
            searchProjectController.searchProjects(searchBar.getText());
        });

        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(panelLabel, BorderLayout.NORTH);
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
            ArrayList<ButtonAction> buttonActions = new ArrayList<>();

            Object[][] info = new Object[projectRankingList.size()][columnNames.length];
            for (int i = 0; i < projectRankingList.size(); i++) {
                info[i][0] = projectRankingList.get(i).getProjectTitle();
                info[i][1] = cutString(projectRankingList.get(i).getProjectDescription());
                info[i][2] = "View Details";
                int finalI = i;
                buttonActions.add(new ButtonAction() {
                    @Override
                    public void onClick() {
                        System.out.println("Viewing details for project: " + projectRankingList.get(finalI).getProjectId());
                        DisplayIndividualProjectView projectView = new DisplayIndividualProjectView(projectRankingList.get(finalI)); // Use this line when want to display project
                    }
                });
            }
            DefaultTableModel infoTableModel = new DefaultTableModel(info, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make only the button column editable
                    return column == 2;
                }
            };
            infoTable.setModel(infoTableModel);
            ButtonColumn buttonColumn = new ButtonColumn(infoTable, 2);
            buttonColumn.setActions(buttonActions);
            TableColumnModel columnModel = infoTable.getColumnModel();

            for (int i = 0; i < columnWidths.length; i++) {
                columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
            }

        }
    }

    private String cutString(String str) {
        int maxLength = 100;
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }

    /**
     * Interface for button actions.
     */
    private interface ButtonAction {
        void onClick();
    }

    /**
     * Class for rendering and editing buttons in the table.
     */
    private class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
        private final JTable table;
        private final JButton renderButton;
        private final JButton editButton;
        private String text;
        private ArrayList<ButtonAction> actions = new ArrayList<>();

        public ButtonColumn(JTable table, int column) {
            super();
            this.table = table;
            renderButton = new JButton();
            editButton = new JButton();
            editButton.setFocusPainted(false);
            editButton.addActionListener(this);

            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer(this);
            columnModel.getColumn(column).setCellEditor(this);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (hasFocus) {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            } else if (isSelected) {
                renderButton.setForeground(table.getSelectionForeground());
                renderButton.setBackground(table.getSelectionBackground());
            } else {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
            renderButton.setText((value == null) ? "" : value.toString());
            return renderButton;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            text = (value == null) ? "" : value.toString();
            editButton.setText(text);
            return editButton;
        }

        @Override
        public Object getCellEditorValue() {
            return text;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
            actions.get(table.getSelectedRow()).onClick();
        }

        public void setActions(ArrayList<ButtonAction> actions) {
            this.actions = actions;
        }
    }
}
