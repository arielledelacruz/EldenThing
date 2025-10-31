package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.actors.Ability;

/**
 * A class representing a Talisman that an actor can pick up and drop
 * @author Adrian Kristanto
 */
public class Talisman extends Item {
    public Talisman() {
        super("Talisman", 'o', true);
        this.addCapability(Ability.CURE);
    }
}
