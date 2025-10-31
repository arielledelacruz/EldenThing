package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.List;

/**
 * A composite effect that applies a list of {@link Effect}s to an {@link Actor}.
 * <p>
 * This class allows grouping multiple effects together and applying them sequentially to an actor.
 * Useful for combining multiple effects like damage, healing, status changes, etc., into a single action.
 * </p>
 * <p>
 * Implements the {@link Effect} interface, so it can be used wherever a single Effect is expected.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public class EffectsList implements Effect {

    /**
     * The list of effects to be applied.
     */
    private final List<Effect> effects = new ArrayList<>();

    /**
     * Adds an effect to the list of effects.
     *
     * @param effect the effect to be added
     */
    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    /**
     * Applies all effects in the list to the given actor within the specified game map.
     *
     * @param actor the actor to which the effects are applied
     * @param gameMap the map where the effects take place
     */
    @Override
    public void apply(Actor actor, GameMap gameMap) {
        for (Effect effect : effects) {
            effect.apply(actor, gameMap);
        }
    }
}
