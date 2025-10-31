package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.items.edibles.Egg;


/**
 * A condition that is satisfied when a specified number of turns has passed since the Egg was created.
 * Used to control when an Egg is eligible to hatch based on a turn threshold.
 * @author Arielle Ocampo
 */

public class TurnCounterCondition implements Condition {

    /** The egg whose turn count is being tracked. */
    private final Egg egg;
    /** The number of turns that must pass before the condition is satisfied. */
    private final int turnThreshold;


    /**
     * Constructs a TurnCounterCondition with a specific egg and turn threshold.
     *
     * @param egg the egg being monitored
     * @param turnThreshold the number of turns required to satisfy the condition
     */
    public TurnCounterCondition(Egg egg, int turnThreshold) {
        this.egg = egg;
        this.turnThreshold = turnThreshold;
    }

    /**
     * Checks if the condition is satisfied based on the egg's hatch counter.
     *
     * @param actor the actor checking the condition (not used in this condition)
     * @param location the location of the actor (not used in this condition)
     * @return true if the egg's hatch counter is equal to or exceeds the threshold
     */
    @Override
    public boolean isSatisfied(Actor actor, Location location) {
        return egg.getHatchCounter() >= turnThreshold;
    }
}
