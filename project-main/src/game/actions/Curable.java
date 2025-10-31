package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Represents an entity that can be cured by an actor.
 * <p>
 * Implementing classes define their own curing logic when interacted with,
 * such as removing blight, resetting rot timers, or triggering effects.
 * Used by {@link game.actions.CureAction}.
 * </p>
 *
 * @author Mohanad AL-Mansoob
 *
 */
public interface Curable {

    /**
     * Applies a curing effect at the given location by the specified actor.
     *
     * @param actor    the actor performing the cure
     * @param location the location of the curable object
     */
    void cure(Actor actor, Location location);

}
