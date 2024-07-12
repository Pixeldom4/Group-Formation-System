package view;

import entities.ProjectInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DisplayIndividualProjectView extends JFrame implements ActionListener, PropertyChangeListener {

    private JTextField projectTitleField;
    private JTextField tagsField;
    private JTextArea projectDescriptionArea;



    public DisplayIndividualProjectView(ProjectInterface project) {
        setTitle("Project Details");
        setSize(400, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        projectTitleField = new JTextField(project.getProjectTitle());
        projectTitleField.setEditable(false);
        tagsField = new JTextField(project.getProjectTags().toString());
        tagsField.setEditable(false);
        projectDescriptionArea = new JTextArea(project.getProjectDescription());
        projectDescriptionArea.setEditable(false);
        // Project Title
        detailsPanel.add(new JLabel("Project Title:" + project.getProjectTitle() ));

        // Project Tags
        detailsPanel.add(new JLabel("Tags:" + project.getProjectTags() ));

        // Project Description
        JLabel projectDescriptionLabel = new JLabel("Project Description:");
        projectDescriptionArea = new JTextArea(project.getProjectDescription());
        projectDescriptionArea.setEditable(false);
        projectDescriptionArea.setLineWrap(true); // Enables line wrapping
        projectDescriptionArea.setWrapStyleWord(true); // Wraps at word boundaries
        JScrollPane scrollPane = new JScrollPane(projectDescriptionArea);
        detailsPanel.add(projectDescriptionLabel);
        detailsPanel.add(scrollPane);

        add(detailsPanel, BorderLayout.CENTER);

        add(detailsPanel, BorderLayout.CENTER);

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
