package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.function.Supplier;

/**
 * An {@link Effect} that spawns a new instance of an {@link Actor} near a reference actor.
 * <p>
 * This effect uses a {@link Supplier} to generate a fresh instance of the actor to avoid
 * reusing the same object multiple times. The actor will be spawned in an adjacent location
 * around the reference actor (or the acting actor if reference actor is not provided).
 * </p>
 * <p>
 * This ensures that the spawned actor is always a new instance, preventing illegal state exceptions.
 * </p>
 *
 * Example usage:
 * <pre>
 *     SpawnActorEffect effect = new SpawnActorEffect(() -> new OmenSheep(), null);
 * </pre>
 *
 * @author Mohanad
 */
public class SpawnActorEffect implements Effect {

    /**
     * A factory that supplies a fresh instance of the actor to spawn.
     */
    private final Supplier<Actor> actorFactory;

    /**
     * The actor near which the new actor should be spawned.
     * If {@code null}, the actor who applies the effect will be used as the reference.
     */
    private final Actor referenceActor;

    /**
     * Constructs a {@code SpawnActorEffect}.
     *
     * @param actorFactory   a {@link Supplier} that provides a fresh {@link Actor} instance
     * @param referenceActor the actor to spawn near (can be {@code null} to default to the acting actor)
     */
    public SpawnActorEffect(Supplier<Actor> actorFactory, Actor referenceActor) {
        this.actorFactory = actorFactory;
        this.referenceActor = referenceActor;
    }

    /**
     * Applies the spawn effect by creating a new actor instance and placing it
     * in a nearby available location.
     *
     * @param actor   the actor applying the effect (used as reference if {@code referenceActor} is null)
     * @param gameMap the game map where the actor will be spawned
     */
    @Override
    public void apply(Actor actor, GameMap gameMap) {
        Actor reference = (referenceActor != null) ? referenceActor : actor;
        Location locationReference = gameMap.locationOf(reference);

        Actor newActor = actorFactory.get(); // create a fresh instance

        for (Exit exit : locationReference.getExits()) {
            if (exit.getDestination().canActorEnter(newActor)) {
                gameMap.addActor(newActor, exit.getDestination());
                break;
            }
        }
    }
}



