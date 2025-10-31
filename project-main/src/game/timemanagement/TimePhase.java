package game.timemanagement;

/**
 * Interface to represent a time phase in the game (e.g., DAY or NIGHT).
 * Provides method to get the current phase and to advance to the next phase.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public interface TimePhase {
    Phases getTimePhase();
    TimePhase nextPhase();
}
