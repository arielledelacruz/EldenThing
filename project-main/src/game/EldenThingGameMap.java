package game;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.GroundFactory;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.NumberRange;
import game.actors.npcs.SuspiciousMerchant;
import game.behaviours.StandardNPCController;
import game.timemanagement.*;

import java.util.List;
import java.util.Random;

/**
 * A specialized GameMap for EldenThing, supporting time-aware behavior.
 * <p>
 * This map can react to the in-game time (Day/Night cycle) and spawns
 * special NPCs like the Suspicious Merchant based on the current phase.
 * It supports two modes of accessing time:
 * - Direct control via TimeTracker (injected manually)
 * - Indirect awareness via TimeProvider from ServiceLocator
 *
 * @author Adrian Kristanto
 *
 * Modified from GameMap by Adji Ilhamhafiz Sarie Hakim
 */
public class EldenThingGameMap extends GameMap implements TimeAware {

    /**
     * Optional TimeTracker reference, if the map is actively progressing time itself.
     */
    private final TimeTracker timeTracker;

    /**
     * Optional TimeProvider reference, allowing the map to be passively time-aware.
     */
    private final TimeProvider timeProvider;

    /**
     * Random number generator used for spawning actors at random locations.
     */
    private Random random;

    /**
     * Tracks whether the merchant has been spawned to avoid multiple instances at night.
     */
    boolean merchantSpawned;

    /**
     * Constructor used when map only needs to react to time, not progress it.
     * Uses the ServiceLocator to obtain the global TimeProvider.
     *
     * @param name          name of the map
     * @param groundFactory factory to create ground types
     * @param lines         list of strings defining the map layout
     */

    public EldenThingGameMap(String name, GroundFactory groundFactory, List<String> lines) {
        super(name, groundFactory, lines);
        this.timeTracker = null;
        this.timeProvider = ServiceLocator.getTimeProvider();
        this.random = new Random();
        this.merchantSpawned = false;
    }

    /**
     * Constructor used when this map is responsible for progressing time.
     * Accepts a TimeTracker which it will tick every turn.
     *
     * @param name          name of the map
     * @param groundFactory factory to create ground types
     * @param groundChar    default ground character
     * @param width         width of the map
     * @param height        height of the map
     * @param timeTracker   instance used to control and progress time
     */

    public EldenThingGameMap(String name, GroundFactory groundFactory, char groundChar, int width, int height, TimeTracker timeTracker) {
        super(name, groundFactory, groundChar, width, height);
        this.timeTracker = timeTracker;
        this.timeProvider = null;
    }

    /**
     * Ticks the map forward one turn:
     * - If a TimeTracker is present, it progresses time.
     * - Selects a random location and checks if time-based changes (like NPC spawning) should occur.
     * - Delegates to the parent class to progress all other map mechanics.
     */

    public void tick() {
        if (timeTracker != null) {
            timeTracker.tick();
        }

        if (random != null) {
            NumberRange xRange = this.getXRange();
            NumberRange yRange = this.getYRange();

            int randX = random.nextInt(xRange.min(), xRange.max());
            int randY = random.nextInt(yRange.min(), yRange.max());

            this.onTimeChange(this.at(randX, randY));
        }

        super.tick();
    }

    /**
     * Time-aware behavior executed at a given location when time changes.
     * - Spawns a Suspicious Merchant during Night if the location is empty and no merchant exists.
     * - Resets the spawn flag during Day to allow future respawning.
     *
     * @param location a randomly chosen location to evaluate for time-based changes
     */
    @Override
    public void onTimeChange(Location location) {
        if (timeProvider != null) {
            if (this.timeProvider.getCurrentPhase() == Phases.NIGHT && !merchantSpawned) {

                boolean containsActor = location.containsAnActor();

                if (!containsActor) {
                    location.addActor(new SuspiciousMerchant(new StandardNPCController()));
                    merchantSpawned = true;
                }

            } else if (this.timeProvider.getCurrentPhase() == Phases.DAY) {
                merchantSpawned = false;
            }
        }
    }
}
