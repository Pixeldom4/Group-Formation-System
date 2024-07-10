package use_case.SearchingForProjects;

import Entities.ProjectInterface;
import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import data_access.DAOImplementationConfig;
import data_access.local.EmbedDataAccessInterface;
import data_access.IProjectRepository;
import data_access.local.LocalProjectDataAccessObject;

import java.util.*;

public class LocalProjectSearchObject implements ProjectSearchInterface {

    private final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();
    private IProjectRepository projectDataAccess = DAOImplementationConfig.getProjectDataAccess();

    public LocalProjectSearchObject() {

    }

    /**
     * Creates a new LocalProjectSearchObject using the given project repository.
     * Used for testing where files are not stored in the project folder.
     * @param projectRepository the project repository to use
     */
    public LocalProjectSearchObject(IProjectRepository projectRepository){
        projectDataAccess = projectRepository;
    }

    @Override
    public ArrayList<ProjectInterface> searchProjects(String query, int amount) {
        float[] queryEmbedding = embeddingAPI.getEmbedData(query);

        Map<Integer, Float> cosineSimilarityMap = new LinkedHashMap<>();
        HashMap<Integer, float[]> dataVector = projectDataAccess.getAllEmbeddings(); //<projectId, embedding>
        for (Map.Entry<Integer, float[]> data : dataVector.entrySet()) {
            cosineSimilarityMap.put(data.getKey(), calcCosineSimilarity(queryEmbedding, data.getValue()));
        }
        cosineSimilarityMap = sortByValue(cosineSimilarityMap);

        ArrayList<ProjectInterface> result = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            if (cosineSimilarityMap.isEmpty()) {
                break;
            }
            int projectId = cosineSimilarityMap.keySet().iterator().next();
            result.add(projectDataAccess.getProjectById(projectId));
            cosineSimilarityMap.remove(projectId);
        }

        return result;
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private float calcCosineSimilarity(float[] vectorA, float[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return (float) (dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)));
    }
}
