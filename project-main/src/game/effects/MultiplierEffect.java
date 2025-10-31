package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An effect that increases an actor's damage multiplier by a specified amount.
 * When applied, it boosts the damage output of the actor.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class MultiplierEffect implements Effect{

    private final float amount;

    /**
     * Constructor.
     *
     * @param amount the multiplier amount to increase the actor's damage by
     */
    public MultiplierEffect(float amount) {
        this.amount = amount;
    }

    /**
     * Applies the multiplier effect to the given actor by increasing their damage multiplier.
     *
     * @param actor the actor to apply the effect to
     * @param gameMap the current game map (unused in this effect)
     */
    @Override
    public void apply(Actor actor, GameMap gameMap) {
        actor.increaseDamageMultiplier(this.amount);
    }
}
