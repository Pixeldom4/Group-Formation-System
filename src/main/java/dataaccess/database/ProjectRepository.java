package dataaccess.database;

import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import entities.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ProjectRepository extends SQLDatabaseManager implements IProjectRepository {
    private final IUserProjectsRepository userProjectsRepository;

    /**
     * Constructs a ProjectRepository object.
     *
     * @param databaseName The name of the database to manage. Note that this must include a '.db' file extension.
     */
    public ProjectRepository(String databaseName, IUserProjectsRepository userProjectsRepository) {
        super(databaseName);
        this.userProjectsRepository = userProjectsRepository;
    }

    /**
     * Initializes the database with the required tables if they do not already exist.
     */
    @Override
    public void initialize() {
        String projectSql = "CREATE TABLE IF NOT EXISTS Projects (Id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT NOT NULL, Budget DOUBLE, Description TEXT NOT NULL, OwnerId INTEGER NOT NULL, FOREIGN KEY(OwnerId) REFERENCES Users(Id))";
        String projectTagsSql = "CREATE TABLE IF NOT EXISTS ProjectTags (ProjectId INTEGER NOT NULL, Tag TEXT NOT NULL, PRIMARY KEY(ProjectId, Tag), FOREIGN KEY(ProjectId) REFERENCES Projects(Id))";
        String projectEmbeddingSql = "CREATE TABLE IF NOT EXISTS ProjectEmbeddings (ProjectId INTEGER NOT NULL, EmbeddingIndex INTEGER NOT NULL, EmbeddingValue FLOAT NOT NULL, PRIMARY KEY (ProjectId, EmbeddingIndex), FOREIGN KEY(ProjectId) REFERENCES Projects(Id))";
        super.initializeTables(projectSql, projectTagsSql, projectEmbeddingSql);
    }

    /**
     * Adds a set of tags to the database. More specifically, the tags are added to the
     * ProjectTags table.
     *
     * @param projectId the id of the project to add the tags to.
     * @param tags a set of tags associated with the project.
     */
    @Override
    public boolean addTags(int projectId, HashSet<String> tags) {
        String sql = "INSERT INTO ProjectTags (ProjectId, Tag) VALUES (?, ?)";

        return executeTagUpdates(projectId, tags, sql);
    }

    /**
     * Removes a set of tags to the database. More specifically, the tags are removed from the
     * ProjectTags table.
     *
     * @param projectId the id of the project to add the tags to.
     * @param tags a set of tags associated with the project.
     */
    @Override
    public boolean removeTags(int projectId, HashSet<String> tags) {
        String sql = "DELETE FROM ProjectTags WHERE ProjectId = ? AND Tag = ?";

        return executeTagUpdates(projectId, tags, sql);
    }

    /**
     * Executes a batch of SQL statements for tag updates.
     *
     * @param projectId the id of the project.
     * @param tags a set of tags associated with the project.
     * @param sql the SQL statement to execute in batch.
     */
    private boolean executeTagUpdates(int projectId, HashSet<String> tags, String sql) {
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (String tag : tags) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setString(2, tag);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

            return true;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Creates a project in the database. Note that all validation should be done
     * prior to this method in accordance to clean architecture. Particularly, validation
     * should be done in the service class / (data access object ?).
     *
     * @param title the title of the project.
     * @param budget the budget of the project.
     * @param description the description of the project.
     * @param tags a set of tags associated with the project.
     * @return a Project object corresponding to the created project. Otherwise, null.
     */
    @Override
    public Project createProject(String title, double budget, String description, HashSet<String> tags, float[] embeddings, int ownerId) {
        String projectSql = "INSERT INTO Projects (Title, Budget, Description, OwnerId) VALUES (?, ?, ?, ?)";
        String embeddingSql = "INSERT INTO ProjectEmbeddings (ProjectId, EmbeddingIndex, EmbeddingValue) VALUES (?, ?, ?)";

        Connection connection = super.getConnection();

        try {
            connection.setAutoCommit(false); // begin transaction

            try (PreparedStatement projectStatement = connection.prepareStatement(projectSql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement embeddingStatement = connection.prepareStatement(embeddingSql)) {
                projectStatement.setString(1, title);
                projectStatement.setDouble(2, budget);
                projectStatement.setString(3, description);
                projectStatement.setInt(4, ownerId);

                int affectedRows = projectStatement.executeUpdate();

                if (affectedRows > 0) { // project was added => key generated
                    try (ResultSet keys = projectStatement.getGeneratedKeys()) {
                        if (keys.next()) {
                            int projectId = keys.getInt(1);

                            this.addTags(projectId, tags); // insert tags into ProjectTags table

                            // insert embeddings into ProjectEmbeddings table
                            for (int i = 0; i < embeddings.length; i++) {
                                embeddingStatement.setInt(1, projectId);
                                embeddingStatement.setInt(2, i);
                                embeddingStatement.setFloat(3, embeddings[i]);
                                embeddingStatement.addBatch();
                            }
                            embeddingStatement.executeBatch();

                            connection.commit(); // end transaction
                            return new Project(projectId, title, budget, description, tags);
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
     * Deletes the project associated with the given project ID from the database.
     *
     * @param projectId The ID of the project to delete.
     */
    @Override
    public boolean deleteProject(int projectId) {
        String deleteProjectSql = "DELETE FROM Projects WHERE Id = ?";
        String deleteProjectTagSql  = "DELETE FROM ProjectTags WHERE ProjectId = ?";
        String deleteProjectEmbeddingSql = "DELETE FROM ProjectEmbeddings WHERE ProjectId = ?";

        Connection connection = super.getConnection();

        try {
            connection.setAutoCommit(false); // begin transaction

            // Remove project from UserProjects table
            userProjectsRepository.removeProjectFromAllUsers(projectId);

            try (PreparedStatement deleteProjectStatement = connection.prepareStatement(deleteProjectSql);
                 PreparedStatement deleteProjectTagsStatement = connection.prepareStatement(deleteProjectTagSql);
                 PreparedStatement deleteProjectEmbeddingStatement = connection.prepareStatement(deleteProjectEmbeddingSql)) {
                deleteProjectStatement.setInt(1, projectId);
                deleteProjectStatement.executeUpdate();

                deleteProjectTagsStatement.setInt(1, projectId);
                deleteProjectTagsStatement.executeUpdate();

                deleteProjectEmbeddingStatement.setInt(1, projectId);
                deleteProjectEmbeddingStatement.executeUpdate();

                connection.commit(); // end transaction

                return true;
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
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return false;
    }

    /**
     * Retrieves a project from the database by its ID.
     *
     * @param projectId The ID of the project to retrieve.
     * @return A Project object representing the retrieved project, or null if the project is not found.
     */
    @Override
    public Project getProjectById(int projectId) {
        String sql = "SELECT Title, Budget, Description FROM Projects WHERE Id = ?";

        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("Title");
                    double budget = rs.getDouble("Budget");
                    String description = rs.getString("Description");
                    HashSet<String> tags = this.getTagsForProject(projectId);
                    return new Project(projectId, title, budget, description, tags);
                }
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a set of projects that match the given keyword in their title, description, or tags.
     *
     * @param keyword The keyword to search for in the project title, description, and tags.
     * @return A HashSet of Project objects that match the search criteria.
     */
    @Override
    public HashSet<Project> getProjectsByKeyword(String keyword) {
        HashSet<Project> projects = new HashSet<>();

        String sql = "SELECT DISTINCT Projects.Id, Projects.Title, Projects.Budget, Projects.Description FROM Projects " +
                "LEFT JOIN ProjectTags ON Projects.Id = ProjectTags.ProjectId " +
                "WHERE Projects.Title LIKE ? OR Projects.Description LIKE ? OR ProjectTags.Tag LIKE ?;";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            String queryKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, queryKeyword);
            preparedStatement.setString(2, queryKeyword);
            preparedStatement.setString(3, queryKeyword);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int projectId = rs.getInt("Id");
                    String title = rs.getString("Title");
                    double budget = rs.getDouble("Budget");
                    String description = rs.getString("Description");
                    HashSet<String> tags = this.getTagsForProject(projectId);

                    projects.add(new Project(projectId, title, budget, description, tags));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return projects;
    }

    /**
     * Updates an existing project in the database with new details, tags, and embeddings.
     * The method updates the project's title, budget, and description, deletes the old tags and embeddings,
     * and inserts the new tags and embeddings.
     *
     * @param projectId   The ID of the project to update.
     * @param title       The new title of the project.
     * @param budget      The new budget of the project.
     * @param description The new description of the project.
     * @param tags        The new set of tags associated with the project.
     * @param embeddings  The new array of embeddings associated with the project.
     */
    @Override
    public boolean update(int projectId, String title, double budget, String description, HashSet<String> tags, float[] embeddings) {
        String updateProjectSql = "UPDATE Projects SET Title = ?, Budget = ?, Description = ? WHERE Id = ?";
        String deleteTagsSql = "DELETE FROM ProjectTags WHERE ProjectId = ?";
        String deleteEmbeddingsSql = "DELETE FROM ProjectEmbeddings WHERE ProjectId = ?";
        String insertTagSql = "INSERT INTO ProjectTags (ProjectId, Tag) VALUES (?, ?)";
        String insertEmbeddingSql = "INSERT INTO ProjectEmbeddings (ProjectId, EmbeddingIndex, EmbeddingValue) VALUES (?, ?, ?)";

        Connection connection = super.getConnection();

        try {
            connection.setAutoCommit(false); // begin transaction

            try (PreparedStatement updateProjectStatement = connection.prepareStatement(updateProjectSql);
                 PreparedStatement deleteTagsStatement = connection.prepareStatement(deleteTagsSql);
                 PreparedStatement deleteEmbeddingsStatement = connection.prepareStatement(deleteEmbeddingsSql);
                 PreparedStatement insertTagStatement = connection.prepareStatement(insertTagSql);
                 PreparedStatement insertEmbeddingStatement = connection.prepareStatement(insertEmbeddingSql)) {

                // Update project details
                updateProjectStatement.setString(1, title);
                updateProjectStatement.setDouble(2, budget);
                updateProjectStatement.setString(3, description);
                updateProjectStatement.setInt(4, projectId);
                updateProjectStatement.executeUpdate();

                // Delete old tags
                deleteTagsStatement.setInt(1, projectId);
                deleteTagsStatement.executeUpdate();

                // Insert new tags
                for (String tag : tags) {
                    insertTagStatement.setInt(1, projectId);
                    insertTagStatement.setString(2, tag);
                    insertTagStatement.addBatch();
                }
                insertTagStatement.executeBatch();

                // Delete old embeddings
                deleteEmbeddingsStatement.setInt(1, projectId);
                deleteEmbeddingsStatement.executeUpdate();

                // Insert new embeddings
                for (int i = 0; i < embeddings.length; i++) {
                    insertEmbeddingStatement.setInt(1, projectId);
                    insertEmbeddingStatement.setInt(2, i);
                    insertEmbeddingStatement.setFloat(3, embeddings[i]);
                    insertEmbeddingStatement.addBatch();
                }
                insertEmbeddingStatement.executeBatch();

                connection.commit(); // end transaction

                return true;
            }

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

        return false;
    }


    /**
     * Retrieves a set of tags associated with a given project ID.
     *
     * @param projectId The ID of the project to retrieve tags for.
     * @return A HashSet of tags associated with the specified project.
     */
    private HashSet<String> getTagsForProject(int projectId) {
        String sql = "SELECT Tag FROM ProjectTags WHERE ProjectId = ?";
        HashSet<String> tags = new HashSet<>();

        Connection connection = super.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectId);
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
     * Retrieves all project embeddings from the database and returns them as a HashMap.
     * The keys in the HashMap are the project IDs, and the values are float arrays representing the embeddings.
     *
     * @return A HashMap where each key is a project ID and each value is a float array of embeddings.
     */
    public HashMap<Integer, float[]> getAllEmbeddings() {
        String sql = "SELECT ProjectId, EmbeddingIndex, EmbeddingValue FROM ProjectEmbeddings ORDER BY ProjectId, EmbeddingIndex";
        HashMap<Integer, float[]> embeddingsMap = new HashMap<>();

        Connection connection = super.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            int currentProjectId = -1;
            ArrayList<Float> currentEmbeddingList = new ArrayList<>();

            while (rs.next()) {
                int projectId = rs.getInt("ProjectId");
                float embeddingValue = rs.getFloat("EmbeddingValue");

                if (projectId != currentProjectId) { // check whether we moved to a new project
                    storeEmbedding(embeddingsMap, currentProjectId, currentEmbeddingList);

                    currentProjectId = projectId;
                    currentEmbeddingList.clear();
                }
                currentEmbeddingList.add(embeddingValue);
            }

            // add last project's embeddings to the map
            storeEmbedding(embeddingsMap, currentProjectId, currentEmbeddingList);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return embeddingsMap;
    }

    /**
     * Stores the current project's embeddings in the provided HashMap.
     * Converts the ArrayList of embedding values to a float array and maps it to the current project ID.
     *
     * @param embeddingsMap The HashMap to store the project embeddings.
     * @param currentProjectId The ID of the current project.
     * @param currentEmbeddingList The list of embedding values for the current project.
     */
    private void storeEmbedding(HashMap<Integer, float[]> embeddingsMap, int currentProjectId, ArrayList<Float> currentEmbeddingList) {
        if (currentProjectId != -1) {
            // convert ArrayList to float array and store in the map
            float[] embeddingArray = new float[currentEmbeddingList.size()];
            for (int i = 0; i < currentEmbeddingList.size(); i++) {
                embeddingArray[i] = currentEmbeddingList.get(i);
            }
            embeddingsMap.put(currentProjectId, embeddingArray);
        }
    }

    /**
     * Retrieves the owner ID of a project from the database by its project ID.
     *
     * @param projectId The ID of the project whose owner ID is to be retrieved.
     * @return The ID of the user who owns the project, or -1 if the project is not found.
     */
    @Override
    public int getOwnerId(int projectId) {
        String sql = "SELECT OwnerId FROM Projects WHERE Id = ?";
        Connection connection = super.getConnection();

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
        return -1; // Return -1 if the project is not found or an error occurs
    }
}