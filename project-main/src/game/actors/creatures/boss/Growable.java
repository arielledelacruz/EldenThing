package game.actors.creatures.boss;

/**
 * Interface for anything that can grow on BedOfChaos
 * and contribute to attack power each turn.
 * Implementing classes must define getAttackPower() and grow().
 *
 * @author Arielle Ocampo
 */
public interface Growable  {

    /**
     * Returns its attack power.
     *
     * @return attack power value
     */
    int getAttackPower();

    /**
     * Performs growth.
     */
    void grow();
}
