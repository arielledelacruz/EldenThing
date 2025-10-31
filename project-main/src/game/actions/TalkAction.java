package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.dialogue.Dialogue;
import game.actors.DialogueCapable;

/**
 * An {@link Action} that allows an {@link Actor} (usually the player) to select
 * a dialogue option when interacting with a {@link DialogueCapable} NPC.
 * <p>
 * This action invokes the {@code handleDialogueChoice} method on the NPC,
 * producing a response from the current {@link Dialogue}.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public class TalkAction extends Action {

    /**
     * The dialogue-capable NPC this action targets.
     */
    private final DialogueCapable npc;

    /**
     * The index of the dialogue option selected by the player.
     */
    private final int optionIndex;

    /**
     * Constructs a new {@code TalkAction}.
     *
     * @param npc the dialogue-capable NPC
     * @param optionIndex the index of the dialogue option chosen
     */
    public TalkAction(DialogueCapable npc, int optionIndex) {

        this.npc = npc;
        this.optionIndex = optionIndex;

    }

    /**
     * Executes the action, returning the NPC's response to the chosen dialogue option.
     *
     * @param actor the actor performing the action (e.g., the player)
     * @param map the current game map
     * @return the result of the dialogue interaction
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        return npc.handleDialogueChoice(optionIndex);

    }

    /**
     * Returns a description of this action for display in the menu.
     *
     * @param actor the actor performing the action
     * @return a string describing the dialogue option being selected
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Say to " + npc + ": " + npc.getCurrentDialogue().getCurrentOptions().get(optionIndex);
    }
}
