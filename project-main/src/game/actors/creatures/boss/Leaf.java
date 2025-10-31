package game.actors.creatures.boss;

/**
 * A leaf grown on BedOfChaos that adds attack power
 * and heals the boss when it grows.
 *
 * @author Arielle Ocampo
 */
public class Leaf implements Growable {

    /** Attack power contribution of a leaf. */
    private static final int LEAF_ATTACK_POWER = 1;

    /** Amount of HP healed when leaf is grown. */
    private static final int HEAL_AMOUNT = 5;


    private final BedOfChaos boss;

    /**
     * Constructs a Leaf grown on boss.
     *
     * @param boss BedOfChaos to be healed
     */
    public Leaf(BedOfChaos boss) {
        this.boss = boss;
    }


    /**
     * Returns fixed attack power of leaf.
     *
     * @return LEAF_ATTACK_POWER
     */
    @Override
    public int getAttackPower() {
        return LEAF_ATTACK_POWER;
    }

    /**
     * Heals the boss by HEAL_AMOUNT.
     */
    @Override
    public void grow() {
        boss.heal(HEAL_AMOUNT);
    }
}

