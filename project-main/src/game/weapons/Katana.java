package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Merchant;
import game.effects.EffectsList;

/**
 * A weapon named "Katana" that can be bought from a {@link Merchant}.
 * <p>
 * The Katana deals 50 damage, has a hit rate of 60%, and uses the verb "delivers".
 * Upon purchase, the buyer takes 25 damage, any additional effects are applied,
 * and the Katana is added to the buyer’s inventory.
 * </p>
 * <p>
 * Implements {@link Buyable}, allowing it to be listed in a merchant's inventory.
 * </p>
 *
 * Example usage:
 * <pre>
 *     Buyable katana = new Katana();
 *     katana.onPurchase(player, merchant, gameMap);
 * </pre>
 *
 * @author Mohanad Al-Mansoob
 */
public class Katana extends WeaponItem implements Buyable {

    /**
     * The cost of the Katana in Runes.
     */
    private int cost;

    /**
     * A list of effects to apply when the Katana is purchased.
     */
    private EffectsList effectsList;

    /**
     * Constructs a {@code Katana} with a specified cost and effects.
     *
     * @param cost         the rune cost of the Katana
     * @param effectsList  the list of effects to apply upon purchase
     */
    public Katana(int cost, EffectsList effectsList) {

        super("Katana", 'j', 50, "delivers", 60);
        this.cost = cost;
        this.effectsList = effectsList;

    }

    /**
     * Constructs a default {@code Katana} with a cost of 500 runes and no additional effects.
     */
    public Katana() {

        this(500, new EffectsList());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * {@inheritDoc}
     * <p>
     * On purchase, the buyer takes 25 damage, receives any additional effects,
     * and the Katana is added to their inventory.
     * </p>
     *
     * @param buyer     the actor purchasing the weapon
     * @param merchant  the merchant selling the weapon
     * @param gameMap   the game map where the transaction occurs
     */
    @Override
    public void onPurchase(Actor buyer, Merchant merchant, GameMap gameMap) {
        buyer.hurt(25);
        effectsList.apply(buyer, gameMap);
        buyer.addItemToInventory(this);
    }
}
