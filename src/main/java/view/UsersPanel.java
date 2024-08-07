package view;

import usecase.manageusers.getusers.UserData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashSet;

public class UsersPanel extends JPanel {
    private JTable usersTable;
    private DefaultTableModel tableModel;

    public UsersPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        String[] columnNames = {"First Name", "Last Name", "Email", "Tags", "Desired Compensation", "Is Owner"};
        tableModel = new DefaultTableModel(columnNames, 0);
        usersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        add(scrollPane);
    }

    public void displayUsers(HashSet<UserData> users) {
        tableModel.setRowCount(0); // Clear existing rows
        for (UserData user : users) {
            Object[] row = new Object[]{
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUserEmail(),
                    String.join(", ", user.getTags()), // Convert tags set to a comma-separated string
                    user.getDesiredCompensation(),
                    user.isOwner()
            };
            tableModel.addRow(row);
        }
    }

    // Getter for the usersTable
    public JTable getUsersTable() {
        return usersTable;
    }
}
