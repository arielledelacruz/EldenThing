package game.actors.npcs;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Status;
import game.behaviours.NPCController;
import game.behaviours.WanderBehaviour;
import game.conditions.EmptyInventoryCondition;
import game.conditions.MoneyCondition;
import game.conditions.NearStatusCondition;
import game.actions.BuyAction;
import game.actors.Merchant;
import game.effects.AttributeEffect;
import game.effects.EffectsList;
import game.effects.MaxAttributeEffect;
import game.grounds.GroundStatus;
import game.weapons.BroadSword;
import game.weapons.Buyable;
import game.weapons.DragonslayerGreatsword;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the NPC Merchant Kale, a vendor that sells items and speaks based on specific conditions.
 * Merchant Kale is an NPC who:
 * <ul>
 *     <li>Wanders randomly using {@link WanderBehaviour}</li>
 *     <li>Says different {@link Monologue} messages based on environmental and player conditions</li>
 *     <li>Offers items for sale through {@link BuyAction}</li>
 * </ul>
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class MerchantKale extends NPC implements Merchant {

    private static final int MERCHANT_KALE_HP = 200;
    private static final int MONEY_THRESHOLD = 500;

    private static final int BROADSWORD_PRICE = 150;
    private static final int BROADSWORD_STAMINA_MAX_BOOST = 30;

    private static final int DRAGONSLAYER_GREATSWORD_PRICE = 1700;
    private static final int DRAGONSLAYER_GREATSWORD_STAMINA_BOOST = 20;


    /**
     * List of items available for purchase from Merchant Kale.
     */
    private final List<Buyable> sellItems = new ArrayList<>();

    /**
     * Constructs a new instance of Merchant Kale, initializing monologues, behaviors, and merchandise.
     */
    public MerchantKale(NPCController controller) {
        super("Merchant Kale", 'k', MERCHANT_KALE_HP, controller);
        this.addBehaviour(0, new WanderBehaviour());

        // Adding monologues to the pool
        this.addIntoMonologuePool(new Monologue("A merchant’s life is a lonely one. But the roads… they whisper secrets to those who listen."));
        this.addIntoMonologuePool(new Monologue(new NearStatusCondition(this, Status.CURSED), "Rest by the flame when you can, friend. These lands will wear you thin."));
        this.addIntoMonologuePool(new Monologue(new EmptyInventoryCondition(), "Not a scrap to your name? Even a farmer should carry a trinket or two."));
        this.addIntoMonologuePool(new Monologue(new MoneyCondition(MONEY_THRESHOLD, false), "Ah, hard times, I see. Keep your head low and your blade sharp."));

        // setup sellItems
        initializeSellItems();

    }

    private void initializeSellItems() {

        EffectsList broadSwordEffects = new EffectsList();
        broadSwordEffects.addEffect(new MaxAttributeEffect(BaseActorAttributes.STAMINA, BROADSWORD_STAMINA_MAX_BOOST));
        sellItems.add(new BroadSword(BROADSWORD_PRICE, broadSwordEffects));

        EffectsList dragonslayerGreatswordEffects = new EffectsList();
        dragonslayerGreatswordEffects.addEffect(new AttributeEffect(BaseActorAttributes.STAMINA, DRAGONSLAYER_GREATSWORD_STAMINA_BOOST));
        sellItems.add(new DragonslayerGreatsword(DRAGONSLAYER_GREATSWORD_PRICE, dragonslayerGreatswordEffects));

    }

    /**
     * Returns the action Merchant Kale performs each turn.
     * Delegates to the superclass's behavior-driven logic.
     *
     * @param actions    available actions
     * @param lastAction the action performed last turn
     * @param map        the current game map
     * @param display    the display interface
     * @return an {@link Action} to perform this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Retrieves a list of actions to sell items to other actors (players).
     *
     * @return an {@link ActionList} containing {@link BuyAction}s for each sellable item
     */
    @Override
    public ActionList getSellActions() {
        ActionList actionList = new ActionList();

        for (Buyable item : sellItems) {
            actionList.add(new BuyAction(item, this));
        }

        return actionList;
    }

    /**
     * Returns the list of actions that another actor can perform on Merchant Kale.
     * This includes actions from the superclass (e.g., Listen, Attack) and buying items.
     *
     * @param otherActor the actor interacting with the merchant
     * @param direction  the direction of the merchant from the actor
     * @param map        the map context
     * @return an {@link ActionList} of allowed actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actionList = super.allowableActions(otherActor, direction, map);
        actionList.add(getSellActions());
        return actionList;
    }
}
