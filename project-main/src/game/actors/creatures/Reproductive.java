package game.actors.creatures;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface for creatures that can reproduce.
 * Reproduction logic is defined in each class.
 *
 * @author Arielle Ocampo
 */
public interface Reproductive {

    /**
     * Called to reproduce at current location.
     *
     * @param map the game map of the reproducing entity
     * @param location the current location of the reproducing entity
     */
    void reproduce(GameMap map, Location location);
}
