package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Seed;

/**
 * An action that allows an actor to plant a {@link Seed} at a specified location.
 *
 * <p>The planting operation is delegated to the {@link Seed#plant(Actor, Location)} method,
 * and the seed is removed from the actor's inventory after planting.</p>
 *
 * <p>This action is used to grow various plants, trees, or special items that can evolve
 * or affect the gameplay environment.</p>
 *
 * @author Mohanad Al-Mansoob
 * Modified by Adji Ilhamhafiz Sarie Hakim
 */
public class PlantAction extends Action {

    /**
     * The seed to be planted.
     */
    private final Seed seed;

    /**
     * The location where the seed will be planted.
     */
    private final Location location;

    /**
     * Constructor to create a PlantAction.
     *
     * @param seed the {@link Seed} to be planted
     * @param location the location where the seed will be planted
     */
    public PlantAction(Seed seed, Location location){

        this.seed = seed;
        this.location = location;

    }

    /**
     * Executes the planting action.
     * Plants the seed at the specified location and removes the seed from the actor's inventory.
     *
     * @param actor the actor performing the planting action
     * @param map the map the actor is on
     * @return a string describing the outcome of the planting
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        seed.plant(actor, location);
        actor.removeItemFromInventory(seed);
        return actor + " plants " + seed + " at " + location;

    }

    /**
     * Provides a description of this action for display in the menu.
     *
     * @param actor the actor performing the action
     * @return a string describing the plant action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " plants " + seed;
    }
}
