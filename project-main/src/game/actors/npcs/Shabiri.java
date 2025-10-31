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
 * Shabiri is an {@link NPC} that engages in dynamically generated, deep philosophical
 * dialogue with the player using an LLM (Large Language Model) backend.
 * <p>
 * Shabiri implements {@link DialogueCapable}, meaning it can provide and update
 * {@link Dialogue} objects. Dialogue is generated based on a structured JSON prompt
 * designed to simulate three rounds of conversation initiated by the player.
 * </p>
 *
 * <p>Shabiri's dialogue is poetic, cryptic, and unsettling in tone.</p>
 *
 * @author Mohanad Al-Mansoob
 */
public class Shabiri extends NPC implements DialogueCapable {


    /**
     * A detailed JSON prompt instructing the LLM to generate a 3-round dialogue
     * with specific tone and formatting.
     */
    private final String DIALOGUE_PROMPT = """
    You are Shabiri, an ancient, cryptic entity in a fantasy world. You enjoy deep, evolving philosophical conversations with adventurers. The player always begins the dialogue.
    
    Generate a STRICTLY JSON object representing a 3-round dialogue initiated by the player. The structure should follow this format exactly:
    
    {
      "options": [
        ["Player greeting + question 1", "Player greeting + question 2", "Player greeting + question 3"],       // Round 1 (player starts)
        ["Reflective follow-up 1", "Thoughtful follow-up 2", "Emotionally reactive follow-up 3"],              // Round 2
        ["Final remark or insight 1", "Final remark or insight 2", "Final remark or insight 3"]                // Round 3
      ],
      "responses": [
        ["Shabiri's reply to option 1", "Reply to option 2", "Reply to option 3"],   // Round 1
        ["Reply to follow-up 1", "Reply to follow-up 2", "Reply to follow-up 3"],   // Round 2
        ["Final reply 1", "Final reply 2", "Final reply 3"]                         // Round 3
      ]
    }
    
    Tone and Conversation Guidelines:
    - The greeting from Shabiri should be poetic, mystical, or unsettling in a calm way.
    - The player's **first round** options should each begin like a greeting or invitation to speak, followed by a deep or existential question.
        - e.g., "Greetings, Shabiri. What do you see when you look at the stars?"
    - The **second round** should feel like the player is engaging with Shabiri's earlier response — either agreeing, reflecting, or challenging it thoughtfully.
        - e.g., "That’s fascinating... but does that mean we have no free will?"
    - The **final round** should be conclusive or emotionally weighted — a parting thought, final insight, or personal reflection.
    - Shabiri's replies should evolve naturally, becoming deeper, more enigmatic, or more personal with each round.

    """;

    /**
     * Controller responsible for governing Shabiri's autonomous behaviours.
     */
    private final NPCController controller;

    /**
     * Manager responsible for sending prompts to the LLM and receiving structured dialogue responses.
     */
    private final DialogueManager dialogueManager;

    /**
     * The current dialogue state for this NPC, which evolves as the player interacts.
     */
    private Dialogue dialogue;


    /**
     * Constructs a new instance of Shabiri, generating an initial dialogue using the LLM.
     *
     * @param controller       the NPC behavior controller
     * @param dialogueManager  the manager responsible for interacting with the LLM
     */
    public Shabiri(NPCController controller, DialogueManager dialogueManager){

        super("Shabiri", 'Q', 76, controller);

        this.controller = controller;
        this.dialogueManager = dialogueManager;
        this.dialogue = fetchNewDialogue();
    }

    /**
     * Gets the current dialogue object.
     *
     * @return the current {@link Dialogue}
     */
    @Override
    public Dialogue getCurrentDialogue() {
        return this.dialogue;
    }

    /**
     * Resets the dialogue by generating a new one via the LLM.
     */
    @Override
    public void resetDialogue() {
        this.dialogue = fetchNewDialogue();
    }

    /**
     * Fetches a new {@link Dialogue} from the {@link DialogueManager}, or provides a fallback
     * if dialogue generation fails.
     *
     * @return a valid {@link Dialogue} object
     */
    private Dialogue fetchNewDialogue() {

        try {
            return dialogueManager.generateDialogue(DIALOGUE_PROMPT);
        } catch (DialogueParsingException e) {
            e.printStackTrace();

            return new Dialogue(
                    List.of(
                            List.of("Hi"),
                            List.of("What?"),
                            List.of("What did you say?")
                    ),
                    List.of(
                            List.of("Seems like not a good day to talk to you!"),
                            List.of("Ha?"),
                            List.of("Bla bla bla ba...")
                    )
            );
        }

    }


    /**
     * Handles the player's choice of a dialogue option, returns the NPC response,
     * and progresses to the next round. If all rounds are completed, it resets the dialogue.
     *
     * @param optionIndex the index of the selected player dialogue option
     * @return the corresponding NPC response
     */
    @Override
    public String handleDialogueChoice(int optionIndex) {

        String response = dialogue.getResponseFor(optionIndex);
        dialogue.nextRound();

        if (dialogue.isFinished()) {
            resetDialogue();
        }

        return response;
    }

    /**
     * Provides allowable {@link TalkAction}s for each dialogue option available in the current round.
     *
     * @param actor the actor interacting with this NPC
     * @param direction the direction of the NPC from the actor
     * @param map the game map
     * @return an {@link ActionList} containing all valid dialogue options
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
