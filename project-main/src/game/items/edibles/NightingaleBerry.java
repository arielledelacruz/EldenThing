package game.items.edibles;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.EatAction;
import game.effects.AttributeEffect;
import game.effects.EffectsList;
import game.effects.MultiplierEffect;
import game.timemanagement.Phases;
import game.timemanagement.ServiceLocator;
import game.timemanagement.TimeAware;
import game.timemanagement.TimeProvider;

/**
 * A special edible item that provides both a health boost and a temporary damage multiplier.
 * The item disappears from the map during the day phase.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class NightingaleBerry extends Item implements TimeAware, Edible {

    private final TimeProvider timeProvider;
    private final EffectsList effectsList;
    private static final int HEALTH_INCREASE_AMOUNT = 20;
    private static final float MULTIPLIER_UPDATED_VALUE = 1.5f;


    /**
     * Constructor for the NightingaleBerry item.
     * Adds the effects of health regeneration and a damage multiplier boost.
     */
    public NightingaleBerry() {
        super("Nightingale Berry", '^', true);
        this.effectsList = new EffectsList();
        this.saturateEffectsList();
        this.timeProvider = ServiceLocator.getTimeProvider();
    }

    /**
     * Adds the effects of the Nightingale Berry: health increase and damage multiplier.
     */
    private void saturateEffectsList(){
        this.effectsList.addEffect(new AttributeEffect(BaseActorAttributes.HEALTH, HEALTH_INCREASE_AMOUNT));
        this.effectsList.addEffect(new MultiplierEffect(MULTIPLIER_UPDATED_VALUE));
    }

    /**
     * Removes the berry from the map if it is daytime.
     *
     * @param location the current location of the item on the map
     */
    @Override
    public void onTimeChange(Location location) {
        if (this.timeProvider.getCurrentPhase() == Phases.DAY){
            location.removeItem(this);
        }
    }

    /**
     * Called each turn to update the item's state.
     * Handles removal based on time of day.
     *
     * @param currentLocation the location of the item
     */
    @Override
    public void tick(Location currentLocation) {
        this.onTimeChange(currentLocation);
        super.tick(currentLocation);
    }

    /**
     * Applies the effects of the berry to the actor and removes the item from the inventory.
     *
     * @param actor the actor consuming the item
     * @param map   the game map
     * @return a String describing the result
     */

    @Override
    public String eat(Actor actor, GameMap map) {
        effectsList.apply(actor, map);
        actor.removeItemFromInventory(this);
        return actor + " eats the Nightingale Berry.";
    }

    /**
     * Returns the list of actions allowed on this item, including the EatAction.
     *
     * @param owner the actor owning the item
     * @param map   the current game map
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map){
        ActionList actions = super.allowableActions(owner, map);

        actions.add(new EatAction(this));

        return actions;
    }
}
