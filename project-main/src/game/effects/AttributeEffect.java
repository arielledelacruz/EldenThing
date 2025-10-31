package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An {@link Effect} that modifies a specific {@link BaseActorAttributes} of an {@link Actor}.
 * <p>
 * The effect can increase or decrease the value of an attribute depending on the specified amount.
 * If the actor does not have the attribute, this effect does nothing.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public class AttributeEffect implements Effect {

    /**
     * The attribute to modify.
     */
    private final BaseActorAttributes attribute;

    /**
     * The amount to modify the attribute by.
     * Positive values will increase the attribute, negative values will decrease it.
     */
    private final int amount;

    /**
     * Constructor to create an AttributeEffect.
     *
     * @param attribute the attribute to modify
     * @param amount the amount to modify the attribute by (positive to increase, negative to decrease)
     */
    public AttributeEffect(BaseActorAttributes attribute, int amount) {
        this.attribute = attribute;
        this.amount = amount;
    }

    /**
     * Applies the effect to the target actor.
     * <p>
     * If the actor possesses the specified attribute, its value is increased or decreased
     * based on the given amount.
     * </p>
     *
     * @param actor the actor to apply the effect on
     * @param gameMap the game map where the actor resides (unused in this effect)
     */
    @Override
    public void apply(Actor actor, GameMap gameMap) {

        if (actor.hasAttribute(attribute)) {

            if (amount > 0) {

                actor.modifyAttribute(attribute, ActorAttributeOperations.INCREASE, amount);

            } else if (amount < 0) {

                actor.modifyAttribute(attribute, ActorAttributeOperations.DECREASE, -amount);

            }

        }

    }

}
