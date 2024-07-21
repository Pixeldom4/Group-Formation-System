package view;

import usecase.acceptapplication.AcceptApplicationController;
import usecase.deleteproject.DeleteProjectController;
import usecase.editproject.EditProjectController;
import usecase.editproject.EditProjectInputData;
import usecase.getapplications.GetApplicationsController;
import usecase.rejectapplication.RejectApplicationController;
import viewmodel.DisplayProjectApplicationViewModel;
import viewmodel.EditProjectPanelViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

public class EditProjectPanel extends JPanel implements PropertyChangeListener {

    private final EditProjectPanelViewModel editProjectViewModel;
    private final EditProjectController editProjectController;
    private final GetApplicationsController getApplicationsController;
    private final DeleteProjectController deleteProjectController;
    private final DisplayProjectApplicationViewModel displayProjectApplicationViewModel;
    private final AcceptApplicationController acceptApplicationController;
    private final RejectApplicationController rejectApplicationController;
    private JTextField titleField;
    private JTextField budgetField;
    private JTextArea descriptionField;
    private JTextField tagsField;
    private JButton saveButton;
    private JButton refreshButton;
    private JButton viewApplicationButton;
    private JButton deleteButton;
    private int projectId;
    private int editorId;

    public EditProjectPanel(
            EditProjectPanelViewModel editProjectViewModel,
            EditProjectController editProjectController,
            GetApplicationsController getApplicationsController,
            DeleteProjectController deleteProjectController,
            DisplayProjectApplicationViewModel displayProjectApplicationViewModel,
            AcceptApplicationController acceptApplicationController,
            RejectApplicationController rejectApplicationController) {
        this.editProjectViewModel = editProjectViewModel;
        this.editProjectViewModel.addPropertyChangeListener(this);
        this.editProjectController = editProjectController;
        this.getApplicationsController = getApplicationsController;
        this.deleteProjectController = deleteProjectController;
        this.displayProjectApplicationViewModel = displayProjectApplicationViewModel;
        this.acceptApplicationController = acceptApplicationController;
        this.rejectApplicationController = rejectApplicationController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        titleField = new JTextField();
        budgetField = new JTextField();
        descriptionField = new JTextArea();
        tagsField = new JTextField();
        saveButton = new JButton("Save");
        viewApplicationButton = new JButton("View Applications");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");

        add(new JLabel("Project Title:"));
        add(titleField);
        add(new JLabel("Budget:"));
        add(budgetField);
        add(new JLabel("Description:"));
        add(new JScrollPane(descriptionField));
        add(new JLabel("Tags (comma separated):"));
        add(tagsField);
        add(saveButton);
        add(viewApplicationButton);
        add(deleteButton);
        add(refreshButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProject();
            }
        });

        viewApplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new DisplayProjectApplicationView(projectId,
                        displayProjectApplicationViewModel,
                        getApplicationsController,
                        acceptApplicationController,
                        rejectApplicationController);

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int dialogResult = JOptionPane.showConfirmDialog (null,
                        "Are you sure you would like to delete " + projectId + "?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    deleteProjectController.deleteProject(projectId);
                }

            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshProject();
            }
        });

    }

    public void setProjectDetails(int projectId, int editorId, String title, double budget, String description, HashSet<String> tags) {
        this.projectId = projectId;
        this.editorId = editorId;
        titleField.setText(title);
        budgetField.setText(String.valueOf(budget));
        descriptionField.setText(description);
        tagsField.setText(String.join(", ", tags));
    }

    private void saveProject() {
        String newTitle = titleField.getText();
        double newBudget = Double.parseDouble(budgetField.getText());
        String newDescription = descriptionField.getText();
        HashSet<String> newTags = new HashSet<>();
        for (String tag : tagsField.getText().split(",")) {
            newTags.add(tag.trim());
        }
        editProjectController.editProject(projectId, newTitle, newBudget, newDescription, newTags, editorId);
    }

    private void refreshProject() {
        // Assume the view model already has the current project details
        titleField.setText(editProjectViewModel.getTitle());
        budgetField.setText(String.valueOf(editProjectViewModel.getBudget()));
        descriptionField.setText(editProjectViewModel.getDescription());
        tagsField.setText(String.join(", ", editProjectViewModel.getTags()));
        editorId = editProjectViewModel.getEditorId();
        projectId =  editProjectViewModel.getProjectId();
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
                JOptionPane.showMessageDialog(null, "Project updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update project: " + editProjectViewModel.getErrorMessage());
            }
        }
    }
}
