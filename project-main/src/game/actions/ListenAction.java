package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.npcs.NPC;

import java.util.Random;

/**
 * An action that allows an {@link Actor} to listen to a monologue from an {@link NPC}.
 * When executed, this action retrieves a random line from the NPC's monologue and returns it.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class ListenAction extends Action {

    /**
     * The NPC from whom the actor will listen.
     */
    private final NPC npc;

    /**
     * Random number generator to choose a random monologue line.
     */
    private final Random random;

    /**
     * Constructs a new {@code ListenAction}.
     *
     * @param npc the NPC to listen to
     */
    public ListenAction(NPC npc) {
        this.npc = npc;
        this.random = new Random();
    }

    /**
     * Executes the action, allowing the actor to hear a random line from the NPC's monologue.
     *
     * @param actor the actor performing the action
     * @param map   the current game map
     * @return a string containing the randomly selected monologue line
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return this.npc.sayMonologue(actor, map).get(random.nextInt(this.npc.sayMonologue(actor, map).size())).listen();
    }

    /**
     * Provides a description of this action suitable for displaying in a menu.
     *
     * @param actor the actor performing the action
     * @return a string describing the listen action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " listens to " + this.npc;
    }
}
