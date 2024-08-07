package view;

import entities.User;
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import usecase.manageprojects.ManageProjectsController;
import view.components.NumericTextField;
import view.components.TagPanel;
import config.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import config.PlayVoiceServiceConfig;
import viewmodel.AddProjectPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel for adding a new project.
 */
@SuppressWarnings("FieldCanBeLocal")
public class AddProjectPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final AddProjectPanelViewModel addProjectPanelViewModel;
    private final ManageProjectsController createProjectController;
    private final GetLoggedInUserController getLoggedInUserController;
    private final ViewManagerModel viewManagerModel;

    private final JPanel projectInfoPanel = new JPanel();
    private final JPanel projectDataPanel = new JPanel();
    private final GridLayout projectDataGridLayout = new GridLayout(0, 2);
    private final JLabel projectNameLabel = new JLabel("Project Name");
    private final JLabel projectBudgetLabel = new JLabel("Project Budget");
    private final JLabel projectDescriptionLabel = new JLabel("Project Description");
    private final JLabel projectTagsLabel = new JLabel("Project Tags To Add");
    private final JTextField projectNameField = new JTextField();
    private final NumericTextField projectBudgetField = new NumericTextField();
    private final JTextField projectDescriptionField = new JTextField();

    private final JTextField projectTagsField = new JTextField();
    private final JButton addTagButton = new JButton("Add Tag");
    private final GridLayout tagPanelLayout = new GridLayout(0, 1);
    private final TagPanel tagPanel = new TagPanel();
    private final JLabel tagPanelLabel = new JLabel("Project tags (Press add tag to add): ");

    private final JButton addProjectButton = new JButton("Create project");

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

    /**
     * Constructs an AddProjectPanel.
     *
     * @param viewManagerModel the view manager model
     * @param addProjectPanelViewModel the view model for the add project panel
     * @param createProjectController the controller for creating projects
     * @param getLoggedInUserController the controller for getting the logged-in user
     */
    public AddProjectPanel(ViewManagerModel viewManagerModel,
                           AddProjectPanelViewModel addProjectPanelViewModel,
                           ManageProjectsController createProjectController,
                           GetLoggedInUserController getLoggedInUserController) {
        this.viewManagerModel = viewManagerModel;
        viewManagerModel.addPropertyChangeListener(this);

        this.getLoggedInUserController = getLoggedInUserController;
        this.addProjectPanelViewModel = addProjectPanelViewModel;
        addProjectPanelViewModel.addPropertyChangeListener(this);
        this.createProjectController = createProjectController;

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        this.playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        projectInfoPanel.setLayout(new BoxLayout(projectInfoPanel, BoxLayout.Y_AXIS));

        addTagButton.addActionListener(_ -> {
            String tagText = projectTagsField.getText();
            if (!tagText.isEmpty()) {
                tagPanel.addTagToPanel(tagText);
                projectTagsField.setText("");
            }
        });

        projectDataPanel.setLayout(projectDataGridLayout);

        hoverVoiceService.addHoverVoice(projectNameField, "Enter project name here");
        hoverVoiceService.addHoverVoice(projectBudgetField, "Enter project budget here");
        hoverVoiceService.addHoverVoice(projectDescriptionField, "Enter project description here");
        hoverVoiceService.addHoverVoice(projectTagsField, "Enter project tags here");
        hoverVoiceService.addHoverVoice(addTagButton, "Press to add tag");

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

        addProjectButton.addActionListener(_ -> {
            String title = projectNameField.getText();
            double budget = Double.parseDouble(projectBudgetField.getText());
            String description = projectDescriptionField.getText();
            User loggedInUser = addProjectPanelViewModel.getLoggedInUser();
            if (loggedInUser == null) {
                JOptionPane.showMessageDialog(null, "You must be logged in to create a project.");
                return;
            }
            createProjectController.createProject(title, budget, description, tagPanel.getTags(), loggedInUser.getUserId());
        });

        hoverVoiceService.addHoverVoice(addProjectButton, "Press to create project");

        this.add(addProjectButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No implementation needed
    }

    /**
     * Clears the panel fields and tags.
     */
    private void clearPanel(){
        projectNameField.setText("");
        projectBudgetField.clear();
        projectDescriptionField.setText("");
        projectTagsField.setText("");
        tagPanel.clearPanel();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("success")) {
            boolean success = (boolean) evt.getNewValue();
            String projectName = addProjectPanelViewModel.getProjectName();
            if (success){
                String message = "Project " + projectName + " created successfully";
                playVoiceService.playVoice(message);
                JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                clearPanel();
                viewManagerModel.addProjectEvent();
            }
            else {
                playVoiceService.playVoice("Project creation failed: " + addProjectPanelViewModel.getErrorMessage());
                JOptionPane.showMessageDialog(null, addProjectPanelViewModel.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (evt.getPropertyName().equals("login")) {
            getLoggedInUserController.getLoggedInUser();
        }
    }

}
