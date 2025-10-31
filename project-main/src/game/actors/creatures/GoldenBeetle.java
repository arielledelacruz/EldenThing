package game.actors.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.EatAction;
import game.actors.Ability;
import game.actors.Status;
import game.behaviours.*;
import game.conditions.NearStatusCondition;
import game.effects.AttributeEffect;
import game.items.edibles.Edible;
import game.items.edibles.Egg;

/**
 * Represents a Golden Beetle creature in the game.
 * Golden Beetles can reproduce, be eaten, and interact with other actors.
 * @author Anfal Ahsan
 */

public class GoldenBeetle extends Creature implements Reproductive, Edible {

    /** Counter to track how many turns have passed since the last egg was laid. */
    private int eggLayCounter = 0;
    /** Maximum number of turns before the beetle lays another egg. */
    private final int MAX_EGG_COUNTER = 5;
    /** Amount of health restored when this beetle is eaten. */
    private final int HEALING_AMOUNT = 15;
    /** Reward in balance when this beetle is eaten. */
    private final int BALANCE_REWARD = 1000;
    /** Actor that the beetle will follow if found. */
    private Actor followTarget = null;
    /** the stamina to be incremented*/
    private final int STAMINA = 20;

    /**
     * Constructs a Golden Beetle with initial behaviours.
     * It can reproduce and wander.
     */
    public GoldenBeetle(NPCController controller) {
        super("Golden Beetle", 'b', 25, controller);
        addBehaviour(0, new ReproduceBehaviour(this));
        addBehaviour(2, new WanderBehaviour());
    }


    /**
     * Handles the reproduction behavior.
     * Every MAX_EGG_COUNTER turns, a Golden Egg is laid if the condition is met.
     *
     * @param map the game map
     * @param location the current location of the beetle
     */
    @Override
    public void reproduce(GameMap map, Location location) {
        eggLayCounter++;

        if (eggLayCounter >= MAX_EGG_COUNTER) {
            Egg egg = new Egg(
                    "Golden Egg",
                    '0',
                    new GoldenBeetle(new StandardNPCController()),
                    new NearStatusCondition(Status.CURSED),
                    new AttributeEffect(BaseActorAttributes.STAMINA, STAMINA));

                    location.addItem(egg);
            System.out.println("Golden Beetle laid an egg at " + location);
            eggLayCounter = 0;
        }
    }


    /**
     * Returns a list of actions that can be performed on this beetle by another actor.
     * Adds an EatAction to allow actors to eat the beetle.
     *
     * @param otherActor the actor interacting with this beetle
     * @param direction direction of the other actor
     * @param map the current game map
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actionList = super.allowableActions(otherActor, direction, map);
        actionList.add(new EatAction(this));

        return actionList;
    }

    /**
     * Defines what happens when another actor eats this beetle.
     * The actor is healed, given balance, and the beetle is removed from the map.
     *
     * @param actor the actor that eats the beetle
     * @param map the current game map
     * @return a description of the result
     */
    @Override
    public String eat(Actor actor, GameMap map) {
        actor.heal(HEALING_AMOUNT);
        actor.addBalance(BALANCE_REWARD);
        map.removeActor(this);
        return actor + " eats the beetle.";
    }

    /**
     * Determines what the beetle does during its turn.
     * If a followable actor is nearby, adds a FollowBehaviour.
     * Otherwise, proceeds with defined behaviours.
     *
     * @param actions list of possible actions
     * @param lastAction the action taken last turn
     * @param map the current game map
     * @param display the display used for printing messages
     * @return the action chosen for this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        if (followTarget == null || !map.contains(followTarget)) {
            // Scan surroundings for a followable actor
            Location here = map.locationOf(this);
            for (Exit exit : here.getExits()) {
                Location adjacent = exit.getDestination();
                Actor other = adjacent.getActor();
                if (other != null && other.hasCapability(Ability.FOLLOWABLE)) {
                    followTarget = other;
                    this.addBehaviour(1, new FollowBehaviour(followTarget));
                    break;
                }
            }
        }

        return super.playTurn(actions, lastAction, map, display);

    }
}
