package view;

import usecase.editproject.EditProjectController;
import usecase.editproject.EditProjectInputData;
import viewmodel.EditProjectPanelViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class EditProjectPanel extends JPanel {

    private final EditProjectPanelViewModel editProjectViewModel;
    private final EditProjectController editProjectController;
    private JTextField titleField;
    private JTextField budgetField;
    private JTextArea descriptionField;
    private JTextField tagsField;
    private JButton saveButton;
    private JButton refreshButton;
    private int projectId;
    private int editorId;

    public EditProjectPanel(EditProjectPanelViewModel editProjectViewModel, EditProjectController editProjectController) {
        this.editProjectViewModel = editProjectViewModel;
        this.editProjectController = editProjectController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        titleField = new JTextField();
        budgetField = new JTextField();
        descriptionField = new JTextArea();
        tagsField = new JTextField();
        saveButton = new JButton("Save");
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
        add(refreshButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProject();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshProject();
            }
        });

        // Initialize the fields
        refreshProject();
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
        editProjectController.editProject(new EditProjectInputData(projectId, newTitle, newBudget, newDescription, newTags, editorId));
    }

    private void refreshProject() {
        // Assume the view model already has the current project details
        titleField.setText(editProjectViewModel.getTitle());
        budgetField.setText(String.valueOf(editProjectViewModel.getBudget()));
        descriptionField.setText(editProjectViewModel.getDescription());
        tagsField.setText(String.join(", ", editProjectViewModel.getTags()));
    }
}
