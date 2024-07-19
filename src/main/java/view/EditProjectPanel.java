package view;

import usecase.editproject.EditProjectController;
import view.components.NumericTextField;
import viewmodel.EditProjectPanelViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

public class EditProjectPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final EditProjectPanelViewModel editProjectPanelViewModel;
    private final EditProjectController editProjectController;

    private final JPanel projectInfoPanel = new JPanel();
    private final JPanel projectDataPanel = new JPanel();
    private final GridLayout projectDataGridLayout = new GridLayout(0, 2);
    private final JLabel projectNameLabel = new JLabel("Project Name");
    private final JLabel projectBudgetLabel = new JLabel("Project Budget");
    private final JLabel projectDescriptionLabel = new JLabel("Project Description");
    private final JLabel projectTagsLabel = new JLabel("Project Tags");
    private final JTextField projectNameField = new JTextField();
    private final JTextField projectBudgetField = new NumericTextField();
    private final JTextField projectDescriptionField = new JTextField();
    private final JTextField projectTagsField = new JTextField();
    private final JButton addTagButton = new JButton("Add Tag");
    private final GridLayout tagPanelLayout = new GridLayout(0, 1);
    private final JPanel tagPanel = new JPanel();
    private final JLabel tagPanelLabel = new JLabel("Project tags: ");
    private final JButton editProjectButton = new JButton("Edit project");

    private final Set<String> tags = new HashSet<>();

    public EditProjectPanel(EditProjectPanelViewModel editProjectPanelViewModel, EditProjectController editProjectController) {
        this.editProjectPanelViewModel = editProjectPanelViewModel;
        editProjectPanelViewModel.addPropertyChangeListener(this);
        this.editProjectController = editProjectController;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        projectInfoPanel.setLayout(new BoxLayout(projectInfoPanel, BoxLayout.Y_AXIS));

        addTagButton.addActionListener(e -> {
            String tagText = projectTagsField.getText();
            if (!tagText.isEmpty() && !tags.contains(tagText)) {
                addTagToPanel(tagText);
                tags.add(tagText);
                projectTagsField.setText("");
            }
        });

        projectDataPanel.setLayout(projectDataGridLayout);

        projectDataPanel.add(projectNameLabel);
        projectDataPanel.add(projectNameField);
        projectDataPanel.add(projectBudgetLabel);
        projectDataPanel.add(projectBudgetField);
        projectDataPanel.add(projectDescriptionLabel);
        projectDataPanel.add(projectDescriptionField);
        projectDataPanel.add(projectTagsLabel);
        projectDataPanel.add(projectTagsField);
        projectDataPanel.add(addTagButton);

        projectInfoPanel.add(projectDataPanel);

        tagPanel.add(tagPanelLabel);
        tagPanel.setLayout(tagPanelLayout);
        tagPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        projectInfoPanel.add(tagPanel);

        this.add(projectInfoPanel);

        editProjectButton.addActionListener(e -> {
            int projectId = editProjectPanelViewModel.getProjectId();
            String title = projectNameField.getText();
            double budget = Double.parseDouble(projectBudgetField.getText());
            String description = projectDescriptionField.getText();
//            editProjectController.editProject(projectId, title, budget, description, tags);
        });

        this.add(editProjectButton);
    }

    private void addTagToPanel(String text) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JButton removeButton = new JButton("x");

        JPanel tag = new JPanel();
        tag.setBorder(new EmptyBorder(0, 10, 0, 10));
        tag.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;

        constraints.gridx = 0;
        constraints.weightx = 0.8;
        tag.add(label, constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.2;
        tag.add(removeButton, constraints);

        removeButton.addActionListener(e -> {
            tags.remove(text);
            tagPanel.remove(tag);
            tagPanel.revalidate();
            tagPanel.repaint();
        });

        tagPanel.add(tag);
        tagPanel.revalidate();
        tagPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle actions if needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("success")) {
            boolean success = (boolean) evt.getNewValue();
            String projectName = editProjectPanelViewModel.getProjectName();
            if (success) {
                JOptionPane.showMessageDialog(null, "Project " + projectName + " edited successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, editProjectPanelViewModel.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
