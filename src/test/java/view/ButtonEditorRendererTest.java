package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditorRendererTest {

    public static void main(String[] args) {
        // Initialize the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = createTestFrame();
            frame.setVisible(true);

            // Set up a timer to close the frame automatically after 5 seconds (5000 milliseconds)
            Timer timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose(); // Close the frame
                }
            });
            timer.setRepeats(false); // Ensure the timer only runs once
            timer.start();
        });
    }

    // Method to create the frame
    private static JFrame createTestFrame() {
        JFrame frame = new JFrame("ButtonEditorRenderer Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        // Add the panel to the frame
        frame.add(createTestPanel(), BorderLayout.CENTER);

        return frame;
    }

    // Method to create the JPanel containing the JTable
    private static JPanel createTestPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Set up the table with some dummy data
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Name", "Action"}, 0);
        JTable table = new JTable(model);

        // Populate the table with sample data
        model.addRow(new Object[]{"John Doe", "Edit"});
        model.addRow(new Object[]{"Jane Smith", "Edit"});
        model.addRow(new Object[]{"Alice Brown", "Edit"});

        // Set the ButtonEditorRenderer for the "Action" column
        ButtonEditorRenderer buttonEditorRenderer = new ButtonEditorRenderer();
        table.getColumn("Action").setCellRenderer(buttonEditorRenderer);
        table.getColumn("Action").setCellEditor(buttonEditorRenderer);

        // Add the table to a scroll pane and then to the panel
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
