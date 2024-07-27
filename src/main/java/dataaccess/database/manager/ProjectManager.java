package dataaccess.database.manager;

import dataaccess.database.SQLDatabaseManager;
import entities.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * Manages project-related operations in the database.
 */
public class ProjectManager extends SQLDatabaseManager {

    /**
     * Constructs a ProjectManager with the specified database name.
     *
     * @param databaseName the name of the database.
     */
    public ProjectManager(String databaseName) {
        super(databaseName);
    }

    @Override
    public void initialize() {
        String projectSql = "CREATE TABLE IF NOT EXISTS Projects (Id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT NOT NULL, Budget DOUBLE, Description TEXT NOT NULL, OwnerId INTEGER NOT NULL, FOREIGN KEY(OwnerId) REFERENCES Users(Id))";
        super.initializeTables(projectSql);
    }

    /**
     * Creates a new project in the database.
     *
     * @param title       the title of the project.
     * @param budget      the budget of the project.
     * @param description the description of the project.
     * @param ownerId     the user ID of the owner of the project.
     * @return the created Project object, or null if the operation fails.
     */
    public Project createProject(String title, double budget, String description, int ownerId) {
        String projectSql = "INSERT INTO Projects (Title, Budget, Description, OwnerId) VALUES (?, ?, ?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement projectStatement = connection.prepareStatement(projectSql, Statement.RETURN_GENERATED_KEYS)) {
            projectStatement.setString(1, title);
            projectStatement.setDouble(2, budget);
            projectStatement.setString(3, description);
            projectStatement.setInt(4, ownerId);

            int affectedRows = projectStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet keys = projectStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        int projectId = keys.getInt(1);
                        return new Project(projectId, title, budget, description, new HashSet<>());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a project from the database.
     *
     * @param projectId the ID of the project to delete.
     * @return true if the project was deleted successfully, false otherwise.
     */
    public boolean deleteProject(int projectId) {
        String deleteProjectSql = "DELETE FROM Projects WHERE Id = ?";
        Connection connection = getConnection();
        try (PreparedStatement deleteProjectStatement = connection.prepareStatement(deleteProjectSql)) {
            deleteProjectStatement.setInt(1, projectId);
            deleteProjectStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves a project from the database by its ID.
     *
     * @param projectId the ID of the project to retrieve.
     * @return the Project object if found, or null otherwise.
     */
    public Project getProjectById(int projectId) {
        String sql = "SELECT Title, Budget, Description FROM Projects WHERE Id = ?";
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("Title");
                    double budget = rs.getDouble("Budget");
                    String description = rs.getString("Description");
                    return new Project(projectId, title, budget, description, new HashSet<>());
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Updates a project's information in the database.
     *
     * @param projectId   the ID of the project.
     * @param title       the title of the project.
     * @param budget      the budget of the project.
     * @param description the description of the project.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateProject(int projectId, String title, double budget, String description) {
        String updateProjectSql = "UPDATE Projects SET Title = ?, Budget = ?, Description = ? WHERE Id = ?";
        Connection connection = getConnection();
        try (PreparedStatement updateProjectStatement = connection.prepareStatement(updateProjectSql)) {
            updateProjectStatement.setString(1, title);
            updateProjectStatement.setDouble(2, budget);
            updateProjectStatement.setString(3, description);
            updateProjectStatement.setInt(4, projectId);
            updateProjectStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves the owner ID of a project by its project ID.
     *
     * @param projectId the ID of the project.
     * @return the owner ID of the project, or -1 if the project is not found.
     */
    public int getOwnerId(int projectId) {
        String sql = "SELECT OwnerId FROM Projects WHERE Id = ?";
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("OwnerId");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }
}
