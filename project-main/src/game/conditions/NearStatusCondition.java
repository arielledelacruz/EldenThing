package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A condition that checks if a specified {@link Enum} status is present in the surrounding area of an actor.
 * This condition can detect the presence of a capability on adjacent {@link edu.monash.fit2099.engine.positions.Ground}s,
 * {@link Item}s, or {@link Actor}s. It can optionally evaluate the surrounding of a specific actor rather than
 * the one passed into {@link #isSatisfied(Actor, Location)}.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class NearStatusCondition implements Condition {

    /**
     * Optional actor whose location will be checked for nearby status. If null, the actor passed to isSatisfied() is used.
     */
    private final Actor actor;

    /**
     * The capability status to search for in nearby locations.
     */
    private final Enum<?> status;

    /**
     * Constructs a {@code NearStatusCondition} that checks near a specific actor.
     *
     * @param actor  the actor whose surroundings will be evaluated
     * @param status the status capability to look for
     */
    public NearStatusCondition(Actor actor, Enum<?> status) {
        this.actor = actor;
        this.status = status;
    }

    /**
     * Constructs a {@code NearStatusCondition} that checks around the actor passed to {@link #isSatisfied}.
     *
     * @param status the status capability to look for
     */
    public NearStatusCondition(Enum<?> status) {
        this.actor = null;
        this.status = status;
    }

    /**
     * Determines whether the specified status is present in adjacent locations.
     * <p>
     * The search includes checking:
     * <ul>
     *     <li>The ground at each adjacent location</li>
     *     <li>Any items at those locations</li>
     *     <li>Any actors present in those locations</li>
     * </ul>
     * </p>
     *
     * @param actor    the actor whose surrounding will be checked if no specific actor is set
     * @param location the location of the actor
     * @return {@code true} if the status is found nearby, {@code false} otherwise
     */
    @Override
    public boolean isSatisfied(Actor actor, Location location) {

        if (this.actor != null) {
            location = location.map().locationOf(this.actor);
        }

        assert location != null;

        for (Exit exit : location.getExits()) {
            Location point = exit.getDestination();

            if (point.getGround().hasCapability(this.status)) {
                return true;
            }

            for (Item item : point.getItems()) {
                if (item.hasCapability(this.status)) {
                    return true;
                }
            }

            if (point.containsAnActor() && point.getActor().hasCapability(this.status)) {
                return true;
            }
        }

        return false;
    }
}