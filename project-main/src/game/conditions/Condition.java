package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Represents a condition that can be checked against an {@link Actor} and a {@link Location}.
 * <p>
 * Conditions are used to determine whether certain requirements are met for events,
 * interactions, or monologues (e.g., whether an actor has enough money, is near a specific type of ground, etc.).
 * </p>
 *
 * Implementing classes define the logic for whether the condition is fulfilled.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public interface Condition {

    /**
     * Evaluates whether the condition is satisfied based on the given actor and location.
     *
     * @param actor    the actor to evaluate the condition for
     * @param location the location of the actor
     * @return {@code true} if the condition is satisfied, {@code false} otherwise
     */
    boolean isSatisfied(Actor actor, Location location);
}
