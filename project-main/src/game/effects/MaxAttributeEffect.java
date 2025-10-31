package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An {@link Effect} that modifies the maximum value of a specific attribute of an {@link Actor}.
 * <p>
 * This effect can be used to increase or decrease an actor's maximum attribute value, such as maximum health,
 * stamina, or mana.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public class MaxAttributeEffect implements Effect {

    /**
     * The amount by which to modify the attribute's maximum value.
     * Positive values increase the maximum, negative values decrease it.
     */
    private final int amount;

    /**
     * The attribute to be modified.
     */
    private final Enum<?> attribute;

    /**
     * Constructor to create a MaxAttributeEffect.
     *
     * @param attribute the attribute to be modified (e.g., HEALTH, STAMINA)
     * @param amount the amount to modify the maximum by. Positive to increase, negative to decrease.
     */
    public MaxAttributeEffect(Enum<?> attribute, int amount) {

        this.amount = amount;
        this.attribute = attribute;

    }

    /**
     * Applies the effect to the given actor, modifying the maximum value of the specified attribute.
     *
     * @param actor the actor whose attribute maximum will be modified
     * @param gameMap the game map where the actor resides (unused but kept for consistency)
     */
    @Override
    public void apply(Actor actor, GameMap gameMap) {
        if (amount > 0) {
            actor.modifyAttributeMaximum(attribute, ActorAttributeOperations.INCREASE, amount);
        } else if (amount < 0) {
            actor.modifyAttributeMaximum(attribute, ActorAttributeOperations.DECREASE, -amount);
        }

    }
}
