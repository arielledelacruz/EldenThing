package game.grounds;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Represents a teleportation destination consisting of a target map and location.
 * @author anfal
 */
public class TeleportDestination {
    /** The map to teleport to */
    private final GameMap map;
    /** The location within the map to teleport to */
    private final Location location;

    /**
     * Constructs a teleportation destination with a specific map and location.
     *
     * @param map the target GameMap
     * @param location the specific Location within the map
     */
    public TeleportDestination(GameMap map, Location location) {
        this.map = map;
        this.location = location;
    }

    /**
     * Returns the target map of the teleport destination.
     *
     * @return the GameMap to teleport to
     */
    public GameMap getMap() { return map; }

    /**
     * Returns the specific location in the target map for teleportation.
     *
     * @return the Location to teleport to
     */
    public Location getLocation() { return location; }
}
