package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;

import java.util.ArrayList;

/**
 * A behaviour that allows an Actor to attack nearby Actors
 * if their health is above a specified threshold.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class AttackBehaviour implements Behaviour {
    private static final int DEFAULT_HEALTH_THRESHOLD = 0;

    /**
     * The health threshold used to determine whether an Actor should be attacked.
     */
    private final int health;

    /**
     * Constructor for {@code AttackBehaviour}.
     *
     * @param health The minimum health an adjacent Actor must have
     *               for this Actor to consider attacking.
     */
    public AttackBehaviour(int health) {
        this.health = health;
    }

    public AttackBehaviour() {
        this(DEFAULT_HEALTH_THRESHOLD); // Default threshold of 0, meaning any Actor with health > 0 can be attacked.
    }

    /**
     * Scans adjacent tiles for Actors whose health is greater than the defined threshold
     * and returns an {@link AttackAction} against the first such Actor found.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return An {@link AttackAction} if a valid target is found; otherwise {@code null}.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        ArrayList<Action> actions = new ArrayList<>();

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location targetLoc = exit.getDestination();
            if (targetLoc.containsAnActor()) {
                Actor target = targetLoc.getActor();
                if (target.getAttribute(BaseActorAttributes.HEALTH) > this.health) {
                    actions.add(new AttackAction(target, "around"));
                }
            }
        }

        if (!actions.isEmpty()) {
            return actions.get(0);
        } else {
            return null;
        }
    }
}
