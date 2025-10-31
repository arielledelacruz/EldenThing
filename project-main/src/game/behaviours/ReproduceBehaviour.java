package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.creatures.Reproductive;

/**
 * A behaviour that allows a Reproductive actor to attempt reproduction.
 * If reproduction results in a new item being added to the actor's current location,
 * this behaviour returns a DoNothingAction to occupy the turn.
 * Otherwise, it returns null to allow other behaviours to proceed.
 * @author Anfal Ahsan
 */
public class ReproduceBehaviour implements Behaviour {
    /** The reproductive actor associated with this behaviour. */
    private final Reproductive reproductive;

    /**
     * Constructs a ReproduceBehaviour with a given reproductive actor.
     *
     * @param reproductive the actor that has the ability to reproduce
     */
    public ReproduceBehaviour(Reproductive reproductive){
        this.reproductive = reproductive;
    }

    /**
     * Determines whether the actor should reproduce this turn.
     * If reproduction is successful (an item is added), returns a DoNothingAction.
     * If not, returns null so other behaviours (e.g., wandering) can be attempted.
     *
     * @param actor the actor performing the behaviour
     * @param map the current game map
     * @return an Action to be performed this turn, or null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        int itemCountBefore = location.getItems().size();

        reproductive.reproduce(map, location);

        int itemCountAfter = location.getItems().size();
        if (itemCountAfter > itemCountBefore) {
            return new DoNothingAction();
        }

        return null; // Allow other behaviours like wandering to run
    }

}
