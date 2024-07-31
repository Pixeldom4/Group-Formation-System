package usecase.getusers;

import javax.swing.*;
import java.util.HashSet;

public class GetUsersPresenter implements GetUsersOutputBoundary {

    @Override
    public void prepareSuccessView(GetUsersOutputData outputData) {
        SwingUtilities.invokeLater(() -> {
            JFrame userFrame = new JFrame("Users List");
            userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            userFrame.setSize(400, 300);

            String[] columnNames = {"User ID", "First Name", "Last Name", "Tags", "Compensation", "Owner"};
            Object[][] data = new Object[outputData.getUsers().size()][7];
            int i = 0;
            for (UserData userData : outputData.getUsers()) {
                data[i][0] = userData.getUserId();
                data[i][1] = userData.getFirstName();
                data[i][2] = userData.getLastName();
                data[i][4] = String.join(", ", userData.getTags());
                data[i][5] = userData.getDesiredCompensation();
                data[i][6] = userData.isOwner() ? "Yes" : "No";
                i++;
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            userFrame.add(scrollPane);

            userFrame.setVisible(true);
        });
    }

    @Override
    public void prepareFailView(String errorMessage) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }
}
