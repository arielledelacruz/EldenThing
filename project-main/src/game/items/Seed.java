package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.PlantAction;
import game.grounds.GroundStatus;
import game.grounds.plants.Plant;


/**
 * An abstract class representing a seed that can be planted by an actor.
 * <p>
 * Seeds are items that can be used by actors to plant on the ground. They are
 * typically used to grow various types of plants. This class provides the functionality
 * to check whether the ground is suitable for planting and allows actors to perform
 * a planting action.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 * Modified by Adji Ilhamhafiz Sarie Hakim
 */
public class Seed extends Item {

    private final Plant plant;
    private final int staminaCost;

    /**
     * Constructor for creating a Seed object.
     * <p>
     * This constructor initializes the seed with a name and display character. The seed
     * is also marked as being usable.
     * </p>
     *
     * @param name the name of the seed
     * @param plant the plant that will grow from this seed
     * @param staminaCost the stamina cost to plant this seed
     */
    public Seed(String name, Plant plant, int staminaCost) {

        super(name, '*', true);
        this.plant = plant;
        this.staminaCost = staminaCost;
    }

    /**
     * Abstract method that should be implemented to define the action of planting the seed.
     * <p>
     * This method defines the logic for planting the seed on the specified location. It should be
     * implemented by concrete subclasses of Seed to specify what happens when the seed is planted.
     * </p>
     *
     * @param actor the actor who is planting the seed
     * @param location the location where the seed is being planted
     */
    public void plant(Actor actor, Location location) {
        location.setGround(plant);
        this.plant.plantEffect(actor, location);
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaCost);
    }

    /**
     * Returns a list of actions that an actor can perform with this seed.
     * <p>
     * If the ground at the actor's current location is plantable, the actor can perform a {@link PlantAction}
     * to plant the seed.
     * </p>
     *
     * @param otherActor the actor who is interacting with the seed
     * @param map the game map where the action is happening
     * @return a list of allowable actions for this seed
     */
    @Override
    public ActionList allowableActions(Actor otherActor, GameMap map) {

        ActionList actionList = new ActionList();

        Location location = map.locationOf(otherActor);

        Ground ground = location.getGround();

        if (ground.hasCapability(GroundStatus.PLANTABLE)) {

            actionList.add(new PlantAction(this, location));

        }

        return actionList;

    }
}
