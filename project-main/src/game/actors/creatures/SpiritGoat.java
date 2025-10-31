package game.actors.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.Curable;
import game.actions.CureAction;
import game.actors.Ability;
import game.actors.Status;
import game.behaviours.NPCController;
import game.behaviours.ReproduceBehaviour;
import game.behaviours.StandardNPCController;
import game.behaviours.WanderBehaviour;
import game.conditions.Condition;
import game.conditions.NearStatusCondition;

/**
 * A mystical creature that passively roams around the map.
 *
 * <p>Represents a "Spirit Goat" with low health and wandering behaviour.
 * The Spirit Goat has a limited lifespan represented by a rot countdown attribute.
 * If not cured in time, the goat will disappear from the map.</p>
 *
 * <p>This creature does not engage in combat or perform any special actions aside from wandering.</p>
 *
 * @author Mohanad Al-Mansoob
 * Modified by: Arielle Ocampo
 */
public class SpiritGoat extends Creature implements Curable, Reproductive {

    /**
     * Constructor for the Spirit Goat.
     * <p>Initializes the Spirit Goat with a name, display character, hit points,
     * wandering behaviour, and a rot countdown attribute.</p>
     */
    public SpiritGoat(NPCController controller) {

        super("Spirit Goat", 'y', 50, controller);
        addBehaviour(0, new ReproduceBehaviour(this));
        addBehaviour(2, new WanderBehaviour());
        addAttribute(CreatureAttributes.ROT_COUNTDOWN, new BaseActorAttribute(10));

    }

    /**
     * Handles the goat's behaviour each turn.
     * <p>Decreases the rot countdown. If the countdown reaches zero, the goat
     * disappears from the map. Otherwise, continues normal wandering behaviour.</p>
     *
     * @param actions list of possible actions
     * @param lastAction the previous action performed
     * @param map the map the actor is on
     * @param display the display to output messages
     * @return the action to perform for the turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        modifyAttribute(CreatureAttributes.ROT_COUNTDOWN, ActorAttributeOperations.DECREASE, 1);

        if (getAttribute(CreatureAttributes.ROT_COUNTDOWN) < 1){

            map.removeActor(this);
            display.println(this + " has disappeared.");
            return new DoNothingAction();

        }

        return super.playTurn(actions, lastAction, map, display);

    }

    /**
     * Returns a string representation of the Spirit Goat,
     * including its remaining rot countdown.
     *
     * @return a string describing the goat and its countdown
     */
    @Override
    public String toString() {
        return super.toString() + " (" +
                this.getAttribute(CreatureAttributes.ROT_COUNTDOWN) + "/" +
                this.getAttributeMaximum(CreatureAttributes.ROT_COUNTDOWN) +
                ")";
    }

    /**
     * Cures the Spirit Goat by restoring its rot countdown to the maximum value.
     *
     * @param actor the actor performing the cure
     * @param location the location of the goat being cured
     */
    @Override
    public void cure(Actor actor, Location location) {

        int maxCountDown = this.getAttributeMaximum(CreatureAttributes.ROT_COUNTDOWN);

        this.modifyAttribute(CreatureAttributes.ROT_COUNTDOWN, ActorAttributeOperations.INCREASE, maxCountDown);

    }

    /**
     * Spawns a Spirit Goat if in the surroundings of blessed entities.
     *
     * @param map the game map of the Spirit Goat reproducing
     * @param location the location of the Spirit Goat reproducing
     */
    @Override
    public void reproduce(GameMap map, Location location) {
        Condition nearBlessed = new NearStatusCondition(this, Status.BLESSED_BY_GRACE);

        if (!nearBlessed.isSatisfied(this, location)) {
            return;
        }

        for (Exit exit : location.getExits()) {
            Location adjacent = exit.getDestination();

            if (!adjacent.containsAnActor()) {
                adjacent.addActor(new SpiritGoat(new StandardNPCController()));
                break;
            }

        }
    }

    /**
     * Returns a list of actions that other actors can perform on this goat.
     *
     * <p>If the other actor has the {@link Ability#CURE} capability, they are allowed to
     * perform a {@link CureAction} on the Spirit Goat.</p>
     *
     * @param otherActor the actor interacting with this goat
     * @param direction the direction of the other actor relative to this goat
     * @param map the map the goat is on
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {


        ActionList actionList = super.allowableActions(otherActor, direction, map);
        Location location = map.locationOf(this);

        if (otherActor.hasCapability(Ability.CURE)) {
            actionList.add(new CureAction(this, location));
        }

        return actionList;
    }

}
