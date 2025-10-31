package game.dialogue;

import java.util.List;

/**
 * Represents a structured dialogue system between a player and an NPC.
 * <p>
 * The dialogue consists of multiple rounds, each containing a set of options
 * the player can choose from, and corresponding responses from the NPC.
 * The dialogue progresses by moving to the next round after a response.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public class Dialogue {

    /**
     * The list of player options for each dialogue round.
     */
    private final List<List<String>> options;


    /**
     * The list of NPC responses corresponding to player options for each round.
     */
    private final List<List<String>> responses; // NPC responses per round

    /**
     * The current dialogue round index.
     */
    private int roundIndex = 0;

    /**
     * Constructs a new {@code Dialogue} instance with specified player options and NPC responses.
     *
     * @param options the list of options available to the player per round
     * @param responses the list of NPC responses per round, matched to player options
     */
    public Dialogue(List<List<String>> options, List<List<String>> responses) {
        this.options = options;
        this.responses = responses;
    }


    /**
     * Returns the list of options available to the player in the current round.
     *
     * @return a list of options for the current round, or an empty list if the dialogue is finished
     */
    public List<String> getCurrentOptions() {
        if (roundIndex < options.size()) {
            return options.get(roundIndex);
        }
        return List.of(); // No more options
    }

    /**
     * Returns the NPC's response corresponding to the player's selected option.
     *
     * @param optionIndex the index of the chosen option
     * @return the NPC's response, or a default message if the index is out of bounds
     */
    public String getResponseFor(int optionIndex) {
        if (roundIndex < responses.size() && optionIndex < responses.get(roundIndex).size()) {
            return responses.get(roundIndex).get(optionIndex);
        }
        return "Shhh... we will talk later";
    }

    /**
     * Advances the dialogue to the next round.
     */
    public void nextRound() {
        roundIndex++;
    }


    /**
     * Returns whether the dialogue has finished (i.e., no more options left).
     *
     * @return {@code true} if the dialogue has finished, {@code false} otherwise
     */
    public boolean isFinished() {
        return roundIndex >= options.size();
    }
}

