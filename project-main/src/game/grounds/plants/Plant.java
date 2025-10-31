package game.grounds.plants;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A base class for all plant types in the game.
 *
 * <p>Represents a generic plant that can be placed on the ground in the game world. All specific
 * types of plants (like the Inheritree or Bloodrose) extend this class to inherit common behavior
 * and properties of plants, such as display character and name.</p>
 *
 * <p>This class extends the {@link Ground} class and serves as the base for all plant-related
 * functionality in the game.</p>
 *
 * @author Mohanad Al-Mansoob
 * Modified by Adji Ilhamhafiz Sarie Hakim
 */
public abstract class Plant extends Ground {

    /**
     * Constructor for the Plant class.
     *
     * <p>Initializes the plant with a display character and a name. These attributes are passed
     * to the parent {@link Ground} class constructor.</p>
     *
     * @param displayChar the character used to represent this plant on the map
     * @param name the name of the plant
     */
    public Plant(char displayChar, String name) {

        super(displayChar, name);

    }

    public abstract void plantEffect(Actor actor, Location location);

}