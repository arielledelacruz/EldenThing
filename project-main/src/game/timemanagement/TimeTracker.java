package game.timemanagement;

import edu.monash.fit2099.engine.displays.Display;

/**
 * Tracks the passage of time in the game by counting turns.
 * Switches between Day and Night phases every fixed number of turns.
 * Implements TimeProvider to supply the current phase.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class TimeTracker implements TimeProvider {
    /** Number of turns that make up one full day or night. */
    private static final int TURNS_PER_DAY = 10;
    private TimePhase currentPhase;
    private int turnCount;

    /**
     * Constructs a TimeTracker starting at DayPhase.
     */
    public TimeTracker() {
        this.currentPhase = new DayPhase();
        this.turnCount = 0;
    }


    /**
     * Advances the game time by one turn.
     * Changes the time phase when the number of turns reaches the threshold.
     */
    public void tick() {
        turnCount++;
        if (turnCount % TURNS_PER_DAY == 0) {
            currentPhase = currentPhase.nextPhase();
            new Display().println("Time has changed to: " + currentPhase.getTimePhase());
        }
    }

    /**
     * Returns the current time phase.
     *
     * @return the current Phases enum (DAY or NIGHT)
     */
    @Override
    public Phases getCurrentPhase() {
        return currentPhase.getTimePhase();
    }
}
