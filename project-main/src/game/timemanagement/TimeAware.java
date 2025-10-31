package game.timemanagement;

import edu.monash.fit2099.engine.positions.Location;
/**
 * Interface for objects that respond to time phase changes.
 * Implementing classes define what happens when time changes.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public interface TimeAware {
    void onTimeChange(Location location);
}
