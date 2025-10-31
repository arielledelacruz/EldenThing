package game.grounds;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.actors.Ability;
import game.actors.Status;
import java.util.ArrayList;
import java.util.List;

/**
 * A special type of ground that allows actors with the TELEPORT ability
 * to teleport to predefined destinations.
 *
 * Only actors standing on this tile (not adjacent) can teleport.
 *
 * Represented by 'A' on the map.
 * @author anfal
 */
public class TeleportationCircle extends Ground {
    /** List of possible teleport destinations from this circle */
    private final List<TeleportDestination> destinations = new ArrayList<>();

    /**
     * Constructor for the TeleportationCircle.
     * Initializes with display character 'A' and name "Teleportation Circle".
     */
    public TeleportationCircle() {
        super('A', "Teleportation Circle");

    }

    /**
     * Adds a teleportation destination to the list.
     *
     * @param destination the teleportation destination to add
     */
    public void addDestination(TeleportDestination destination) {
        destinations.add(destination);
    }


    /**
     * Determines what actions an actor can perform at this location.
     * Only actors currently standing on this tile and possessing the TELEPORT ability
     * will be given teleport actions.
     *
     * @param actor     the Actor acting
     * @param location  the current Location of the actor
     * @param direction the direction of the location from the Actor
     * @return a list of allowable actions (may include teleport actions)
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        // Only provide teleport actions if the actor is STANDING on this tile (not adjacent)
        if (location.getActor() == actor && actor.hasCapability(Ability.TELEPORT)) {
            for (TeleportDestination dest : destinations) {
                actions.add(new TeleportAction(dest.getMap(), dest.getLocation()));
            }
        }

        return actions;
    }

}
