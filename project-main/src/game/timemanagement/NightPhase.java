package game.timemanagement;

/**
 * Represents the nighttime phase in the game.
 * Transitions to DayPhase when `nextPhase()` is called.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class NightPhase implements TimePhase {

    /**
     * Returns the enum constant representing the NIGHT phase.
     *
     * @return Phases.NIGHT
     */
    @Override
    public Phases getTimePhase() {
        return Phases.NIGHT;
    }

    /**
     * Returns the next time phase (DayPhase).
     *
     * @return a new instance of DayPhase
     */
    @Override
    public TimePhase nextPhase() {
        return new DayPhase();
    }
}
