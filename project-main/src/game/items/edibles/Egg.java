package game.items.edibles;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.EatAction;
import game.actors.creatures.Creature;
import game.conditions.Condition;
import game.effects.Effect;


/**
 * Abstract representation of an egg that is laid on the ground,
 * hatches into a creature based on hatching conditions, and
 * be eaten by actors if stored in inventory.
 *
 * <p>
 * Subclasses define their own hatching logic.
 * </p>
 *
 * @author Arielle Ocampo
 */
public class Egg extends Item implements Edible {

    /**
     * Counts the number of consecutive turns egg has spent
     * on the ground (upon being laid/dropped).
     *
     */
    private int hatchCounter = 0;
    private final Creature hatchling;
    private Condition hatchCondition;
    private final Effect eatEffect;

    /**
     * Constructs a new Egg.
     *
     * @param name the name to display for egg
     * @param displayChar the character to represent egg on map
     */
    public Egg(String name, char displayChar, Creature hatchling,
               Condition hatchCondition, Effect eatEffect) {
        super(name, displayChar, true);
        this.hatchling = hatchling;
        this.hatchCondition = hatchCondition;
        this.eatEffect = eatEffect;
    }


    /**
     * Sets or replaces the condition used to determine whether this egg should hatch.
     *
     * @param condition the new hatching Condition
     *
     */
    public void setHatchCondition(Condition condition) {
        this.hatchCondition = condition;
    }

    /**
     * Called once per turn when the egg is on the map.
     * Increments hatchCounter and hatches the egg when ready.
     *
     * @param location current location of egg on map
     */
    @Override
    public void tick(Location location) {
        hatchCounter++;

        // Check if the condition is satisfied
        if (hatchCondition.isSatisfied(null, location)) {

            // try to hatch in current location if it's empty
            if (!location.containsAnActor()) {
                location.addActor(hatchling);
                location.removeItem(this);
                return;
            }

            // otherwise look for the first adjacent empty location
            for (Exit exit : location.getExits()) {
                Location adjacent = exit.getDestination();
                if (!adjacent.containsAnActor()) {
                    adjacent.addActor(hatchling);
                    location.removeItem(this);
                    return;
                }
            }

            // If no empty adjacent tile, do nothing
        }
    }

    /**
     * Returns the number of consecutive turns egg has spent on ground
     * since it was laid or dropped.
     *
     * @return current hatch counter value
     */
    public int getHatchCounter() {
        return hatchCounter;
    }

    /**
     * Called when an actor eats egg from its inventory.
     * Subclasses apply their effects and logic.
     *
     * @param actor actor eating the egg
     * @param map game map context
     * @return description of the eating action
     */
    @Override
    public String eat(Actor actor, GameMap map) {
        eatEffect.apply(actor, map);
        actor.removeItemFromInventory(this);
        return actor + " eats the egg.";
    }

    /**
     * Only allows EatAction if this egg is currently held in the actor's inventory.
     *
     * @param owner actor performing the action
     * @param map the map where the actor is performing the action on
     * @return list of actions
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        actions.add(new EatAction(this));

        return actions;
    }
}
