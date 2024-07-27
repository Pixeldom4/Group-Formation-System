package dataaccess.database.manager;

import dataaccess.database.SQLDatabaseManager;
import entities.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class ProjectManager extends SQLDatabaseManager {

    public ProjectManager(String databaseName) {
        super(databaseName);
    }

    @Override
    public void initialize() {
        String projectSql = "CREATE TABLE IF NOT EXISTS Projects (Id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT NOT NULL, Budget DOUBLE, Description TEXT NOT NULL, OwnerId INTEGER NOT NULL, FOREIGN KEY(OwnerId) REFERENCES Users(Id))";
        super.initializeTables(projectSql);
    }

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
