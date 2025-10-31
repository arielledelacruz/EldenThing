package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Merchant;

/**
 * An interface representing items that can be purchased from a {@link Merchant}.
 * <p>
 * Classes implementing this interface must define how much the item costs and
 * what happens when the item is purchased.
 * </p>
 *
 * Example usage:
 * <pre>
 *     Buyable sword = new BroadSword();
 *     int price = sword.getCost();
 *     sword.onPurchase(player, merchant, gameMap);
 * </pre>
 *
 * @author
 */
public interface Buyable {

    /**
     * Returns the cost of the item in Runes.
     *
     * @return the cost
     */
    int getCost();

    /**
     * Defines the behavior that occurs when the item is purchased.
     *
     * @param buyer    the actor purchasing the item
     * @param merchant the merchant selling the item
     * @param gameMap  the game map where the purchase occurs
     */
    void onPurchase(Actor buyer, Merchant merchant, GameMap gameMap);

}
