package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A condition that checks whether an {@link Actor}'s health is above or below a specified threshold.
 * This condition can be used to control dialogue, behaviors, or events that depend on the actor's current health.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class HealthCondition implements Condition {
    /**
     * The health threshold to compare against.
     */
    private final int health;
    /**
     * If true, condition checks if health is greater than threshold; otherwise, checks if less.
     */
    private final boolean higher;

    /**
     * Constructs a {@code HealthCondition} with a specific health threshold and comparison direction.
     *
     * @param health the health threshold to compare against
     * @param higher {@code true} if condition should check for health greater than the threshold,
     *               {@code false} if it should check for health less than the threshold
     */
    public HealthCondition(int health, boolean higher) {
        this.health = health;
        this.higher = higher;
    }

    /**
     * Evaluates whether the actor's health satisfies the condition.
     *
     * @param actor    the actor to evaluate
     * @param location the location of the actor (not used in this condition)
     * @return {@code true} if the actor's health meets the specified condition, {@code false} otherwise
     */
    @Override
    public boolean isSatisfied(Actor actor, Location location) {
        if (this.higher) {
            return actor.getAttribute(BaseActorAttributes.HEALTH) > this.health;
        } else {
            return actor.getAttribute(BaseActorAttributes.HEALTH) < this.health;
        }
    }
}
