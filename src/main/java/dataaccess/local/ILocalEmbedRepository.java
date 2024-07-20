package dataaccess.local;


import java.util.HashMap;

public interface ILocalEmbedRepository {

    /**
     * Saves an embedding for a project.
     * @param embedding the embedding of the project
     * @param id the id of the project
     */
    void saveEmbedData(float[] embedding, int id);

    /**
     * Saves an embedding for a project.
     * @param data the data of the project to be used for embedding
     * @param id the id of the project
     */
    void saveEmbedData(String data, int id);

    /**
     * Removes an embedding for a project.
     * @param id the id of the project
     */
    void removeEmbedData(int id);

    /**
     * Retrieves an embedding for a project.
     * @param id the id of the project
     * @return the embedding of the project
     */
    float[] getEmbedData(int id);

    /**
     * Retrieves all embeddings and their associated project ids.
     * @return a hashmap where the key is the project id and the value is the embedding
     */
    HashMap<Integer, float[]> getAllEmbeddings();
}
