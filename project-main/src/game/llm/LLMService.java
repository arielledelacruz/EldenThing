package game.llm;

/**
 * An interface for integrating a Large Language Model (LLM) service.
 * <p>
 * Classes implementing this interface are expected to connect to an LLM backend and return
 * generated text based on the provided prompt.
 * </p>
 *
 * <p>Implementations may call an external API or use a local model to generate the text response.</p>
 *
 * <p><b>Example usage:</b>
 * <pre>{@code
 * LLMService llm = new OpenAILLMService();
 * String response = llm.generateText("Tell me a story about a brave knight.");
 * }</pre>
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public interface LLMService {

    /**
     * Generates a text response based on the provided prompt.
     *
     * @param prompt the input prompt to send to the language model
     * @return the generated text response from the model
     */
    String generateText(String prompt);

}
