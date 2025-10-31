package game.timemanagement;

/**
 * Represents the daytime phase in the game.
 * Transitions to NightPhase when `nextPhase()` is called.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class DayPhase implements TimePhase {

    /**
     * Returns the enum constant representing the DAY phase.
     *
     * @return Phases.DAY
     */
    @Override
    public Phases getTimePhase() {
        return Phases.DAY;
    }

    /**
     * Returns the next time phase (NightPhase).
     *
     * @return a new instance of NightPhase
     */
    @Override
    public TimePhase nextPhase() {
        return new NightPhase();
    }
}
