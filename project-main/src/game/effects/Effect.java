package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;


/**
 * Represents an effect that can be applied to an {@link Actor} within a {@link GameMap}.
 * <p>
 * Effects define specific behaviors or changes that can be applied to actors, items, or other entities.
 * Common use cases include applying damage, healing, buffs, debuffs, or other status changes.
 * </p>
 * <p>
 * This interface follows the Strategy Pattern, allowing different effect implementations to be used interchangeably.
 * </p>
 *
 * @author Mohanad Al-Mansoob
 */
public interface Effect {


    /**
     * Applies the effect to the given actor within the specified game map.
     *
     * @param actor the actor to which the effect is applied
     * @param gameMap the map where the effect takes place
     */
    void apply(Actor actor, GameMap gameMap);

}
