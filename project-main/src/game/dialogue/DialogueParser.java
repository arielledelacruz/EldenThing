package game.dialogue;

/**
 * An interface for parsing dialogue data from a specific format (e.g., JSON, XML, etc.)
 * into a {@link Dialogue} object.
 * <p>
 * Implementing classes are responsible for converting a given string representation
 * of dialogue into a usable {@code Dialogue} instance, and may throw a
 * {@link DialogueParsingException} if the input format is invalid or cannot be parsed.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public interface DialogueParser {

    /**
     * Parses the given input string and returns a {@link Dialogue} object.
     *
     * @param parserFormat the string to parse, typically in a specific format (e.g., JSON)
     * @return a {@code Dialogue} object representing the parsed input
     * @throws DialogueParsingException if the input cannot be parsed
     */
    Dialogue parse(String parserFormat) throws DialogueParsingException;

}
