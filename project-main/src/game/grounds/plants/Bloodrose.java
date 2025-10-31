package game.grounds.plants;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A plant that harms nearby actors.
 *
 * <p>Represents a "Bloodrose," a dangerous plant that damages actors standing on the same
 * tile as the plant, as well as those in adjacent tiles. Each actor in the area of effect
 * takes damage from the Bloodrose every game tick.</p>
 *
 * <p>The Bloodrose is a passive hazard, causing harm to those in its vicinity without
 * the need for any direct action from the player or other actors.</p>
 *
 * @author Mohanad Al-Mansoob
 * Modified by Adji Ilhamhafiz Sarie Hakim
 */
public class Bloodrose extends Plant {

    /**
     * Constructor for the Bloodrose.
     * <p>Initializes the Bloodrose with a display character and name.</p>
     */
    public Bloodrose() {

        super('w', "Bloodrose");

    }

    /**
     * Executes the Bloodrose's behavior each game tick.
     * <p>This method calls {@link #hurtSurroundingActors(Location)} to damage actors
     * standing on or near the Bloodrose.</p>
     *
     * @param location the location of the Bloodrose
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        hurtSurroundingActors(location);
    }

    /**
     * Damages actors standing on or near the Bloodrose.
     * <p>Each actor standing on the same tile as the Bloodrose, as well as actors
     * on adjacent tiles, will take 10 points of damage.</p>
     *
     * @param location the location of the Bloodrose
     */
    private void hurtSurroundingActors(Location location) {

        // Heal the actor standing on the Inheritree itself
        if (location.containsAnActor()) {
            Actor centerActor = location.getActor();
            centerActor.hurt(10);
        }

        // Heal the actor surrounding the Inheritree
        for (Exit exit: location.getExits()) {

            Location adjacent = exit.getDestination();

            if (adjacent.containsAnActor()) {

                Actor actor = adjacent.getActor();

                actor.hurt(10);

            }

        }

    }

    @Override
    public void plantEffect(Actor actor, Location location) {
        actor.hurt(5);
    }
}
