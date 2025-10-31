package game;

import edu.monash.fit2099.engine.displays.Display;

import java.util.Scanner;

/**
 * An extended version of the {@link Display} class that supports reading user input from the terminal.
 * <p>
 * This class allows interactive dialogue by printing a prompt and reading a line of user input.
 * It is useful for debugging, testing, or scenarios where player text input is needed.
 * </p>
 *
 * Example usage:
 * <pre>
 *     ExtendedDisplay display = new ExtendedDisplay();
 *     String name = display.readLine("Enter your name: ");
 * </pre>
 *
 * @author Mohanad Al-Mansoob
 */
public class ExtendedDisplay extends Display {

    /**
     * Scanner used to read user input from standard input (System.in).
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prints the given prompt and waits for the user to enter a line of text.
     *
     * @param prompt The text prompt to show before waiting for input.
     * @return The line entered by the user.
     */
    public String readLine(String prompt) {
        this.print(prompt);
        return scanner.nextLine();
    }



}
