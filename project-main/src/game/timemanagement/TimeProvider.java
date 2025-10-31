package game.timemanagement;

/**
 * Interface for retrieving the current time phase.
 * Implemented by classes that manage the game's time state.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public interface TimeProvider {
    Phases getCurrentPhase();
}
