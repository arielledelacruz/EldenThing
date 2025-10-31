package game.actors.creatures;

/**
 * Enum representing teh various attributes of creatures in the game.
 *
 * @author Mohanad Al-Mansoob
 */
public enum CreatureAttributes {

    /**
     * The countdown representing the time remaining before a creature disappears from the map.
     * <p>This attribute is primarily used to manage creatures with a limited lifespan, such as
     * the Omen Sheep. Once the countdown reaches zero, the creature is removed from the map.</p>
     */
    ROT_COUNTDOWN

}
