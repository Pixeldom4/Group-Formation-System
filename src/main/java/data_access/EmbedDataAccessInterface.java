package data_access;

import java.util.ArrayList;

public interface EmbedDataAccessInterface {

    void saveEmbedData(float[] embedding, int id);
    void saveEmbedData(String data, int id);
    int[] getClosestProjects(float[] input, int amount);
}
