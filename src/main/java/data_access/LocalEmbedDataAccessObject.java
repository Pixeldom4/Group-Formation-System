package data_access;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import com.spotify.voyager.jni.Index;
import java.io.File;
import java.io.IOException;

public class LocalEmbedDataAccessObject implements EmbedDataAccessInterface {

    private final String FILE_PATH = DAOImplementationConfig.getProjectCSVPath() + "embeds.voy";
    private final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();
    private Index data_index = new Index(Index.SpaceType.Cosine, 1536);

    public LocalEmbedDataAccessObject() {
        File f = new File(FILE_PATH);
        if (f.exists() && !f.isDirectory()) {
            data_index = Index.load(FILE_PATH);
        }


    }
    @Override
    public void saveEmbedData(float[] embedding, int id) {
        data_index.addItem(embedding, id);
        data_index.saveIndex(FILE_PATH);
    }

    public void saveEmbedData(String data, int id) {
        data_index.addItem(embeddingAPI.getEmbedData(data), id);
        data_index.saveIndex(FILE_PATH);
    }

    @Override
    public void removeEmbedData(int id) {
        data_index.markDeleted(id);
        data_index.saveIndex(FILE_PATH);
    }

    @Override
    public int[] getClosestProjects(float[] input, int amount) {
        Index.QueryResults results = data_index.query(input, amount);
        int[] intLabels = new int[results.labels.length];

        // Convert each element from long to int
        for (int i = 0; i < results.labels.length; i++) {
            long value = results.labels[i];
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("Value out of range for integers: " + value);
            }
            intLabels[i] = (int) value;
        }
        return intLabels;
    }
}
