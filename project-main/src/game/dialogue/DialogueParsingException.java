package game.dialogue;

/**
 * Exception thrown when an error occurs while parsing dialogue data.
 * <p>
 * This exception is typically used by classes implementing the {@link DialogueParser}
 * interface to indicate that the input string could not be parsed into a valid {@link Dialogue}
 * object due to syntax errors, format issues, or other parsing failures.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public class DialogueParsingException extends Exception {

    /**
     * Constructs a new {@code DialogueParsingException} with the specified detail message and cause.
     *
     * @param message a detail message explaining the reason for the exception
     * @param cause the underlying cause of the parsing failure
     */
    public DialogueParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
