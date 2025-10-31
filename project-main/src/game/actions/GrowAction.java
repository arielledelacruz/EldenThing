package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Display;
import game.actors.creatures.boss.BedOfChaos;

/**
 * Triggers growth.
 * Returns an empty string.
 *
 * @author Arielle Ocampo
 */
public class GrowAction extends Action {

    private final BedOfChaos boss;

    /**
     * Creates a GrowAction for the boss.
     *
     * @param boss the BedOfChaos to grow
     */
    public GrowAction(BedOfChaos boss) {
        this.boss = boss;
    }


    /**
     * Calls grow() on the boss and prints growth messages.
     * Returns an empty string.
     *
     * @param actor actor performing the action
     * @param map   GameMap
     * @return empty string
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Display display = new Display();
        boss.grow(display);
        return "";
    }

    /**
     * Returns an empty description (does not appear in menu).
     *
     * @param actor the actor
     * @return empty string
     */
    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}

