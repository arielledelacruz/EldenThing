package game.dialogue;

import game.llm.LLMService;

/**
 * Manages the generation and parsing of dialogues using an {@link LLMService}.
 * <p>
 * This class sends prompts to a language model and parses the returned JSON
 * into a {@link Dialogue} object using the {@link JsonDialogueParser}.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public class DialogueManager {

    /**
     * The language model service used to generate text.
     */
    private final LLMService llmService;

    /**
     * The parser used to convert LLM-generated JSON into a Dialogue.
     */
    private final DialogueParser parser;

    /**
     * Constructs a new {@code DialogueManager} with the specified LLM service.
     *
     * @param llmService the LLM service used to generate dialogue content
     */
    public DialogueManager(LLMService llmService, DialogueParser parser) {

        this.llmService = llmService;
        this.parser = parser;

    }

    /**
     * Generates a {@link Dialogue} by sending a prompt to the LLM and parsing the JSON response.
     * <p>
     * The prompt is prefixed with instructions for the LLM to respond with JSON only.
     * </p>
     *
     * @param npcPrompt the prompt describing the dialogue scenario or NPC behavior
     * @return the generated {@link Dialogue}
     * @throws DialogueParsingException if the JSON response cannot be parsed correctly
     */
    public Dialogue generateDialogue(String npcPrompt) throws DialogueParsingException {
        // 1. Prepend only‐JSON instruction:
        String fullPrompt = """
            You must reply ONLY in valid JSON. Do not include any additional text.
            
            %s
            """.formatted(npcPrompt);

        // 2. Send that wrapped prompt to the LLM
        String rawJson = llmService.generateText(fullPrompt);

        // 3. Parse whatever came back (expecting JSON) into a Dialogue
        return parser.parse(rawJson);
    }

}
