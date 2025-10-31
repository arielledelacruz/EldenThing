package game.actors.npcs;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.creatures.GoldenBeetle;
import game.behaviours.NPCController;
import game.behaviours.StandardNPCController;
import game.behaviours.WanderBehaviour;
import game.actions.BuyAction;
import game.actors.Merchant;
import game.actors.creatures.OmenSheep;
import game.effects.AttributeEffect;
import game.effects.EffectsList;
import game.effects.MaxAttributeEffect;
import game.effects.SpawnActorEffect;
import game.weapons.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the NPC Sellen, a mystical merchant who sells magical weapons and delivers cryptic monologues.
 * Sellen:
 * <ul>
 *     <li>Wanders the map using {@link WanderBehaviour}</li>
 *     <li>Delivers static {@link Monologue} lines related to magic and lore</li>
 *     <li>Sells a selection of magical weapons that apply various {@link game.effects.Effect}s upon purchase</li>
 * </ul>
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class Sellen extends NPC implements Merchant {

    private static final int SELLEN_HIT_POINTS = 150;

    // Weapon prices
    private static final int BROAD_SWORD_PRICE = 100;
    private static final int DRAGONSLAYER_GREATSWORD_PRICE = 1500;
    private static final int KATANA_PRICE = 500;

    // Attribute effects
    private static final int BROAD_SWORD_HEALTH_BOOST = 20;
    private static final int KATANA_HEALTH_BOOST = 10;
    private static final int KATANA_STAMINA_MAX_BOOST = 20;

    /**
     * List of items available for purchase from Sellen.
     */
    private final List<Buyable> sellItems = new ArrayList<>();

    /**
     * Constructs a new instance of Sellen, initializing monologues, behavior, and items for sale.
     */
    public Sellen(NPCController controller) {
        super("Sellen", 's', SELLEN_HIT_POINTS, controller);
        this.addBehaviour(0, new WanderBehaviour());

        // Adding monologues to the pool
        this.addIntoMonologuePool(new Monologue("The academy casts out those it fears. Yet knowledge, like the stars, cannot be bound forever"));
        this.addIntoMonologuePool(new Monologue("You sense it too, don’t you? The Glintstone hums, even now."));

        // Setup Sellable items
        initializeSellItems();

    }

    private void initializeSellItems() {

        EffectsList broadSwordEffects = new EffectsList();
        broadSwordEffects.addEffect(new MaxAttributeEffect(BaseActorAttributes.HEALTH, BROAD_SWORD_HEALTH_BOOST));
        sellItems.add(new BroadSword(BROAD_SWORD_PRICE, broadSwordEffects));

        EffectsList dragonslayerGreatswordEffects = new EffectsList();
        dragonslayerGreatswordEffects.addEffect(new SpawnActorEffect(() -> new GoldenBeetle(new StandardNPCController()), null));
        sellItems.add(new DragonslayerGreatsword(DRAGONSLAYER_GREATSWORD_PRICE, dragonslayerGreatswordEffects));

        EffectsList katanaEffects = new EffectsList();
        katanaEffects.addEffect(new SpawnActorEffect(() -> new OmenSheep(new StandardNPCController()), this));
        katanaEffects.addEffect(new AttributeEffect(BaseActorAttributes.HEALTH, KATANA_HEALTH_BOOST));
        katanaEffects.addEffect(new MaxAttributeEffect(BaseActorAttributes.STAMINA, KATANA_STAMINA_MAX_BOOST));
        sellItems.add(new Katana(500, katanaEffects));

    }

    /**
     * Returns the action Sellen will perform on her turn.
     * Delegates to {@link NPC#playTurn(ActionList, Action, GameMap, Display)}.
     *
     * @param actions     actions that can be performed
     * @param lastAction  last action performed
     * @param map         the map the NPC is on
     * @param display     the display to show output
     * @return an {@link Action} to perform this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Retrieves actions that allow players to buy items from Sellen.
     *
     * @return an {@link ActionList} containing {@link BuyAction}s for each item in stock
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
}