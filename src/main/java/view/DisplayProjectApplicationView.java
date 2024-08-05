package view;

import entities.ProjectInterface;
import usecase.manageapplications.ManageApplicationsController;
import view.components.ButtonAction;
import view.components.ButtonColumn;
import config.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import config.PlayVoiceServiceConfig;
import viewmodel.DisplayProjectApplicationViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A view for displaying and managing project applications.
 */
@SuppressWarnings("FieldCanBeLocal")
public class DisplayProjectApplicationView extends JFrame implements ActionListener, PropertyChangeListener {

    private JTextField projectTitleField;
    private JTextArea projectDescriptionArea;
    private final int projectId;

    private final int[] columnWidths = {400, 200, 200, 200};
    private final String[] columnNames = {"Applicant", "view", "Accept", "Decline"};
    private final JTable infoTable = new JTable();

    private final DisplayProjectApplicationViewModel displayProjectApplicationViewModel;

    private final ManageApplicationsController manageApplicationsController;

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

    /**
     * Constructs a DisplayProjectApplicationView.
     *
     * @param projectId the ID of the project
     * @param displayProjectApplicationViewModel the view model for displaying project applications
     * @param manageApplicationsController the controller for manging applications
     */
    public DisplayProjectApplicationView(int projectId,
                                         DisplayProjectApplicationViewModel displayProjectApplicationViewModel,
                                         ManageApplicationsController manageApplicationsController) {
        this.displayProjectApplicationViewModel = displayProjectApplicationViewModel;
        this.displayProjectApplicationViewModel.addPropertyChangeListener(this);

        this.projectId = projectId;
        this.manageApplicationsController = manageApplicationsController;

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        this.playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

        setTitle("Project Applications");
        setSize(400, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        manageApplicationsController.getApplicationsForProject(projectId);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                displayProjectApplicationViewModel.removePropertyChangeListener(DisplayProjectApplicationView.this);
            }
        });
    }

    /**
     * Displays the applicants in the table.
     *
     * @param applicationsData the data of the applications
     */
    private void displayApplicants(Object[][] applicationsData){
        ArrayList<ButtonAction> viewButtonActions = new ArrayList<>();
        ArrayList<ButtonAction> acceptButtonActions = new ArrayList<>();
        ArrayList<ButtonAction> declineButtonActions = new ArrayList<>();

        Object[][] info = new Object[applicationsData.length][4];
        Map<Point, String> buttonSpeechMap = new HashMap<>();

        DisplayProjectApplicationView temp = this;
        for (int i = 0; i < applicationsData.length; i++) {
            info[i][0] = applicationsData[i][0];
            info[i][1] = "view";
            info[i][2] = "Accept";
            info[i][3] = "Decline";

            buttonSpeechMap.put(new Point(i, 0), "Application info: " + applicationsData[i][0]);
            buttonSpeechMap.put(new Point(i, 1), "Press to download application");
            buttonSpeechMap.put(new Point(i, 2), "Press to accept application");
            buttonSpeechMap.put(new Point(i, 3), "Press to decline application");

            int finalI = i;

            viewButtonActions.add(() -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads");
                File downloadsDirectory = downloadsPath.toFile();
                if (downloadsDirectory.exists() && downloadsDirectory.isDirectory()) {
                    fileChooser.setCurrentDirectory(downloadsDirectory);
                }

                int result = fileChooser.showOpenDialog(temp);
                if (result == JFileChooser.APPROVE_OPTION) {

                    File selectedDirectory = fileChooser.getSelectedFile();

                    String fileName = applicationsData[finalI][0]+" application.pdf";
                    File outputFile = new File(selectedDirectory, fileName);

                    try(FileOutputStream fos = new FileOutputStream(outputFile)){
                        fos.write((byte[])applicationsData[finalI][3]);
                        JOptionPane.showMessageDialog(temp, "Downloaded file: " + fileName);
                    } catch (IOException ex){
                        JOptionPane.showMessageDialog(temp, "Error saving file: " + ex.getMessage());
                    }
                }
            });
            acceptButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    manageApplicationsController.acceptApplicant(projectId, (Integer) applicationsData[finalI][1]);
                }
            });

            acceptButtonActions.add(() -> acceptApplicationController.acceptApplicant(projectId, (Integer) applicationsData[finalI][1]));

            declineButtonActions.add(() -> rejectApplicationController.rejectApplicant(projectId, (Integer) applicationsData[finalI][1]));
            declineButtonActions.add(new ButtonAction() {
                @Override
                public void onClick() {
                    manageApplicationsController.rejectApplicant(projectId, (Integer) applicationsData[finalI][1]);
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

        hoverVoiceService.addTableHoverVoice(infoTable, buttonSpeechMap);

        add(infoTable);

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

        if (evt.getPropertyName().equals("getAppSuccess")) {
            Boolean success = (Boolean) evt.getNewValue();
            if (success) {
                Object[][] data = displayProjectApplicationViewModel.getApplicationData();
                displayApplicants(data);
            }
            else {
                JOptionPane.showMessageDialog(null,
                        displayProjectApplicationViewModel.getErrorMessage());
            }
        }

        if (evt.getPropertyName().equals("acceptSuccess")) {
            Boolean success = (Boolean) evt.getNewValue();
            if (success) {
                String acceptedName = displayProjectApplicationViewModel.getSenderName();
                String message = "Accepted application for: " + acceptedName;
                playVoiceService.playVoice(message);
                JOptionPane.showMessageDialog(null, message);
                getApplicationsController.getApplicationsForProject(projectId);
                JOptionPane.showMessageDialog(null, "Accepted application for: " + acceptedName);
                manageApplicationsController.getApplicationsForProject(projectId);
            }
            else {
                playVoiceService.playVoice("Failed to accept application: " + displayProjectApplicationViewModel.getErrorMessage());
                JOptionPane.showMessageDialog(null,
                        displayProjectApplicationViewModel.getErrorMessage());
            }
        }

        if (evt.getPropertyName().equals("rejectSuccess")) {
            Boolean success = (Boolean) evt.getNewValue();
            if (success) {
                String acceptedName = displayProjectApplicationViewModel.getSenderName();
                JOptionPane.showMessageDialog(null, "Rejected application for: " + acceptedName);
                manageApplicationsController.getApplicationsForProject(projectId);
                String message = "Rejected application for: " + acceptedName;
                playVoiceService.playVoice(message);
                JOptionPane.showMessageDialog(null, message);
                getApplicationsController.getApplicationsForProject(projectId);
            }
            else {
                playVoiceService.playVoice("Failed to reject application: " + displayProjectApplicationViewModel.getErrorMessage());
                JOptionPane.showMessageDialog(null,
                        displayProjectApplicationViewModel.getErrorMessage());
            }
        }
    }
}
