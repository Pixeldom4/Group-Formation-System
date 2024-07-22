package api;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A class that implements the EmbeddingAPIInterface to interact with the OpenAI API for generating text embeddings.
 */
public class OpenAPIDataEmbed implements EmbeddingAPIInterface {

    private static final String API_URL = "https://api.openai.com/v1/embeddings";
    private static final String API_MODEL = "text-embedding-3-small";
    private static final String API_TOKEN = System.getenv("API_KEY"); // Note: This should typically be fetched from environment variables.

    /**
     * Constructs an OpenAPIDataEmbed object.
     */
    public OpenAPIDataEmbed() {
    }

    /**
     * Returns an embedding for the given text by calling the OpenAI API.
     *
     * @param text the text to be used for embedding
     * @return an array of floats representing the embedding
     */
    @Override
    public float[] getEmbedData(String text) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject requestBody = new JSONObject();
        requestBody.put("input", text);
        requestBody.put("model", API_MODEL);
        RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        Request request = new Request.Builder()
                .url(API_URL)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .addHeader("Content-Type", "application/json")
                .build();

        JSONObject responseBody = null;

        try {
            Response response = client.newCall(request).execute();
            responseBody = new JSONObject(response.body().string());
            JSONObject embedData = (JSONObject) responseBody.getJSONArray("data").get(0);
            float[] data = new float[embedData.getJSONArray("embedding").length()];
            for (int i = 0; i < embedData.getJSONArray("embedding").length(); i++) {
                data[i] = embedData.getJSONArray("embedding").getFloat(i);
            }
            return data;
        } catch (IOException | JSONException e) {
            System.out.println(responseBody);
            throw new RuntimeException(e);
        }
    }
}
