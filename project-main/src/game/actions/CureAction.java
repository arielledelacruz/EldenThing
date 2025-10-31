package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An action that allows an actor to cure a {@link Curable} target at a specified location.
 *
 * <p>The cure operation is delegated to the {@link Curable} interface's {@code cure} method,
 * allowing for flexible behaviour depending on the target's implementation.</p>
 *
 * <p>Note: This action is intended for use with items or abilities that have curing capabilities,
 * such as a talisman that can heal or purify cursed grounds or creatures.</p>
 *
 * @author Mohanad Al-Mansoob
 */
public class CureAction extends Action {

    /**
     * The curable target to be cured.
     */
    private Curable target;

    /**
     * The location where the cure will be performed.
     */
    private Location location;

    /**
     * Constructor to create a CureAction.
     *
     * @param target the {@link Curable} target to be cured
     * @param location the location where the curing will take place
     */
    public CureAction(Curable target, Location location){

        this.target = target;
        this.location = location;

    }

    /**
     * Executes the curing action.
     * Delegates the curing logic to the {@link Curable#cure(Actor, Location)} method.
     *
     * @param actor the actor performing the curing action
     * @param map the map containing the actor and the target
     * @return a string describing the outcome of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        target.cure(actor, location);

        return actor + " cured" + target;

    }

    /**
     * Provides a description of this action for display in the menu.
     *
     * @param actor the actor performing the action
     * @return a string describing the cure action
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Cure " + target +  " at " + location;
    }
}
