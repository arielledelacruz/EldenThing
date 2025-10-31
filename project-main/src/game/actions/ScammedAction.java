package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Merchant;
import game.actors.Scammer;
import game.weapons.Buyable;

/**
 * Represents an action that allows an {@link Actor} to get scammed by  {@link Merchant}.
 * this action is very similar to {@link BuyAction} but it does not give the item to the actor.
 *
 * <p>
 * The actor must have enough runes (balance) to complete the purchase.
 * On a successful purchase, the cost is deducted from the actor's balance and no item
 * will be given to {@link Actor}.
 * </p>
 *
 * Example usage:
 * <pre>
 *     new ScammedAction(new Uchigatana(), fingerReaderEnia);
 * </pre>
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class ScammedAction extends Action {
    private final Buyable item;
    private final Scammer scammer;

    public ScammedAction(Buyable item, Scammer scammer) {
        this.item = item;
        this.scammer = scammer;
    }


    @Override
    public String execute(Actor actor, GameMap map) {
        int cost = item.getCost();

        if (actor.getBalance() < cost) {
            return actor + " does not have enough Runes to buy " + item + ".";
        }

        actor.deductBalance(cost);
        this.scammer.onScam(actor, map);

        return actor + " has been scammed by " + scammer + " and lost " + cost + " Runes.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Buy " + item + " from " + scammer + " for " + item.getCost() + " runes";
    }
}
