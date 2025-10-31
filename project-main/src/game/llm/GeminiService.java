package game.llm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the {@link LLMService} interface that connects to
 * Google's Gemini API to generate text responses based on input prompts.
 * <p>
 * This class builds and sends HTTP requests to the Gemini 1.5 Flash model
 * and parses the response to return generated content.
 * </p>
 *
 * <p><b>Note:</b> This implementation requires a valid Gemini API key.</p>
 *
 * @author Mohanad Al-Mansoob
 */
public class GeminiService implements LLMService {

    /**
     * The API key used for authenticating requests to the Gemini API.
     */
    private final String apiKey;

    /**
     * The complete base URL for the Gemini model endpoint including the API key.
     */
    private final String baseUrl;

    /**
     * Constructs a {@code GeminiService} instance with the given API key.
     *
     * @param apiKey the API key to access the Gemini API
     */
    public GeminiService(String apiKey) {
        this.apiKey = apiKey;
        this.baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;
    }

    /**
     * Generates text by sending a prompt to the Gemini API.
     *
     * @param prompt the input prompt to send to the model
     * @return the generated text response, or an error message if the request fails
     */
    @Override
    public String generateText(String prompt) {
        try {
            HttpURLConnection connection = setupConnection();
            String requestBody = buildRequestBody(prompt);
            sendRequest(connection, requestBody);
            String response = readResponse(connection);

            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                return "[HTTP " + status + "] " + response;
            }
            return extractReplyText(response);
        } catch (Exception e) {
            e.printStackTrace();
            return "[GeminiService error: " + e.getMessage() + "]";
        }
    }

    /**
     * Sets up the HTTP connection to the Gemini API.
     *
     * @return the configured {@code HttpURLConnection}
     * @throws Exception if the connection cannot be established
     */
    private HttpURLConnection setupConnection() throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        return connection;
    }

    /**
     * Builds the JSON request body for the Gemini API.
     *
     * @param prompt the user-provided prompt
     * @return the JSON string representing the request body
     */
    private String buildRequestBody(String prompt) {
        JSONObject part = new JSONObject().put("text", prompt);
        JSONObject innerPart = new JSONObject().put("parts", new JSONArray().put(part));
        JSONObject outer = new JSONObject().put("contents", new JSONArray().put(innerPart));
        return outer.toString();
    }

    /**
     * Sends the request body through the given HTTP connection.
     *
     * @param connection the open connection to the Gemini API
     * @param body the JSON string to send as the request body
     * @throws Exception if the request fails to send
     */
    private void sendRequest(HttpURLConnection connection, String body) throws Exception {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = body.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    /**
     * Reads the response from the Gemini API.
     *
     * @param connection the HTTP connection with a pending response
     * @return the response body as a string
     * @throws Exception if the response cannot be read
     */
    private String readResponse(HttpURLConnection connection) throws Exception {
        int status = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                (status == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream()),
                StandardCharsets.UTF_8
        ));

        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line.trim());
        }
        reader.close();
        return responseBuilder.toString();
    }

    /**
     * Extracts the reply text from the JSON response returned by the Gemini API.
     *
     * @param response the raw JSON response as a string
     * @return the extracted reply text, or a fallback message if extraction fails
     */
    private String extractReplyText(String response) {
        try {
            JSONObject json = new JSONObject(response);
            String reply = json.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
            return reply
                    .replaceAll("^\\s*[`']{3}json\\s*", "")
                    .replaceAll("[`']{3}\\s*$", "")
                    .trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "[Failed to extract reply text]";
        }
    }
}
