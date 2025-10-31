package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.creatures.boss.BedOfChaos;
import game.actions.GrowAction;

/**
 * Triggers growth for BedOfChaos each turn by returning a GrowAction.
 *
 * @author Arielle Ocampo
 */
public class GrowBehaviour implements Behaviour {

    /**
     * Returns a new GrowAction for BedOfChaos.
     *
     * @param actor actor performing the behaviour (BedOfChaos)
     * @param map   game map actor is on
     * @return GrowAction that executes growth
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        BedOfChaos boss = (BedOfChaos) actor;
        return new GrowAction(boss);
    }
}