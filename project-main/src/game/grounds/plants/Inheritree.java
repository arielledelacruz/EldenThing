package game.grounds.plants;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Status;
import game.grounds.GroundStatus;
import game.grounds.Soil;

import static game.actors.Status.BLESSED_BY_GRACE;

/**
 * A mystical plant that heals actors and cures surrounding cursed ground.
 *
 * <p>Represents the "Inheritree," a unique plant that has the ability to heal both
 * nearby actors and the land around it. It also cures cursed ground by transforming
 * it into normal soil.</p>
 *
 * <p>The Inheritree has a passive effect on its surroundings, making it an important
 * element for healing and cleansing cursed areas in the game world.</p>
 *
 * @author Mohanad Al-Mansoob
 * Modified by Adji Ilhamhafiz Sarie Hakim
 */
public class Inheritree extends Plant {

    /**
     * Constructor for the Inheritree.
     * <p>Initializes the Inheritree with a display character and name,
     * and sets its state to not having cured the ground yet.</p>
     */
    public Inheritree() {

        super('t', "Inheritree");
        this.addCapability(BLESSED_BY_GRACE);

    }

    /**
     * Executes the Inheritree's behavior each game tick.
     * <p>The Inheritree attempts to cure any cursed ground around it once,
     * then heals actors standing on or near it.</p>
     *
     * @param location the location of the Inheritree
     */
    @Override
    public void tick(Location location) {

        super.tick(location);

        healSurroundingActors(location);

    }

    /**
     * Cures cursed ground adjacent to the Inheritree.
     * <p>Transforms cursed ground into normal soil.</p>
     *
     * @param location the location of the Inheritree
     */
    private void cureSurroundingGrounds(Location location) {

        for (Exit exit: location.getExits()) {

            Location adjacent = exit.getDestination();

            if (adjacent.getGround().hasCapability(Status.CURSED)){
                adjacent.setGround(new Soil());
            }

            adjacent.getGround().addCapability(BLESSED_BY_GRACE);
        }

    }

    /**
     * Heals actors standing on or near the Inheritree.
     * <p>Heals the actor standing on the same tile as the Inheritree, as well as actors
     * in adjacent tiles. If the actor has a stamina attribute, it also increases stamina.</p>
     *
     * @param location the location of the Inheritree
     */
    private void healSurroundingActors(Location location) {

        // Heal the actor standing on the Inheritree itself
        if (location.containsAnActor()) {
            Actor centerActor = location.getActor();
            centerActor.heal(5);
            if (centerActor.hasAttribute(BaseActorAttributes.STAMINA)) {
                centerActor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 5);
            }
        }

        // Heal the actor surrounding the Inheritree
        for (Exit exit: location.getExits()) {

            Location adjacent = exit.getDestination();

            if (adjacent.containsAnActor()) {

                Actor actor = adjacent.getActor();

                actor.heal(5);

                if (actor.hasAttribute(BaseActorAttributes.STAMINA)) {

                    actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 5);

                }
            }

        }

    }

    @Override
    public void plantEffect(Actor actor, Location location) {
        cureSurroundingGrounds(location);
    }
}
