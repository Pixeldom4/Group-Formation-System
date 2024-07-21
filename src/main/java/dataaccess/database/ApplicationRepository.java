package dataaccess.database;

import dataaccess.IApplicationRepository;
import entities.Application;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A class representing the Application Repository. In particular, this class manages the Applications table.
 */
public class ApplicationRepository extends SQLDatabaseManager implements IApplicationRepository {

    /**
     * Constructs an ApplicationRepository with the specified database name.
     *
     * @param databaseName the name of the database.
     */
    public ApplicationRepository(String databaseName) {
        super(databaseName);
    }

    /**
     * Initializes the Applications table.
     */
    @Override
    public void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS Applications (SenderUserId INTEGER NOT NULL, ProjectId INTEGER NOT NULL, Text TEXT NOT NULL, PdfBytes VARBINARY(3145728), PRIMARY KEY(SenderUserId, ProjectId), FOREIGN KEY(SenderUserId) REFERENCES Users(Id), FOREIGN KEY(ProjectId) REFERENCES Projects(Id))";
        super.initializeTables(sql);
    }

    /**
     * Creates a new application in the database.
     *
     * @param senderUserId the ID of the sender user.
     * @param projectId    the ID of the project.
     * @param text         the application text.
     * @param pdfBytes     the PDF bytes of the application.
     * @return the created Application object, or null if the operation fails.
     */
    public Application createApplication(int senderUserId, int projectId, String text, byte[] pdfBytes) {
        String sql = "INSERT INTO Applications (SenderUserId, ProjectId, Text, PdfBytes) VALUES (?, ?, ?, ?)";

        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, senderUserId);
            preparedStatement.setInt(2, projectId);
            preparedStatement.setString(3, text);
            preparedStatement.setBytes(4, pdfBytes);
            preparedStatement.executeUpdate();

            return new Application(senderUserId, projectId, text, pdfBytes);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves an application from the database based on the user ID and project ID.
     *
     * @param userId    the ID of the user.
     * @param projectId the ID of the project.
     * @return the Application object if found, or null otherwise.
     */
    public Application getApplication(int userId, int projectId) {
        String sql = "SELECT SenderUserId, ProjectId, Text, PdfBytes FROM Applications WHERE SenderUserId = ? AND ProjectId = ?";

        Connection connection = super.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, projectId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int rsSenderUserId = rs.getInt("SenderUserId");
                    int rsProjectId = rs.getInt("ProjectId");
                    String text = rs.getString("Text");
                    byte[] pdfBytes = rs.getBytes("PdfBytes");

                    return new Application(rsSenderUserId, rsProjectId, text, pdfBytes);
                }
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    /**
     * Retrieves all applications for a specific user.
     *
     * @param userId the ID of the user.
     * @return a HashSet of Application objects for the specified user.
     */
    public HashSet<Application> getApplicationsForUser(int userId) {
        String sql = "SELECT SenderUserId, ProjectId, Text, PdfBytes FROM Applications WHERE SenderUserId = ?";
        return fetchApplications(userId, sql);
    }

    /**
     * Retrieves all applications for a specific project.
     *
     * @param projectId the ID of the project.
     * @return a HashSet of Application objects for the specified project.
     */
    public HashSet<Application> getApplicationsForProject(int projectId) {
        String sql = "SELECT SenderUserId, ProjectId, Text, PdfBytes FROM Applications WHERE ProjectId = ?";
        return fetchApplications(projectId, sql);
    }

    /**
     * Helper method to fetch applications based on a given SQL query and ID.
     *
     * @param id  the ID to filter applications.
     * @param sql the SQL query to execute.
     * @return a HashSet of Application objects.
     */
    @NotNull
    private HashSet<Application> fetchApplications(int id, String sql) {
        HashSet<Application> applications = new HashSet<>();

        Connection connection = super.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int rsSenderUserId = rs.getInt("SenderUserId");
                    int rsProjectId = rs.getInt("ProjectId");
                    String text = rs.getString("Text");
                    byte[] pdfBytes = rs.getBytes("PdfBytes");

                    applications.add(new Application(rsSenderUserId, rsProjectId, text, pdfBytes));
                }
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return applications;
    }

    /**
     * Deletes an application from the database based on the sender user ID and project ID.
     *
     * @param senderUserId the ID of the sender user.
     * @param projectId    the ID of the project.
     * @return true if the application was successfully deleted, false otherwise.
     */
    public boolean deleteApplication(int senderUserId, int projectId) {
        String sql = "DELETE FROM Applications WHERE SenderUserId = ? AND ProjectId = ?";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, senderUserId);
            preparedStatement.setInt(2, projectId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
