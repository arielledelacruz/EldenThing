package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Merchant;
import game.effects.EffectsList;

/**
 * A heavy weapon called "Dragonslayer Greatsword" that can be bought from a {@link Merchant}.
 * <p>
 * This weapon deals 70 damage, has a hit rate of 75%, and uses the verb "strikes".
 * Upon purchase, it increases the buyer's maximum health by 15 and applies additional effects.
 * </p>
 * <p>
 * Implements {@link Buyable}, allowing it to be part of a merchant's wares.
 * </p>
 *
 * Example usage:
 * <pre>
 *     Buyable sword = new DragonslayerGreatsword();
 *     sword.onPurchase(player, merchant, gameMap);
 * </pre>
 *
 * @author Mohanad Al-Mansoob
 */
public class DragonslayerGreatsword extends WeaponItem implements Buyable {

    /**
     * The cost of the weapon in Runes.
     */
    private int cost;

    /**
     * A list of effects to apply when the weapon is purchased.
     */
    private EffectsList effectsList;

    /**
     * Constructs a {@code DragonslayerGreatsword} with a specified cost and effects.
     *
     * @param cost         the rune cost to buy the weapon
     * @param effectsList  the effects to apply on purchase
     */
    public DragonslayerGreatsword(int cost, EffectsList effectsList) {

        super("Dragonslayer Greatsword", 'D', 70, "strikes", 75);
        this.cost = cost;
        this.effectsList = effectsList;

    }

    /**
     * Constructs a default {@code DragonslayerGreatsword} with preset cost and no effects.
     */
    public DragonslayerGreatsword() {
        this(1500, new EffectsList());
    }

    /**
     * returns the cost of the weapon
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds the weapon to the buyer's inventory, increases max health by 15,
     * and applies any additional effects.
     * </p>
     */
    @Override
    public void onPurchase(Actor buyer, Merchant merchant, GameMap gameMap) {

        buyer.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15);
        effectsList.apply(buyer, gameMap);
        buyer.addItemToInventory(this);

    }
}
