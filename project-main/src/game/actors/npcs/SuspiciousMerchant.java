package game.actors.npcs;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.BuyAction;
import game.actions.ScammedAction;
import game.actors.Merchant;
import game.actors.Scammer;
import game.behaviours.NPCController;
import game.behaviours.RunawayBehaviour;
import game.behaviours.WanderBehaviour;
import game.effects.AttributeEffect;
import game.effects.EffectsList;
import game.timemanagement.Phases;
import game.timemanagement.ServiceLocator;
import game.timemanagement.TimeAware;
import game.timemanagement.TimeProvider;
import game.weapons.BroadSword;
import game.weapons.Buyable;
import game.weapons.DragonslayerGreatsword;
import game.weapons.Katana;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A suspicious merchant NPC that can sell items with a chance of scamming buyers.
 * <p>
 * The merchant wanders around, sells items with health effects,
 * reacts to time phases by becoming unconscious during the day,
 * and may run away when it scams a target.
 * </p>
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class SuspiciousMerchant extends NPC implements TimeAware, Merchant, Scammer {
    private static final int SELL_ITEMS_PROBABILITY = 50;
    private static final int ITEM_SELL_PRICE = 500;
    private static final int MERCHANT_HIT_POINTS = 100;
    private static final int CHANCE_OF_SCAMMING = 50;
    private final NPCController controller;
    private final TimeProvider timeProvider;
    private final List<Buyable> sellItems;
    private final Random random;


    /**
     * Constructs a SuspiciousMerchant with the given NPCController.
     * Initializes wandering behaviour and a randomized set of items to sell.
     *
     * @param controller the NPCController managing this merchant's behaviour
     */
    public SuspiciousMerchant(NPCController controller) {
        super("Suspicious Merchant", 'M', MERCHANT_HIT_POINTS, controller);
        this.controller = controller;
        this.timeProvider = ServiceLocator.getTimeProvider();
        this.addBehaviour(1, new WanderBehaviour());

        this.sellItems = new ArrayList<>();
        this.random = new Random();
        this.initializeSellItems();
    }

    /**
     * Randomly initializes the items the merchant can sell,
     * each with some health-boosting effects.
     */
    private void initializeSellItems() {
        if (random.nextInt(100) < SELL_ITEMS_PROBABILITY) {
            EffectsList effects = new EffectsList();
            effects.addEffect(new AttributeEffect(BaseActorAttributes.HEALTH, 20));
            sellItems.add(new BroadSword(ITEM_SELL_PRICE, effects));
        }

        if (random.nextInt(100) < SELL_ITEMS_PROBABILITY) {
            EffectsList effects = new EffectsList();
            effects.addEffect(new AttributeEffect(BaseActorAttributes.HEALTH, 10));
            sellItems.add(new DragonslayerGreatsword(ITEM_SELL_PRICE, effects));
        }

        if (random.nextInt(100) < SELL_ITEMS_PROBABILITY) {
            EffectsList effects = new EffectsList();
            effects.addEffect(new AttributeEffect(BaseActorAttributes.HEALTH, 20));
            sellItems.add(new Katana(ITEM_SELL_PRICE, effects));
        }
    }

    /**
     * Called each turn to get the merchant's next action.
     * Reacts to time phase changes before delegating action choice to the controller.
     *
     * @param actions the list of possible actions for this turn
     * @param lastAction the last action performed
     * @param map the game map
     * @param display the display interface
     * @return the chosen action
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        this.onTimeChange(map.locationOf(this));
        return controller.playturn(this.getBehaviours(), this, map, display);
    }

    /**
     * Reacts to time phase changes.
     * Becomes unconscious during the day if conscious.
     *
     * @param location current location of the merchant
     */
    @Override
    public void onTimeChange(Location location) {
        if (this.timeProvider.getCurrentPhase() == Phases.DAY && this.isConscious()) {
            this.unconscious(location.map());
        }
    }

    /**
     * Returns the list of sell actions available, some of which
     * may be scams based on a random chance.
     *
     * @return an ActionList of buy or scammed actions
     */
    @Override
    public ActionList getSellActions() {
        ActionList actionsList = new ActionList();

        for (Buyable item : sellItems) {
            if (random.nextInt(100) < CHANCE_OF_SCAMMING) {
                actionsList.add(new ScammedAction(item, this));
            } else {
                actionsList.add(new BuyAction(item, this));
            }
        }

        return actionsList;
    }

    /**
     * Returns the list of actions that another actor can perform on Sellen.
     * This includes standard NPC actions and item purchase options.
     *
     * @param otherActor the actor interacting with Sellen
     * @param direction  the direction of Sellen relative to the actor
     * @param map        the game map
     * @return an {@link ActionList} of allowed actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        actions.add(getSellActions());
        return actions;
    }

    /**
     * When this merchant successfully scams a target, it adds a runaway behaviour targeting that actor.
     *
     * @param scammedTarget the actor that was scammed
     * @param map the game map
     */
    @Override
    public void onScam(Actor scammedTarget, GameMap map) {
        this.addBehaviour(0, new RunawayBehaviour(scammedTarget));
    }
}
