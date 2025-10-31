package game.items.edibles;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Represents an item that can be consumed by an actor.
 * Eating logic is defined in each class.
 *
 * @author Arielle Ocampo
 */
public interface Edible {

    /**
     * Called when an actor eats.
     *
     * @param actor the actor performing the eat action
     * @param map the game map context in which the action occurs
     * @return a description of what happens when edible is eaten
     */
    String eat(Actor actor, GameMap map);
}
