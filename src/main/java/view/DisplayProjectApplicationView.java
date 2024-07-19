package view;

import entities.Application;
import entities.ProjectInterface;
import usecase.getprojects.GetProjectsController;
import usecase.getprojects.GetProjectsInteractor;
import usecase.getprojects.GetProjectsPresenter;
import usecase.manageapplications.ManageApplicationsController;
import usecase.manageapplications.ManageApplicationsInputData;
import usecase.manageapplications.ManageApplicationsInteractor;
import usecase.manageapplications.ManageApplicationsPresenter;
import view.components.ButtonAction;
import view.components.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;

public class DisplayProjectApplicationView extends JFrame implements ActionListener, PropertyChangeListener {

    private JTextField projectTitleField;
    private JTextArea projectDescriptionArea;

    private final int[] columnWidths = {400, 200, 200, 200};
    private final String[] columnNames = {"Applicant", "View", "Accept", "Decline"};
    private final JTable infoTable = new JTable();
    private final JScrollPane infoPanel = new JScrollPane(infoTable);

    private final ManageApplicationsPresenter manageApplicationsPresenter;
    private final ManageApplicationsController manageApplicationsController;
//    private final ManageApplicationsInteractor manageApplicationsInteractor;

    public DisplayProjectApplicationView(int projectId) {
        this.manageApplicationsPresenter = new ManageApplicationsPresenter(this);
        this.manageApplicationsController = new ManageApplicationsController(new ManageApplicationsInteractor(this.manageApplicationsPresenter));

        setTitle("Project Applications");
        setSize(400, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        manageApplicationsController.getApplicationsForProject(new ManageApplicationsInputData(projectId));
    }

    public void displayApplicants(Object[][] applicationsData){
        ArrayList<ButtonAction> viewButtonActions = new ArrayList<>();
        ArrayList<ButtonAction> acceptButtonActions = new ArrayList<>();
        ArrayList<ButtonAction> declineButtonActions = new ArrayList<>();

        Object[][] info = new Object[applicationsData.length][4];

        DisplayProjectApplicationView temp = this;
        for (int i = 0; i < applicationsData.length; i++) {
            info[i][0] = applicationsData[i][0];
            info[i][1] = "View";
            info[i][2] = "Accept";
            info[i][3] = "Decline";
            int finalI = i;
            viewButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    System.out.println("clicked on edit for " );
                }
            });
            acceptButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    JOptionPane.showMessageDialog(null, "Accepted application for: ");
                }
            });
            declineButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    JOptionPane.showMessageDialog(null, "Rejected application for: ");
//                    temp.displayApplicants(applicationsData);
                }
            });
        }

        DefaultTableModel infoTableModel = new DefaultTableModel(info, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only the button column editable
                return column == 1 || column == 2 || column == 3;
            }
        };
        infoTable.setModel(infoTableModel);

        ButtonColumn viewColumn = new ButtonColumn(infoTable, 1);
        viewColumn.setActions(viewButtonActions);

        ButtonColumn acceptColumn = new ButtonColumn(infoTable, 2);
        acceptColumn.setActions(acceptButtonActions);

        ButtonColumn declineColumn = new ButtonColumn(infoTable, 3);
        declineColumn.setActions(declineButtonActions);

        TableColumnModel columnModel = infoTable.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        projectTitleField = new JTextField("test");
        projectTitleField.setEditable(false);
        projectDescriptionArea = new JTextArea("desc");
        projectDescriptionArea.setEditable(false);
        // Project Title
        detailsPanel.add(new JLabel("Project Title:"  ));

        // Project ID
        detailsPanel.add(new JLabel("Project ID:"));

        // Project Tags
        detailsPanel.add(new JLabel("Tags:"  ));

        add(infoTable);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("displayDetailProject")){
            ProjectInterface project = (ProjectInterface) evt.getNewValue();
            projectTitleField.setText(project.getProjectTitle());
        }
    }
}
