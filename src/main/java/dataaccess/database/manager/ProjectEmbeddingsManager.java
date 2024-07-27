package dataaccess.database.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages project embeddings-related operations in the database.
 */
public class ProjectEmbeddingsManager extends SQLDatabaseManager {

    /**
     * Constructs a ProjectEmbeddingsManager with the specified database name.
     *
     * @param databaseName the name of the database.
     */
    public ProjectEmbeddingsManager(String databaseName) {
        super(databaseName);
    }

    @Override
    public void initialize() {
        String projectEmbeddingSql = "CREATE TABLE IF NOT EXISTS ProjectEmbeddings (ProjectId INTEGER NOT NULL, EmbeddingIndex INTEGER NOT NULL, EmbeddingValue FLOAT NOT NULL, PRIMARY KEY (ProjectId, EmbeddingIndex), FOREIGN KEY(ProjectId) REFERENCES Projects(Id))";
        super.initializeTables(projectEmbeddingSql);
    }

    /**
     * Adds embeddings for a project in the database.
     *
     * @param projectId  the ID of the project.
     * @param embeddings the embeddings to add.
     * @return true if the embeddings were added successfully, false otherwise.
     */
    public boolean addEmbeddings(int projectId, float[] embeddings) {
        String embeddingSql = "INSERT INTO ProjectEmbeddings (ProjectId, EmbeddingIndex, EmbeddingValue) VALUES (?, ?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement embeddingStatement = connection.prepareStatement(embeddingSql)) {
            for (int i = 0; i < embeddings.length; i++) {
                embeddingStatement.setInt(1, projectId);
                embeddingStatement.setInt(2, i);
                embeddingStatement.setFloat(3, embeddings[i]);
                embeddingStatement.addBatch();
            }
            embeddingStatement.executeBatch();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Removes embeddings for a project from the database.
     *
     * @param projectId the ID of the project.
     * @return true if the embeddings were removed successfully, false otherwise.
     */
    public boolean removeEmbeddings(int projectId) {
        String deleteEmbeddingsSql = "DELETE FROM ProjectEmbeddings WHERE ProjectId = ?";
        Connection connection = getConnection();
        try (PreparedStatement deleteEmbeddingStatement = connection.prepareStatement(deleteEmbeddingsSql)) {
            deleteEmbeddingStatement.setInt(1, projectId);
            deleteEmbeddingStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves all project embeddings from the database.
     *
     * @return a HashMap where the keys are project IDs and the values are embeddings.
     */
    public HashMap<Integer, float[]> getAllEmbeddings() {
        String sql = "SELECT ProjectId, EmbeddingIndex, EmbeddingValue FROM ProjectEmbeddings ORDER BY ProjectId, EmbeddingIndex";
        HashMap<Integer, float[]> embeddingsMap = new HashMap<>();
        Connection connection = getConnection();
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
     *
     * @param embeddingsMap       the HashMap to store the embeddings.
     * @param currentProjectId    the current project ID.
     * @param currentEmbeddingList the list of embeddings for the current project.
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
}
