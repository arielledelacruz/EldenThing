package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Merchant;
import game.effects.EffectsList;

/**
 * A weapon item representing a Broadsword that can be bought from a merchant.
 * <p>
 * This weapon deals 30 damage with a 50% chance to hit and uses the "slashes" verb.
 * </p>
 * <p>
 * Upon purchase, the buyer is healed for 10 hit points, any associated effects in the {@link EffectsList}
 * are applied, and the weapon is added to the buyer's inventory.
 * </p>
 *
 * Example usage:
 * <pre>
 *     BroadSword sword = new BroadSword();
 *     int price = sword.getCost();
 *     sword.onPurchase(player, merchant, gameMap);
 * </pre>
 *
 * @author Mohanad Al-Mansoob
 */
public class BroadSword extends WeaponItem implements Buyable {

    /**
     * The cost of the broadsword in Runes.
     */
    private int cost;

    /**
     * A list of effects to apply to the buyer upon purchase.
     */
    private EffectsList effectsList;

    /**
     * Constructs a Broadsword with a specific cost and effects.
     *
     * @param cost         the cost of the broadsword
     * @param effectsList  the list of effects to apply when purchased
     */
    public BroadSword(int cost, EffectsList effectsList) {

        super("Broadsword", 'b', 30, "slashes", 50);
        this.cost = cost;
        this.effectsList = effectsList;

    }

    /**
     * Constructs a default Broadsword with cost 100 and no special effects.
     */
    public BroadSword(){
        this(100, new EffectsList());
    }

    /**
     * Gets the cost of the broadsword.
     *
     * @return the cost in Runes
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * Applies the purchase effects when the broadsword is bought.
     * <p>
     * Heals the buyer by 10 HP, applies any additional effects from the {@code effectsList},
     * and adds the broadsword to the buyer's inventory.
     * </p>
     *
     * @param buyer    the actor who purchased the broadsword
     * @param merchant the merchant who sold the item
     * @param gameMap  the map in which the action occurred
     */
    @Override
    public void onPurchase(Actor buyer, Merchant merchant, GameMap gameMap) {

        buyer.heal(10);
        effectsList.apply(buyer, gameMap);
        buyer.addItemToInventory(this);

    }
}
