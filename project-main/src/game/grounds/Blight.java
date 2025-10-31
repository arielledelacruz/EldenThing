package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.Curable;
import game.actions.CureAction;
import game.actors.Ability;
import game.actors.Status;
import game.actors.creatures.Zombie;
import game.behaviours.StandardNPCController;
import game.timemanagement.Phases;
import game.timemanagement.ServiceLocator;
import game.timemanagement.TimeAware;
import game.timemanagement.TimeProvider;

import java.util.Random;

/**
 * A class representing a blight covering the ground of the valley.
 * @author Adrian Kristanto
 */
public class Blight extends Ground implements Curable, TimeAware {
    private static final int CHANCE_TO_SPAWN_ZOMBIE = 1;
    private final TimeProvider timeProvider;
    private final Random random;


    /**
     * Constructs a Blight ground object. The Blight is initialized with the display character 'x'
     * and is marked as having the "Cursed" status.
     */
    public Blight() {
        super('x', "Blight");
        this.random = new Random();
        this.addCapability(Status.CURSED);
        this.timeProvider = ServiceLocator.getTimeProvider();
    }

    @Override
    public void tick(Location location) {
        this.onTimeChange(location);
        super.tick(location);
    }

    /**
     * Cures the Blight at the given location, replacing it with regular soil and modifying the
     * actor's stamina.
     * <p>
     * This method removes the Blight (replaces it with Soil) and decreases the actor's stamina by 50.
     * </p>
     * @param actor The actor attempting to cure the Blight.
     * @param location The location of the Blight to be cured.
     */
    @Override
    public void cure(Actor actor, Location location) {

        location.setGround(new Soil());

        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 50);

    }

    /**
     * Returns a list of actions that the actor can perform at this location. If the actor has the
     * ability to cure (as indicated by the {@link Ability#CURE} capability), it will add a
     * {@link CureAction} to the list of allowable actions.
     *
     * @param actor The actor requesting the list of actions.
     * @param location The location where the actions are to be performed.
     * @param direction The direction in which the actor is trying to perform the action.
     * @return An ActionList containing the allowable actions for the actor at this location.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {

        ActionList actionList = super.allowableActions(actor, location, direction);

        if (actor.hasCapability(Ability.CURE)) {
            actionList.add(new CureAction(this, location));
        }

        return actionList;
    }

    @Override
    public void onTimeChange(Location location) {
        if (!location.containsAnActor()) {
            if (
                    this.timeProvider.getCurrentPhase() == Phases.NIGHT &&
                    random.nextInt(100) < CHANCE_TO_SPAWN_ZOMBIE &&
                    this.hasCapability(Status.CURSED)
            ) {
                location.addActor(new Zombie(new StandardNPCController()));
            }
        }
    }
}
