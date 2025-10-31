package game.actors.npcs;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.AttackBehaviour;
import game.behaviours.NPCController;
import game.behaviours.WanderBehaviour;
import game.conditions.HealthCondition;
import game.weapons.BareFist;

/**
 * Represents the NPC Guts, a character known for his strength and rage.
 * Guts is an NPC who:
 * <ul>
 *     <li>Attacks with a bare fist</li>
 *     <li>Wanders randomly using {@link WanderBehaviour}</li>
 *     <li>Has specific monologues that reflect his character</li>
 * </ul>
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class Guts extends NPC {

    private final static int GUTS_HIT_POINTS = 500;
    private final static int ATTACK_BEHAVIOUR_DAMAGE = 50;
    private static final int MONOLOGUE_HEALTH_THRESHOLD = 50;

    /**
     * Constructs a new instance of Guts, initializing monologues and behaviours.
     */
    public Guts(NPCController controller) {
        super("Guts", 'g', GUTS_HIT_POINTS, controller);
        this.setIntrinsicWeapon(new BareFist());
        this.addBehaviour(0, new AttackBehaviour(ATTACK_BEHAVIOUR_DAMAGE));
        this.addBehaviour(1, new WanderBehaviour());
        this.addIntoMonologuePool(new Monologue("RAAAAGH!"));
        this.addIntoMonologuePool(new Monologue("I’LL CRUSH YOU ALL!"));
        this.addIntoMonologuePool(new Monologue(new HealthCondition(MONOLOGUE_HEALTH_THRESHOLD, false), "WEAK! TOO WEAK TO FIGHT ME!"));
    }

    /**
     * Returns the action Guts performs each turn.
     * Delegates to the superclass's behavior-driven logic.
     *
     * @param actions the list of actions available to the actor
     * @param lastAction the last action performed
     * @param map the current game map
     * @param display the display for output
     * @return the action to be performed
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return super.playTurn(actions, lastAction, map, display);
    }
}
