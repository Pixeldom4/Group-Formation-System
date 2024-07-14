package dataaccess.database;

import dataaccess.Database;
import dataaccess.IUserProjectsRepository;
import entities.User;
import dataaccess.IUserRepository;
import org.apache.commons.lang3.NotImplementedException;

import java.sql.*;
import java.util.HashSet;

/**
 * A class representing the User Repository. In particular, this class manages the User table.
 */
public class UserRepository extends SQLDatabaseManager implements IUserRepository {
    private final IUserProjectsRepository userProjectsRepository;
    /**
     * Constructs a UserRepository object.
     *
     * @param databaseName The name of the database to manage. Note that this must include a '.db' file extension.
     */
    public UserRepository(String databaseName, IUserProjectsRepository userProjectsRepository) {
        super(databaseName);
        this.userProjectsRepository = userProjectsRepository;
    }

    /**
     * Initializes the database with the required tables if they do not already exist.
     */
    @Override
    public void initialize() {
        String userSql = "CREATE TABLE IF NOT EXISTS Users (Id INTEGER PRIMARY KEY AUTOINCREMENT, FirstName TEXT NOT NULL, LastName TEXT NOT NULL, Email TEXT UNIQUE NOT NULL, DesiredCompensation DOUBLE, Password TEXT NOT NULL);";
        String userTagsSql = "CREATE TABLE IF NOT EXISTS UserTags (UserId INTEGER NOT NULL, Tag TEXT NOT NULL, PRIMARY KEY(UserId, Tag), FOREIGN KEY(UserId) REFERENCES Users(Id))";
        super.initializeTables(userSql, userTagsSql);
    }

    /**
     * Adds a set of tags to the database. More specifically, the tags are added to the
     * UserTags table.
     *
     * @param userId the id of the project to add the tags to.
     * @param tags a set of tags associated with the project.
     */
    @Override
    public void addTags(int userId, HashSet<String> tags) {
        String sql = "INSERT INTO UserTags (UserId, Tag) VALUES (?, ?)";

        executeTagUpdates(userId, tags, sql);
    }

    /**
     * Removes a set of tags to the database. More specifically, the tags are removed from the
     * ProjectTags table.
     *
     * @param userId the id of the project to add the tags to.
     * @param tags a set of tags associated with the project.
     */
    @Override
    public void removeTags(int userId, HashSet<String> tags) {
        String sql = "DELETE FROM UserTags WHERE UserId = ? AND Tag = ?";

        executeTagUpdates(userId, tags, sql);
    }

    /**
     * Executes a batch of SQL statements for tag updates.
     *
     * @param userId the id of the user.
     * @param tags a set of tags associated with the project.
     * @param sql the SQL statement to execute in batch.
     */
    private void executeTagUpdates(int userId, HashSet<String> tags, String sql) {
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (String tag : tags) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, tag);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Creates/Registers a user in the database. Note that all validation should be done
     * prior to this method in accordance with clean architecture. Particularly, validation
     * should be done in the service class / (data access object ?).
     *
     * @param email the user's Email address.
     * @param firstName the user's first name.
     * @param lastName the user's last name.
     * @param password the user's password.
     * @return a User object corresponding to the created user. Otherwise, null.
     */
    @Override
    public User createUser(String email, String firstName, String lastName, HashSet<String> tags, double desiredCompensation, String password) {
        String sql = "INSERT INTO Users (FirstName, LastName, Email, DesiredCompensation, Password) VALUES (?, ?, ?, ?, ?)";

        Connection connection = super.getConnection();

        try {
            connection.setAutoCommit(false); // begin transaction

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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

                            this.addTags(userId, tags);

                            connection.commit(); // end transaction
                            return new User(userId, firstName, lastName, email, tags, desiredCompensation);
                        }
                    }
                }
            }
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch(SQLException rollbackException) {
                System.err.println(rollbackException.getMessage());
            }
            System.err.println(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return null;
    }

    /**
     * Gets the User object corresponding to the user with a particular Email address.
     *
     * @param email the user's Email address.
     * @return a User object corresponding to the user with Email. Returns null if the user does
     * not exist in the database.
     */
    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT Id, FirstName, LastName, DesiredCompensation FROM Users WHERE Email = ?";

        try (Connection connection = super.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("Id");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    double desiredCompensation = rs.getDouble("DesiredCompensation");

                    return new User(userId, firstName, lastName, email, this.getTagsForUser(userId), desiredCompensation);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a set of tags associated with a given project ID.
     *
     * @param userId The ID of the project to retrieve tags for.
     * @return A HashSet of tags associated with the specified project.
     */
    private HashSet<String> getTagsForUser(int userId) {
        String sql = "SELECT Tag FROM UserTags WHERE UserId = ?";
        HashSet<String> tags = new HashSet<>();

        Connection connection = super.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    tags.add(rs.getString("Tag"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tags;
    }

    /**
     * Gets the User object corresponding to the user with a particular email address.
     *
     * @param userId the user's user ID.
     * @return a User object corresponding to the user with email. Returns null if the user does
     * not exist in the database.
     */
    @Override
    public User getUserById(int userId) {
        String sql = "SELECT FirstName, LastName, Email, DesiredCompensation FROM Users WHERE Id = ?";

        try (Connection connection = super.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String email = rs.getString("Email");
                    double desiredCompensation = rs.getDouble("DesiredCompensation");

                    return new User(userId, firstName, lastName, email, this.getTagsForUser(userId), desiredCompensation);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes the user associated with userId from the database.
     *
     * @param userId the user id of the user to delete.
     */
    @Override
    public void deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE Id = ?";

        Connection connection = super.getConnection();

        try {
            connection.setAutoCommit(false); // begin transaction

            // Remove user from UserProjects table
            userProjectsRepository.removeUserFromAllProjects(userId);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.executeUpdate();
            }

            connection.commit(); // end transaction
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                System.err.println(rollbackException.getMessage());
            }
            System.err.println(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }


    /**
     * Unknown what to be updating currently.
     * Current ideas: changePassword, authenticateUser, changeEmail.
     *
     * @param user the user to update.
     */
    @Override
    public void updateUser(User user) {
        throw new NotImplementedException();
    }
}
