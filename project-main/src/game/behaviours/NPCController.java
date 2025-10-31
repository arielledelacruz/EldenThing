package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.Map;

/**
 * Interface for NPC controllers that determine what an NPC should do during its turn.
 * Implementations can define different strategies for how to select an action
 * from a set of available behaviours.
 *
 * @author Mohanad Al-Mansoob
 */
public interface NPCController {

    Action playturn(Map<Integer, Behaviour> behaviours, Actor actor, GameMap map, Display display);

}
