package view;

import entities.ProjectInterface;
import view.services.hovervoice.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A view for displaying the details of an individual project.
 */
@SuppressWarnings("FieldCanBeLocal")
public class DisplayIndividualProjectView extends JFrame implements ActionListener, PropertyChangeListener {

    private final JLabel projectTitleField;
    private final JLabel tagsField;
    private JTextArea projectDescriptionArea;
    private final JLabel projectID;

    private final IHoverVoiceService hoverVoiceService;

    /**
     * Constructs a DisplayIndividualProjectView.
     *
     * @param project the project to display
     */
    public DisplayIndividualProjectView(ProjectInterface project) {
        setTitle("Project Details");
        setSize(400, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();

        projectTitleField = new JLabel("Project Title:" + project.getProjectTitle());
        projectID = new JLabel("Project ID:" + project.getProjectId());
        tagsField = new JLabel("Tags:" + project.getProjectTags());
        projectDescriptionArea = new JTextArea(project.getProjectDescription());
        projectDescriptionArea.setEditable(false);

        hoverVoiceService.addHoverVoice(projectTitleField, "Project title: " + project.getProjectTitle());
        hoverVoiceService.addHoverVoice(projectID, "Project ID: " + project.getProjectId());
        hoverVoiceService.addHoverVoice(tagsField, "Project tags: " + project.getProjectTags());

        // Project Title
        detailsPanel.add(projectTitleField);

        // Project ID
        detailsPanel.add(projectID);

        // Project Tags
        detailsPanel.add(tagsField);

        // Project Description
        JLabel projectDescriptionLabel = new JLabel("Project Description:");
        projectDescriptionArea = new JTextArea(project.getProjectDescription());
        projectDescriptionArea.setEditable(false);
        projectDescriptionArea.setLineWrap(true); // Enables line wrapping
        projectDescriptionArea.setWrapStyleWord(true); // Wraps at word boundaries
        projectDescriptionArea.setEnabled(false);
        hoverVoiceService.addHoverVoice(projectDescriptionArea, "Project description: " + project.getProjectDescription());

        JScrollPane scrollPane = new JScrollPane(projectDescriptionArea);
        detailsPanel.add(projectDescriptionLabel);
        detailsPanel.add(scrollPane);

        add(detailsPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No implementation needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("displayDetailProject")) {
            ProjectInterface project = (ProjectInterface) evt.getNewValue();
            projectTitleField.setText(project.getProjectTitle());
        }
    }
}
