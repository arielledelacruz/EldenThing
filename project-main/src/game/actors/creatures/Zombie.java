package game.actors.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.AttackBehaviour;
import game.behaviours.NPCController;
import game.behaviours.WanderBehaviour;
import game.timemanagement.Phases;
import game.timemanagement.ServiceLocator;
import game.timemanagement.TimeAware;
import game.timemanagement.TimeProvider;
import game.weapons.BareFist;

/**
 * A Zombie creature that is controlled by an NPCController and reacts to time phases.
 * <p>
 * Zombies take damage during the day phase and have attack and wander behaviours.
 * </p>
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class Zombie extends Creature implements TimeAware {
    private static final int DEFAULT_HIT_POINTS = 50;
    private static final int DAMAGE_TAKEN_IN_DAY_PHASE = 20;
    private final TimeProvider timeProvider;
    private final NPCController controller;


    /**
     * Constructs a Zombie with the specified NPCController.
     * Initializes behaviours for attacking and wandering,
     * sets intrinsic weapon to BareFist,
     * and obtains the global time provider.
     *
     * @param controller the NPCController that manages this Zombie's behaviour
     */
    public Zombie(NPCController controller) {
        super("Zombie", 'Z', DEFAULT_HIT_POINTS, controller);
        this.setIntrinsicWeapon(new BareFist());
        this.addBehaviour(0, new AttackBehaviour());
        this.addBehaviour(1, new WanderBehaviour());
        this.timeProvider = ServiceLocator.getTimeProvider();
        this.controller = controller;
    }

    /**
     * Called every turn to determine the Zombie's action.
     * The Zombie reacts to time changes and unconscious state before
     * delegating action selection to its NPCController.
     *
     * @param actions the list of possible actions for this turn
     * @param lastAction the action performed in the previous turn
     * @param map the game map containing this Zombie
     * @param display the display interface
     * @return the Action chosen for this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        this.onTimeChange(map.locationOf(this));

        if (!this.isConscious()){
            this.unconscious(map);
        }

        return controller.playturn(this.getBehaviours(), this, map, display);
    }


    /**
     * Reacts to time changes by applying damage if the current phase is DAY.
     *
     * @param location the current location of the Zombie
     */
    @Override
    public void onTimeChange(Location location) {
        if (this.timeProvider.getCurrentPhase() == Phases.DAY) {
            this.hurt(DAMAGE_TAKEN_IN_DAY_PHASE);
        }
    }

}
