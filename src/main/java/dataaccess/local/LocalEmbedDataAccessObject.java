package dataaccess.local;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import dataaccess.DAOImplementationConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class LocalEmbedDataAccessObject implements EmbedDataAccessInterface {

    private String FILE_PATH = DAOImplementationConfig.getProjectCSVPath() + "embeds.csv";
    private final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();
    private HashMap<Integer, float[]> embeddings = new HashMap<Integer, float[]>();
    private String[] header = {"projectId", "embedding"};

    /**
     * Creates a new LocalEmbedDataAccessObject.
     * Reads the embeddings from a CSV file if it exists.
     */
    public LocalEmbedDataAccessObject() {
        File f = new File(FILE_PATH);
        try {
            Files.createDirectories(f.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (f.exists() && !f.isDirectory()) {
            readFromCSV();
        }
    }

    /**
     * Creates a new LocalEmbedDataAccessObject with the given path as the save location.
     * Reads the embeddings from the CSV file if it exists.
     * @param path the path to the CSV file
     */
    public LocalEmbedDataAccessObject(String path) {
        FILE_PATH = path;
        File f = new File(path);
        File parent = f.getParentFile();
        try {
            Files.createDirectories(parent.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (f.exists() && !f.isDirectory()) {
            readFromCSV();
        }

    }
    @Override
    public void saveEmbedData(float[] embedding, int id) {
        embeddings.put(id, embedding);
        saveToCSV();
    }

    @Override
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
    public float[] getEmbedData(int id) {
        return embeddings.get(id);
    }

    @Override
    public HashMap<Integer, float[]> getAllEmbeddings() {
        return embeddings;
    }

    /**
     * Saves the embeddings to a CSV file.
     */
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

    /**
     * Loads the embeddings from a CSV file.
     */
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
