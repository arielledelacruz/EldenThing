package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;

/**
 * -
 *
 * An action that allows an actor to attack another actor in a specified direction.
 * If a specific weapon is provided, it will be used for the attack; otherwise, the actor's intrinsic weapon is used.
 * This action also checks if the target becomes unconscious as a result of the attack.
 *
 * Note: This class was adapted from the base code provided in the demo game "huntsman" provided in fit2099 engine.
 *
 * @author Mohanad Al-Mansoob
 */
public class AttackAction extends Action {

    /**
     * The target actor being attacked.
     */
    private Actor target;


    /**
     * The direction of the attack, used for menu description.
     */
    private String direction;

    /**
     * The weapon used for the attack, if any.
     */
    private Weapon weapon;

    /**
     * Constructor to create an attack action using a specific weapon.
     *
     * @param target the Actor to attack
     * @param direction the direction of the attack (used for display purposes)
     * @param weapon the weapon used for the attack
     */
    public AttackAction(Actor target, String direction, Weapon weapon) {
        this.target = target;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Constructor with intrinsic weapon as default
     *
     * @param target the actor to attack
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    /**
     * Executes the attack action.
     * The actor attacks the target using either the provided weapon or their intrinsic weapon.
     * If the target becomes unconscious, the unconscious handler is called.
     *
     * @param actor the actor performing the action
     * @param map the map the actor is on
     * @return a string describing the outcome of the attack
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (weapon == null) {
            weapon = actor.getIntrinsicWeapon();
        }

        String result = weapon.attack(actor, target, map);
        if (!target.isConscious()) {
            result += "\n" + target.unconscious(actor, map);
        }

        return result;
    }


    /**
     * Provides a string description of this action for display in the menu.
     *
     * @param actor the actor performing the action
     * @return a string describing the attack action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction + " with " + (weapon != null ? weapon : "Intrinsic Weapon");
    }
}
