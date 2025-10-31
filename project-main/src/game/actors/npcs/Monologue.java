package game.actors.npcs;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.conditions.Condition;

/**
 * Represents a monologue that an NPC can say.
 * A monologue may have an optional {@link Condition} that must be satisfied
 * before it can be heard by a listener.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class Monologue {
    /**
     * The condition under which this monologue can be heard, or null if always available.
     */
    private final Condition condition;

    /**
     * The message that constitutes the monologue.
     */
    private final String message;

    /**
     * Constructs a monologue with a specific condition.
     *
     * @param condition the {@link Condition} that must be satisfied for this monologue to be available
     * @param message   the message of the monologue
     */
    public Monologue(Condition condition, String message) {
        this.condition = condition;
        this.message = message;
    }

    /**
     * Constructs an unconditional monologue, which can always be heard.
     *
     * @param message the message of the monologue
     */
    public Monologue(String message) {
        this.condition = null;
        this.message = message;
    }

    /**
     * Determines whether the monologue can be listened to by the given actor
     * in the context of the specified game map.
     *
     * @param actor the actor attempting to listen
     * @param map   the current {@link GameMap}
     * @return true if the monologue's condition is satisfied or if no condition is set
     */
    public boolean canListen(Actor actor, GameMap map) {
        if (this.condition == null) {
            return true;
        }
        return this.condition.isSatisfied(actor, map.locationOf(actor));
    }

    /**
     * Returns the message of the monologue.
     *
     * @return the monologue's message as a {@link String}
     */
    public String listen() {
        return this.message;
    }
}
