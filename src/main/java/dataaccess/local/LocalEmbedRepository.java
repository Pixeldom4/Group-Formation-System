package dataaccess.local;

import api.embeddingapi.EmbeddingAPIInterface;
import api.embeddingapi.OpenAPIDataEmbed;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import config.DataAccessConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Local implementation of the ILocalEmbedRepository interface.
 * Manages embedding data using CSV files for storage.
 */
public class LocalEmbedRepository implements ILocalEmbedRepository {

    private String FILE_PATH = DataAccessConfig.getProjectCSVPath() + "embeds.csv";
    private final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();
    private final HashMap<Integer, float[]> embeddings = new HashMap<>();
    private final String[] header = {"projectId", "embedding"};

    /**
     * Creates a new LocalEmbedRepository.
     * Reads the embeddings from a CSV file if it exists.
     */
    public LocalEmbedRepository() {
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
     * Creates a new LocalEmbedRepository with the given path as the save location.
     * Reads the embeddings from the CSV file if it exists.
     *
     * @param path the path to the CSV file
     */
    public LocalEmbedRepository(String path) {
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

    /**
     * Saves an embedding for a project.
     *
     * @param embedding the embedding of the project
     * @param id the id of the project
     */
    @Override
    public void saveEmbedData(float[] embedding, int id) {
        embeddings.put(id, embedding);
        saveToCSV();
    }

    /**
     * Saves an embedding for a project using data.
     *
     * @param data the data of the project to be used for embedding
     * @param id the id of the project
     */
    @Override
    public void saveEmbedData(String data, int id) {
        embeddings.put(id, embeddingAPI.getEmbedData(data));
        saveToCSV();
    }

    /**
     * Removes an embedding for a project.
     *
     * @param id the id of the project
     */
    @Override
    public void removeEmbedData(int id) {
        embeddings.remove(id);
        saveToCSV();
    }

    /**
     * Retrieves an embedding for a project.
     *
     * @param id the id of the project
     * @return the embedding of the project
     */
    @Override
    public float[] getEmbedData(int id) {
        return embeddings.get(id);
    }

    /**
     * Retrieves all embeddings and their associated project ids.
     *
     * @return a hashmap where the key is the project id and the value is the embedding
     */
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
                for (int i = 0; i < embedding.length; i++) {
                    floatArray[i] = embedding[i];
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