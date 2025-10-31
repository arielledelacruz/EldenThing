package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An action that teleports an actor to a specified location on a specified map.
 * When executed, the actor is removed from the current map and added to the destination map at the given location.
 * @author anfal
 */
public class TeleportAction extends Action {
    /** The map to teleport the actor to */
    private final GameMap destinationMap;
    /** The location within the destination map */
    private final Location destinationLocation;

    /**
     * Constructs a TeleportAction to teleport an actor to a given map and location.
     *
     * @param destinationMap the map to teleport to
     * @param destinationLocation the location within the destination map
     */
    public TeleportAction(GameMap destinationMap, Location destinationLocation) {
        this.destinationMap = destinationMap;
        this.destinationLocation = destinationLocation;
    }

    /**
     * Executes the teleportation action.
     * Removes the actor from the current map and adds them to the destination map at the destination location.
     *
     * @param actor the actor performing the action
     * @param map the current map of the actor
     * @return a string description of the teleport action result
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        destinationMap.addActor(actor, destinationLocation);
        return actor + " teleports to " + destinationLocation.x() + "," + destinationLocation.y();
    }

    /**
     * Returns a description of the action suitable for menus.
     *
     * @param actor the actor performing the action
     * @return a string description of the teleport action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " teleports to new location at (" +
                destinationLocation.x() + ", " + destinationLocation.y() + ") in " +
                destinationLocation.map();
    }
}
