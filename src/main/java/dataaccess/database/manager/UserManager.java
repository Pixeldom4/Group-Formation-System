package dataaccess.database.manager;

import dataaccess.database.SQLDatabaseManager;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * Manages user-related operations in the database.
 */
public class UserManager extends SQLDatabaseManager {

    /**
     * Constructs a UserManager with the specified database name.
     *
     * @param databaseName the name of the database.
     */
    public UserManager(String databaseName) {
        super(databaseName);
    }

    /**
     * Initializes the Users table in the database.
     */
    @Override
    public void initialize() {
        String userSql = "CREATE TABLE IF NOT EXISTS Users (Id INTEGER PRIMARY KEY AUTOINCREMENT, FirstName TEXT NOT NULL, LastName TEXT NOT NULL, Email TEXT UNIQUE NOT NULL, DesiredCompensation DOUBLE, Password TEXT NOT NULL);";
        super.initializeTables(userSql);
    }

    /**
     * Creates a new user in the database.
     *
     * @param email               the user's email address.
     * @param firstName           the user's first name.
     * @param lastName            the user's last name.
     * @param desiredCompensation the user's desired compensation.
     * @param password            the user's password.
     * @return the created User object, or null if the operation fails.
     */
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

    /**
     * Retrieves a user from the database by their email address.
     *
     * @param email the user's email address.
     * @return the User object if found, or null otherwise.
     */
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

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param userId the user's ID.
     * @return the User object if found, or null otherwise.
     */
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

    /**
     * Updates a user's information in the database.
     *
     * @param userId             the user's ID.
     * @param firstName          the user's first name.
     * @param lastName           the user's last name.
     * @param desiredCompensation the user's desired compensation.
     * @return true if the update was successful, false otherwise.
     */
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

    /**
     * Deletes a user from the database by their ID.
     *
     * @param userId the user's ID.
     * @return true if the deletion was successful, false otherwise.
     */
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

    /**
     * Retrieves the password for a user by their email address.
     *
     * @param email the user's email address.
     * @return the hashed password if found, or an empty string otherwise.
     */
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
