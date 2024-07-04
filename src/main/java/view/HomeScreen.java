package view;

import javax.swing.*;
import java.awt.*;

public class HomeScreen {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Home Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a search bar
        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(600, 40));

        // Create a search button
        JButton searchButton = new JButton();
        searchButton.setPreferredSize(new Dimension(40, 40));
        searchButton.setIcon(new ImageIcon("path/to/search-icon.png")); // Use a suitable search icon image

        // Create a panel for the search bar and button
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchBar, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Add the search panel to the top of the main panel
        panel.add(searchPanel, BorderLayout.NORTH);

        // Add the main panel to the frame
        frame.add(panel);

        // Make the frame visible
        frame.setVisible(true);
    }
}
