package game.actors.npcs;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ListenAction;
import game.actions.AttackAction;
import game.behaviours.NPCController;

import java.util.*;

/**
 * An abstract class representing a Non-Playable Character (NPC) in the game.
 * NPCs can have behaviours that dictate their actions each turn and can speak monologues
 * under specific conditions. Players may interact with NPCs using actions like Listen or Attack.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public abstract class NPC extends Actor {

    /**
     * A list containing all possible monologues this NPC can say
     */
    private final List<Monologue> monologuePool;
    /**
     * A map of behaviours with priorities. Lower numbers represent higher priority.
     */
    private final Map<Integer, Behaviour> behaviours;

    private final NPCController controller;

    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public NPC(String name, char displayChar, int hitPoints, NPCController controller) {
        super(name, displayChar, hitPoints);
        this.behaviours = new TreeMap<>();
        this.monologuePool = new ArrayList<>();
        this.controller = controller;
    }

    /**
     * Adds a monologue to the NPC's monologue pool.
     *
     * @param dialogue the {@link Monologue} to be added
     */
    public void addIntoMonologuePool(Monologue dialogue) {
        monologuePool.add(dialogue);
    }

    /**
     * Adds a behaviour to the NPC with a specified priority.
     * Lower integer values represent higher priority.
     *
     * @param priority  the priority of the behaviour
     * @param behaviour the {@link Behaviour} to be added
     */
    public void addBehaviour(int priority, Behaviour behaviour) {
        behaviours.put(priority, behaviour);
    }

    public Map<Integer, Behaviour> getBehaviours() {
        return behaviours;
    }

    /**
     * Determines the NPC's action for this turn by consulting its behaviours.
     * If no behaviour returns an action, the NPC does nothing.
     *
     * @param actions    available actions for this actor
     * @param lastAction the action this actor performed last turn
     * @param map        the map the actor is on
     * @param display    the display for output
     * @return the {@link Action} to be performed
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        return controller.playturn(behaviours, this, map, display);
    }

    /**
     * Returns the list of allowable actions another actor can do to this NPC.
     * This includes listening to or attacking the NPC.
     *
     * @param actor     the actor interacting with the NPC
     * @param direction the direction of the NPC from the actor
     * @param map       the map the interaction is taking place on
     * @return an {@link ActionList} of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor actor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(actor, direction, map);
        if (!monologuePool.isEmpty()) {
            actions.add(new ListenAction(this));
        }
        actions.add(new AttackAction(this, direction));
        return actions;
    }

    /**
     * Retrieves a list of monologues that this NPC can say based on the current actor and map.
     * Only monologues whose conditions are satisfied will be returned.
     *
     * @param actor the actor listening to the monologue
     * @param map   the map context for the interaction
     * @return a list of {@link Monologue} objects this NPC can say
     */
    public List<Monologue> sayMonologue(Actor actor, GameMap map){
        List<Monologue> availableMonologues = new ArrayList<>();
        for (Monologue monologue : this.monologuePool) {
            if (monologue.canListen(actor, map)) {
                availableMonologues.add(monologue);
            }
        }
        return availableMonologues;
    }
}
