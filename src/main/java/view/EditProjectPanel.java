package view;

import config.HoverVoiceServiceConfig;
import config.PlayVoiceServiceConfig;
import usecase.manageapplications.ManageApplicationsController;
import usecase.manageprojects.ManageProjectsController;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import viewmodel.DisplayProjectApplicationViewModel;
import viewmodel.EditProjectPanelViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

/**
 * A panel for editing project details.
 */
@SuppressWarnings("FieldCanBeLocal")
public class EditProjectPanel extends JPanel implements PropertyChangeListener {

    private final EditProjectPanelViewModel editProjectViewModel;
    private final ManageApplicationsController manageApplicationsController;
    private final ManageProjectsController manageProjectsController;
    private final DisplayProjectApplicationViewModel displayProjectApplicationViewModel;
    private final JTextField titleField;
    private final JTextField budgetField;
    private final JTextArea descriptionField;
    private final JTextArea tagsField;
    private final JButton saveButton;
    private final JButton refreshButton;
    private final JButton viewApplicationButton;
    private final JButton deleteButton;
    private int projectId;
    private int editorId;
    private boolean isOwner;

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

    /**
     * Constructs an EditProjectPanel.
     *
     * @param editProjectViewModel the view model for editing the project
     * @param manageApplicationsController the controller for manging applications
     * @param manageProjectsController the controller for deleting the project
     * @param displayProjectApplicationViewModel the view model for displaying project applications
     */
    public EditProjectPanel(
            EditProjectPanelViewModel editProjectViewModel,
            ManageApplicationsController manageApplicationsController,
            ManageProjectsController manageProjectsController,
            DisplayProjectApplicationViewModel displayProjectApplicationViewModel) {
        this.editProjectViewModel = editProjectViewModel;
        this.editProjectViewModel.addPropertyChangeListener(this);
        this.manageApplicationsController = manageApplicationsController;
        this.manageProjectsController = manageProjectsController;
        this.displayProjectApplicationViewModel = displayProjectApplicationViewModel;

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        this.playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

        setLayout(new GridBagLayout());

        titleField = new JTextField();
        budgetField = new JTextField();
        descriptionField = new JTextArea();
        tagsField = new JTextArea();
        saveButton = new JButton("Save");
        viewApplicationButton = new JButton("View Applications");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");

        hoverVoiceService.addHoverVoice(titleField, "Enter new project title here");
        hoverVoiceService.addHoverVoice(budgetField, "Enter new project budget here");
        hoverVoiceService.addHoverVoice(descriptionField, "Enter new project description here");
        hoverVoiceService.addHoverVoice(tagsField, "Enter new project tags here");
        hoverVoiceService.addHoverVoice(saveButton, "Press to save project");
        hoverVoiceService.addHoverVoice(viewApplicationButton, "Press to view applications");
        hoverVoiceService.addHoverVoice(deleteButton, "Press to delete project");
        hoverVoiceService.addHoverVoice(refreshButton, "Press to refresh project");

        GridBagConstraints titleLabelGbc = createGbc(0, 0);
        add(new JLabel("Project Title:"), titleLabelGbc);
        GridBagConstraints titleFieldGbc = createGbc(1, 0);
        add(titleField, titleFieldGbc);

        GridBagConstraints budgetLabelGbc = createGbc(0, 1);
        add(new JLabel("Budget:"), budgetLabelGbc);
        GridBagConstraints budgetFieldGbc = createGbc(1, 1);
        add(budgetField, budgetFieldGbc);

        GridBagConstraints descriptionLabelGbc = createGbc(0, 2);
        add(new JLabel("Description:"), descriptionLabelGbc);
        descriptionField.setLineWrap(true);
        FieldScrollPane descriptionScrollPane = new FieldScrollPane(descriptionField);
        GridBagConstraints descriptionFieldGbc = createGbc(1, 2);
        add(descriptionScrollPane, descriptionFieldGbc);

        GridBagConstraints tagsLabelGbc = createGbc(0, 3);
        add(new JLabel("Tags (comma separated):"), tagsLabelGbc);
        tagsField.setLineWrap(true);
        FieldScrollPane tagsScrollPane = new FieldScrollPane(tagsField);
        GridBagConstraints tagsFieldGbc = createGbc(1, 3);
        add(tagsScrollPane, tagsFieldGbc);

        GridBagConstraints saveButtonGbc = createGbc(0, 4);
        add(saveButton, saveButtonGbc);
        GridBagConstraints viewApplicationButtonGbc = createGbc(0, 5);
        add(viewApplicationButton, viewApplicationButtonGbc);
        GridBagConstraints deleteButtonGbc = createGbc(0, 6);
        add(deleteButton, deleteButtonGbc);
        GridBagConstraints refreshButtonGbc = createGbc(0, 7);
        add(refreshButton, refreshButtonGbc);

        saveButton.addActionListener(_ -> saveProject());

        viewApplicationButton.addActionListener(_ -> new DisplayProjectApplicationView(projectId,
                                                                                       this.displayProjectApplicationViewModel,
                                                                                       this.manageApplicationsController));

        deleteButton.addActionListener(_ -> {

            int dialogResult = JOptionPane.showConfirmDialog (null,
                    "Are you sure you would like to delete " + projectId + "?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                manageProjectsController.deleteProject(projectId);
            }

        });

    }

    /**
     * Sets the project details in the panel.
     *
     * @param projectId the ID of the project
     * @param editorId the ID of the editor
     * @param title the title of the project
     * @param budget the budget of the project
     * @param description the description of the project
     * @param tags the tags associated with the project
     */
    public void setProjectDetails(int projectId, int editorId, String title, double budget, String description, HashSet<String> tags) {
        this.projectId = projectId;
        this.editorId = editorId;
        titleField.setText(title);
        budgetField.setText(String.valueOf(budget));
        descriptionField.setText(description);
        tagsField.setText(String.join(", ", tags));
    }

    /**
     * Saves the project details by calling the edit project controller.
     */
    private void saveProject() {
        String newTitle = titleField.getText();
        double newBudget = Double.parseDouble(budgetField.getText());
        String newDescription = descriptionField.getText();
        HashSet<String> newTags = new HashSet<>();
        for (String tag : tagsField.getText().split(",")) {
            newTags.add(tag.trim());
        }
        manageProjectsController.editProject(projectId, newTitle, newBudget, newDescription, newTags, editorId);
    }

    /**
     * Refreshes the project details from the view model.
     */
    private void refreshProject() {
        // Assume the view model already has the current project details
        titleField.setText(editProjectViewModel.getTitle());
        budgetField.setText(String.valueOf(editProjectViewModel.getBudget()));
        descriptionField.setText(editProjectViewModel.getDescription());
        tagsField.setText(String.join(", ", editProjectViewModel.getTags()));
        editorId = editProjectViewModel.getEditorId();
        projectId = editProjectViewModel.getProjectId();
        isOwner = editProjectViewModel.isOwner();
        updateVisibility();
    }

    private void updateVisibility() {
        saveButton.setVisible(isOwner);
        deleteButton.setVisible(isOwner);
        refreshButton.setVisible(isOwner);
        titleField.setEnabled(isOwner);
        budgetField.setEnabled(isOwner);
        descriptionField.setEnabled(isOwner);
        tagsField.setEnabled(isOwner);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("detailInit")) {
            refreshProject();
        }
        if (evt.getPropertyName().equals("editSuccess")) {
            refreshProject();
            Boolean success = (Boolean) evt.getNewValue();
            if (success) {
                playVoiceService.playVoice("Project updated successfully!");
                JOptionPane.showMessageDialog(null, "Project updated successfully!");
            } else {
                playVoiceService.playVoice("Failed to update project: " + editProjectViewModel.getErrorMessage());
                JOptionPane.showMessageDialog(null, "Failed to update project: " + editProjectViewModel.getErrorMessage());
            }
        }
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = (x == 1) ? 1 : 0;
        gbc.weighty = (y == 2 || y == 3) ? 1 : 0;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = (x == 0) ? new Insets(10, 10, 0, 0) : new Insets(10, 10, 0, 10);
        return gbc;
    }

    private static class FieldScrollPane extends JScrollPane {
        public FieldScrollPane(JComponent component) {
            super(component);
            this.createVerticalScrollBar();
            this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        }
    }
}
