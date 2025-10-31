package game.actors.npcs;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.dialogue.Dialogue;
import game.dialogue.DialogueManager;
import game.dialogue.DialogueParsingException;
import game.actions.TalkAction;
import game.actors.DialogueCapable;
import game.behaviours.NPCController;

import java.util.List;

/**
 * A mystical storyteller NPC that engages the player in a 3-part short story dialogue.
 *
 * The dialogue is generated dynamically from an LLM using a JSON structure defining options and responses.
 * The player can choose to continue or decline the story at each step.
 *
 * @author Mohanad Al-Mansoob
 */
public class Narrator extends NPC implements DialogueCapable {

    /**
     * The prompt sent to the DialogueManager's LLM to generate the story dialogue JSON.
     */
    private final String DIALOGUE_PROMPT = """
            You are a mystical storyteller NPC named "The Narrator" in a fantasy game.
            Generate a Strictly JSON object representing a 3-part short story dialogue with the player. The structure should match this format:

            {
              "options": [
                ["Tell me a story"]
                ["Continue", "Decline"],   // Round 1
                ["Continue", "Decline"],   // Round 2
                ["Finish", "Decline"]      // Round 3
              ],
              "responses": [
                ["Would you like to hear a story about...]
                ["Story part 1 if Continue", "Reply if Decline"],
                ["Story part 2 if Continue", "Reply if Decline"],
                ["Story part 3 if Finish", "Reply if Decline"]
              ]
            }

            Each response should tell one part of a unique, cohesive story. The player should be able to choose to continue or decline at each step.
            """;


    /**
     * Controller that manages NPC behavior.
     */
    private final NPCController controller;

    /**
     * Dialogue manager that generates dialogues by interacting with the LLM.
     */
    private final DialogueManager dialogueManager;

    /**
     * The current dialogue instance representing this NPC's conversation state.
     */
    private Dialogue dialogue;

    /**
     * Constructs a new Narrator NPC with the specified controller and dialogue manager.
     *
     * @param controller the NPC controller for behavior management
     * @param dialogueManager the dialogue manager used to generate and parse dialogues
     */
    public Narrator(NPCController controller, DialogueManager dialogueManager) {

        super("The Narrator", 'N', 200, controller);
        this.controller = controller;
        this.dialogueManager = dialogueManager;
        this.dialogue = fetchNewDialogue();

    }

    /**
     * Resets the current dialogue by fetching a new dialogue from the LLM.
     */
    @Override
    public void resetDialogue() {
        this.dialogue = fetchNewDialogue();
    }

    /**
     * Fetches a new dialogue from the DialogueManager by sending the dialogue prompt to the LLM.
     * If parsing fails, returns a fallback placeholder dialogue.
     *
     * @return a Dialogue object representing the NPC's dialogue with the player
     */
    private Dialogue fetchNewDialogue() {

        try {
            return dialogueManager.generateDialogue(DIALOGUE_PROMPT);
        } catch (DialogueParsingException e) {
            e.printStackTrace();

            return new Dialogue(
                    List.of(
                            List.of("…", "…"),
                            List.of("…", "…"),
                            List.of("…", "…")
                    ),
                    List.of(
                            List.of("…", "…"),
                            List.of("…", "…"),
                            List.of("…", "…")
                    )
            );
        }

    }

    /**
     * Returns the current dialogue instance representing the NPC's conversation state.
     *
     * @return the current Dialogue
     */
    @Override
    public Dialogue getCurrentDialogue() {
        return this.dialogue;
    }

    /**
     * Handles the player's dialogue choice by the selected option index.
     * If the player chooses to decline, returns the decline response and resets the dialogue.
     * Otherwise, returns the corresponding response and advances the dialogue round.
     *
     * @param optionIndex the index of the chosen dialogue option
     * @return the response string corresponding to the chosen option
     */
    @Override
    public String handleDialogueChoice(int optionIndex) {

        String chosenText = dialogue.getCurrentOptions().get(optionIndex);

        if (chosenText.equalsIgnoreCase("Decline")) {
            String declineResponse = dialogue.getResponseFor(optionIndex);
            resetDialogue();
            return declineResponse;
        }

        String response = dialogue.getResponseFor(optionIndex);
        dialogue.nextRound();

        if (dialogue.isFinished()) {
            resetDialogue();
        }

        return response;



    }

    /**
     * Returns the list of allowable actions this NPC can perform in the given context,
     * which includes TalkActions for each current dialogue option.
     *
     * @param actor the actor performing the action
     * @param direction the direction of the NPC relative to the actor
     * @param map the game map context
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor actor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(actor, direction, map);


        for (int i = 0; i < dialogue.getCurrentOptions().size(); i++) {
            actions.add(new TalkAction(this, i));
        }

        return actions;

    }
}
