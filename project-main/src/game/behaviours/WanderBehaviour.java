package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.Random;

/**
 * A behaviour that allows an actor to wander randomly around the map.
 * The actor will randomly choose one of the accessible adjacent locations to move to.
 *
 * <p>This behaviour is suitable for passive or non-aggressive actors such as animals.</p>
 *
 * @author Mohanad Al-Mansoob
 */
public class WanderBehaviour implements Behaviour {


    /**
     * Random number generator for selecting a random move.
     */
    private Random random;

    /**
     * Constructor.
     * Initializes the random number generator.
     */
    public WanderBehaviour() {
        random = new Random();
    }

    /**
     * Returns a random movement action to one of the valid adjacent locations.
     * If no valid moves are available, returns {@code null}.
     *
     * @param actor the actor exhibiting this behaviour
     * @param map   the map containing the actor
     * @return a move action if available, otherwise {@code null}
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        ArrayList<Action> actions = new ArrayList<>();

        for (Exit exit : map.locationOf(actor).getExits()) {

            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }

        }

        if (!actions.isEmpty()) {
            return actions.get(random.nextInt(actions.size()));
        }

        else {
            return null;
        }

    }
}
