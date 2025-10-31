package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.edibles.Edible;

/**
 * An action allowing an actor to eat an Edible item.
 *
 * @author Arielle Ocampo
 */
public class EatAction extends Action {

    /**
     * The edible item to be eaten.
     */
    private final Edible edible;

    /**
     * Creates a new EatAction for the given edible item.
     *
     * @param edible  the Edible to be eaten
     */
    public EatAction(Edible edible) {
        this.edible = edible;
    }

    /**
     * Executes eat action by invoking the edible's eat method.
     *
     * @param actor the actor performing the action
     * @param map the game map context
     * @return the result string after eating
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return edible.eat(actor, map);
    }

    /**
     * Returns the menu description displayed.
     *
     * @param actor the actor performing the action
     * @return a string like "Farmer eats Omen Sheep Egg"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " eats " + edible;
    }
}
