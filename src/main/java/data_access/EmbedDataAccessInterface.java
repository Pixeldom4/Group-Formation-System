package data_access;


public interface EmbedDataAccessInterface {

    void saveEmbedData(float[] embedding, int id);
    void saveEmbedData(String data, int id);
    void removeEmbedData(int id);
    int[] getClosestProjects(float[] input, int amount);
}
