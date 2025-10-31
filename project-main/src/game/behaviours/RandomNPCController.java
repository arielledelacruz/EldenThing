package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A controller for NPCs that selects a random behaviour each turn.
 * If no behaviours are available or the chosen behaviour returns null,
 * the NPC does nothing.
 *
 * This adds variety and unpredictability to NPC actions.
 *
 * @author Anfal Ahsan
 */
public class RandomNPCController implements NPCController{

    /** Random number generator for selecting behaviours */
    private final Random rand = new Random();

    /**
     * Selects and performs a random behaviour from the given set of behaviours.
     * If no behaviour is available or the selected behaviour returns null,
     * returns a DoNothingAction.
     *
     * @param behaviours a map of possible behaviours with their priorities
     * @param actor the actor being controlled
     * @param map the game map the actor is in
     * @param display the display used for output
     * @return an Action selected from a random behaviour, or DoNothingAction
     */
    @Override
    public Action playturn(Map<Integer, Behaviour> behaviours, Actor actor, GameMap map, Display display) {
        if (behaviours.isEmpty()) {
            return new DoNothingAction();
        }

        List<Behaviour> behaviourList = new ArrayList<>(behaviours.values());
        Behaviour chosenBehaviour = behaviourList.get(rand.nextInt(behaviourList.size()));

        Action action = chosenBehaviour.getAction(actor, map);
        return action != null ? action : new DoNothingAction();

    }
}
