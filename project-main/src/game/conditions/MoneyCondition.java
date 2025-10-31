package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A condition that checks whether an {@link Actor}'s current balance is greater than or less than a specified amount.
 * This condition can be used to trigger specific behaviors or dialogue based on an actor's available money.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class MoneyCondition implements Condition {

    /**
     * The money threshold for comparison.
     */
    private final int money;
    /**
     * Determines the comparison type: {@code true} for "greater than", {@code false} for "less than".
     */
    private final boolean higher;

    /**
     * Constructs a {@code MoneyCondition} with a specified threshold and comparison direction.
     *
     * @param money  the amount of money to compare against
     * @param higher {@code true} to check if the actor has more than the given amount,
     *               {@code false} to check if the actor has less
     */
    public MoneyCondition(int money, boolean higher) {
        this.money = money;
        this.higher = higher;
    }

    /**
     * Evaluates whether the actor's money satisfies the condition.
     *
     * @param actor    the actor whose balance is being checked
     * @param location the location of the actor (not used in this condition)
     * @return {@code true} if the condition is satisfied, {@code false} otherwise
     */
    @Override
    public boolean isSatisfied(Actor actor, Location location) {
        if (this.higher) {
            return actor.getBalance() > this.money;
        } else {
            return actor.getBalance() < this.money;
        }
    }
}
