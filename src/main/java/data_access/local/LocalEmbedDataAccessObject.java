package data_access.local;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.spotify.voyager.jni.Index;
import data_access.DAOImplementationConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LocalEmbedDataAccessObject implements EmbedDataAccessInterface {

    private final String FILE_PATH = DAOImplementationConfig.getProjectCSVPath() + "embeds.csv";
    private final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();
    private Index data_index = new Index(Index.SpaceType.Cosine, 1536);
    private HashMap<Integer, float[]> embeddings = new HashMap<Integer, float[]>();
    private String[] header = {"projectId", "embedding"};

    public LocalEmbedDataAccessObject() {
        File f = new File(FILE_PATH);
        if (f.exists() && !f.isDirectory()) {
            readFromCSV();
        }

    }
    @Override
    public void saveEmbedData(float[] embedding, int id) {
        embeddings.put(id, embedding);
        saveToCSV();
    }

    public void saveEmbedData(String data, int id) {
        embeddings.put(id ,embeddingAPI.getEmbedData(data));
        saveToCSV();
    }

    @Override
    public void removeEmbedData(int id) {
        embeddings.remove(id);
        saveToCSV();
    }

    @Override
    public HashMap<Integer, float[]> getAllEmbeddings() {
        return embeddings;
    }

    private void saveToCSV() {
        CSVWriter writer;

        try {
            writer = new CSVWriter(new FileWriter(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writer.writeNext(header);
        for (Map.Entry<Integer, float[]> data : embeddings.entrySet()) {
            String[] row = {String.valueOf(data.getKey()), Arrays.toString(data.getValue())};
            writer.writeNext(row);
        }

        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromCSV() {
        CSVReader reader;

        try {
            reader = new CSVReader(new FileReader(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] line;
        try {
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                int projectId = Integer.parseInt(line[0]);
                Float[] embedding = Arrays.stream(line[1].replace("[", "").replace("]", "").split(",")).map(Float::valueOf).toArray(Float[]::new);
                float[] floatArray = new float[embedding.length];
                for (int i = 0 ; i < embedding.length; i++) {
                    floatArray[i] = (float) embedding[i];
                }
                embeddings.put(projectId, floatArray);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
