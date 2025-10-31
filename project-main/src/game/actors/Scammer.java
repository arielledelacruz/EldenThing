package game.actors;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface representing an entity capable of scamming other actors.
 * Implementing classes define behaviour when a scam is performed on a target actor.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public interface Scammer {
    void onScam(Actor scammedTarget, GameMap map);
}
