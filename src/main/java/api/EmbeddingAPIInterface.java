package api;

/**
 * Interface for generating embeddings for text.
 */
public interface EmbeddingAPIInterface {
    /**
     * Returns an embedding for the given text.
     *
     * @param text the text to be used for embedding
     * @return an array of floats representing the embedding
     */
    float[] getEmbedData(String text);
}
