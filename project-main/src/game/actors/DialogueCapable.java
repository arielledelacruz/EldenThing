package game.actors;

import game.dialogue.Dialogue;

/**
 * An interface for actors or entities that support LLM-powered dialogue.
 * <p>
 * Implementing classes are expected to manage a {@link Dialogue} instance,
 * allowing interaction through player choices and resetting the conversation.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public interface DialogueCapable {

    /**
     * Retrieves the current {@link Dialogue} instance associated with this entity.
     *
     * @return the current dialogue
     */
    Dialogue getCurrentDialogue();

    /**
     * Resets the dialogue to its initial state or prepares for a new one.
     */
    void resetDialogue();

    /**
     * Handles a player's selected dialogue option and returns the corresponding NPC response.
     *
     * @param optionIndex the index of the selected option
     * @return the NPC's response to the selected option
     */
    String handleDialogueChoice(int optionIndex);

}
