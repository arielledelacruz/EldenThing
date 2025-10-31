package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A condition that checks whether an {@link Actor}'s inventory is empty.
 * <p>
 * This can be used to determine whether a monologue or action should be available
 * based on the actor not carrying any items.
 * </p>
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class EmptyInventoryCondition implements Condition{

    /**
     * Constructs an {@code EmptyInventoryCondition}.
     */
    public EmptyInventoryCondition() {
    }

    /**
     * Checks if the actor's inventory is empty.
     *
     * @param actor    the actor whose inventory is checked
     * @param location the current location of the actor (not used in this condition)
     * @return {@code true} if the actor has no items, {@code false} otherwise
     */
    @Override
    public boolean isSatisfied(Actor actor, Location location) {
        return actor.getItemInventory().isEmpty();
    }
}
