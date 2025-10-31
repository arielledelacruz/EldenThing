package game.actors;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import game.weapons.Buyable;

/**
 * An interface representing a merchant who can sell items or services to actors.
 * <p>
 * Classes implementing this interface should define what items or services are available for purchase
 * via the {@link #getSellActions()} method, which returns a list of sell-related actions (e.g., {@code BuyAction}).
 * </p>
 *
 * This allows different types of merchants to provide custom sell options.
 *
 * Example usage:
 * <pre>
 *     ActionList actions = merchant.getSellActions();
 * </pre>
 *
 * @author Mohanad Al-Mansoob
 */
public interface Merchant {

    /**
     * Returns a list of actions representing items or services the merchant offers for sale.
     *
     * @return an {@link ActionList} of sell actions available from this merchant
     */
    ActionList getSellActions();


}
