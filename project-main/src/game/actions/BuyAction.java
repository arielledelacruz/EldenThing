package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Merchant;
import game.weapons.Buyable;

/**
 * Represents an action that allows an {@link Actor} to buy a {@link Buyable} item from a {@link Merchant}.
 * <p>
 * The actor must have enough runes (balance) to complete the purchase.
 * On a successful purchase, the cost is deducted from the actor's balance and the item's {@code onPurchase} logic is triggered.
 * </p>
 *
 * Example usage:
 * <pre>
 *     new BuyAction(new Uchigatana(), fingerReaderEnia);
 * </pre>
 *
 * @author Mohanad Al-Mansoob
 */
public class BuyAction extends Action {

    /**
     * The item to be purchased.
     */
    private Buyable item;

    /**
     * The merchant selling the item.
     */
    private Merchant merchant;

    /**
     * Constructs a BuyAction.
     *
     * @param item     The buyable item the actor wants to purchase
     * @param merchant The merchant selling the item
     */
    public BuyAction(Buyable item, Merchant merchant){
        this.item = item;
        this.merchant  = merchant;
    }

    /**
     * Executes the buying process.
     * <ul>
     *     <li>Checks if the actor has enough balance to buy the item.</li>
     *     <li>If so, deducts the cost and triggers the item's purchase behavior.</li>
     *     <li>If not, returns a message indicating insufficient funds.</li>
     * </ul>
     *
     * @param actor The actor performing the action (the buyer)
     * @param map   The game map the actor is on
     * @return A string describing the outcome of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        int cost = item.getCost();

        if (actor.getBalance() < cost) {
            return actor + " does not have enough Runes to buy " + item;
        }

        item.onPurchase(actor, merchant, map);
        actor.deductBalance(cost);

        return actor + " buys " + item + " from " + merchant;

    }

    /**
     * Provides a description of this action suitable for displaying in a menu.
     *
     * @param actor The actor performing the action
     * @return A menu-friendly string describing the buy action
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Buy " + item + " from " + merchant + " for " + item.getCost() + " runes";
    }
}
