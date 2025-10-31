package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.Map;
import java.util.TreeMap;

/**
 * A controller for NPCs that prioritizes behaviours based on their keys (lowest number = highest priority).
 *
 * Iterates through behaviours in ascending order of priority and returns the first non-null action.
 * If no valid action is found, the actor performs a DoNothingAction.
 *
 * This allows for consistent and predictable NPC behavior.
 *
 * @author Mohanad Al-Mansoob
 */
public class StandardNPCController implements NPCController {

    @Override
    public Action playturn(Map<Integer, Behaviour> behaviours, Actor actor, GameMap map, Display display) {

        if (map.locationOf(actor) == null) {
            return new DoNothingAction();
        }

        for (Behaviour behaviour : new TreeMap<>(behaviours).values()) {

            Action action = behaviour.getAction(actor, map);

            if (action != null){
                return action;
            }

        }

        return new DoNothingAction();
    }
}
