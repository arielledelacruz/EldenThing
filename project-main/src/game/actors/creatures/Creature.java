package game.actors.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.behaviours.NPCController;

import java.util.Map;
import java.util.TreeMap;

/**
 * An abstract class representing a general creature in the game world.
 * Creatures have a collection of {@link Behaviour} objects that determine their actions each turn,
 * with each behaviour assigned a priority.
 *
 * <p>During its turn, a creature will execute the first non-null action returned from its behaviours,
 * according to their priority order.</p>
 *
 * @author Mohanad Al-Mansoob
 */
public abstract class Creature extends Actor {

    /** A map of behaviours with their priorities. Lower numbers represent higher priorities. */
    private final Map<Integer, Behaviour> behaviours = new TreeMap<>();

    private final NPCController controller;


    /**
     * Constructor for a Creature.
     *
     * @param name        the name of the creature
     * @param displayChar the character that will represent the creature in the UI
     * @param hitPoints   the creature's initial health points
     */
    public Creature(String name, char displayChar, int hitPoints, NPCController controller) {

        super(name, displayChar, hitPoints);
        this.controller = controller;

    }


    /**
     * Adds a behaviour to the creature with the specified priority.
     *
     * @param priority  the priority of the behaviour (lower value = higher priority)
     * @param behaviour the behaviour to be added
     */
    public void addBehaviour(int priority, Behaviour behaviour) {

        behaviours.put(priority, behaviour);

    }

    public Map<Integer, Behaviour> getBehaviours() {
        return behaviours;
    }

    /**
     * Selects the creature's action for the current turn by checking its behaviours in order of priority.
     *
     * @param actions     list of actions that can be performed
     * @param lastAction  the action performed last turn
     * @param map         the current game map
     * @param display     the display object used for user interaction
     * @return the selected action to perform this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        return controller.playturn(behaviours, this, map, display);

    }


    /**
     * Returns a list of allowable actions that other actors can perform on this creature.
     * By default, allows attacking this creature.
     *
     * @param otherActor the actor that might be performing the action
     * @param direction  the direction of the other actor
     * @param map        the current game map
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new AttackAction(this, direction));
        return actions;
    }
}
