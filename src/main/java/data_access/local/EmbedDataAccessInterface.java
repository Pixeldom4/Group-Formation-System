package data_access.local;


import java.util.HashMap;

public interface EmbedDataAccessInterface {

    void saveEmbedData(float[] embedding, int id);
    void saveEmbedData(String data, int id);
    void removeEmbedData(int id);
    HashMap<Integer, float[]> getAllEmbeddings();
}
