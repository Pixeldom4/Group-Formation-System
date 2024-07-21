package view;

import entities.User;

import javax.swing.*;
import java.awt.*;

public class DisplayMyProfileView extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextArea tagsArea;


    public DisplayMyProfileView(User user) {
        setTitle("My Profile");
        setSize(400, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        firstNameField = new JTextField(user.getFirstName());
        firstNameField.setEditable(false);
        lastNameField = new JTextField(user.getLastName());
        lastNameField.setEditable(false);
        emailField = new JTextField(user.getUserEmail());
        emailField.setEditable(false);
        tagsArea = new JTextArea(user.getTags().toString());
        tagsArea.setEditable(false);
        tagsArea.setLineWrap(true); // Enables line wrapping
        tagsArea.setWrapStyleWord(true); // Wraps at word boundaries

        // User First Name
        detailsPanel.add(new JLabel("First Name:"));
        detailsPanel.add(firstNameField);

        // User Last Name
        detailsPanel.add(new JLabel("Last Name:"));
        detailsPanel.add(lastNameField);

        // User Email
        detailsPanel.add(new JLabel("Email:"));
        detailsPanel.add(emailField);

        // User Tags
        JLabel tagsLabel = new JLabel("Tags:");
        JScrollPane scrollPane = new JScrollPane(tagsArea);
        detailsPanel.add(tagsLabel);
        detailsPanel.add(scrollPane);

        add(detailsPanel, BorderLayout.CENTER);

        this.setVisible(true);


    }
}
