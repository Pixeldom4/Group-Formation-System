package dataaccess.database.manager;

import dataaccess.database.SQLDatabaseManager;
import entities.User;

import java.sql.*;
import java.util.HashSet;

public class UserManager extends SQLDatabaseManager {

    public UserManager(String databaseName) {
        super(databaseName);
    }

    /**
     * Initializes the database with the required tables if they do not already exist.
     */
    @Override
    public void initialize() {
        String userSql = "CREATE TABLE IF NOT EXISTS Users (Id INTEGER PRIMARY KEY AUTOINCREMENT, FirstName TEXT NOT NULL, LastName TEXT NOT NULL, Email TEXT UNIQUE NOT NULL, DesiredCompensation DOUBLE, Password TEXT NOT NULL);";
        super.initializeTables(userSql);
    }

    public User createUser(String email, String firstName, String lastName, double desiredCompensation, String password) {
        String sql = "INSERT INTO Users (FirstName, LastName, Email, DesiredCompensation, Password) VALUES (?, ?, ?, ?, ?)";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setDouble(4, desiredCompensation);
            preparedStatement.setString(5, password);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        int userId = keys.getInt(1);
                        return new User(userId, firstName, lastName, email, new HashSet<>(), desiredCompensation);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT Id, FirstName, LastName, DesiredCompensation FROM Users WHERE Email = ?";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("Id");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    double desiredCompensation = rs.getDouble("DesiredCompensation");
                    return new User(userId, firstName, lastName, email, new HashSet<>(), desiredCompensation);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public User getUserById(int userId) {
        String sql = "SELECT FirstName, LastName, Email, DesiredCompensation FROM Users WHERE Id = ?";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String email = rs.getString("Email");
                    double desiredCompensation = rs.getDouble("DesiredCompensation");
                    return new User(userId, firstName, lastName, email, new HashSet<>(), desiredCompensation);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean updateUser(int userId, String firstName, String lastName, double desiredCompensation) {
        String sql = "UPDATE Users SET FirstName = ?, LastName = ?, DesiredCompensation = ? WHERE Id = ?";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDouble(3, desiredCompensation);
            preparedStatement.setInt(4, userId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE Id = ?";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public String getPasswordByEmail(String email) {
        String sql = "SELECT Password FROM Users WHERE Email = ?";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}
