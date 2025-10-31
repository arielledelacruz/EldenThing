package game.actors.creatures.boss;

import edu.monash.fit2099.engine.displays.Display;

import java.util.ArrayList;
import java.util.List;

/**
 * A branch under BedOfChaos that adds base attack power and grows growables.
 * On grow(), it grows a new growable (branch/leaf) and then calls grow() on
 * existing growables.
 *
 * @author Arielle Ocampo
 */
public class Branch implements Growable {

    private final BedOfChaos boss;
    private final List<Growable> growables = new ArrayList<>();

    /** Base attack power of a branch. */
    private static final int BRANCH_BASE_ATTACK = 3;

    /** Amount of HP healed when a new leaf is grown. */
    private static final int HEAL_AMOUNT = 5;

    /**
     * Constructs a BranchGrowable on the boss.
     *
     * @param boss BedOfChaos the branch is grown on
     */
    public Branch(BedOfChaos boss) {
        this.boss = boss;
    }

    /**
     * Returns BRANCH_BASE_ATTACK plus the sum of each growable’s attack power.
     *
     * @return total attack power
     */
    @Override
    public int getAttackPower() {
        int total = BRANCH_BASE_ATTACK;
        for (Growable growable : growables) {
            total += growable.getAttackPower();
        }
        return total;
    }


    /**
     * Performs growth by:
     *   - Grows a new Branch or Leaf, print message, heal if leaf is grown.
     *   - Calls grow() on each existing growable from before this turn.
     */
    @Override
    public void grow() {
        List<Growable> existingGrowables = new ArrayList<>(this.growables);

        Display display = new Display();
        display.println("Branch is growing...");

        if (Math.random() < 0.5) {
            Branch newBranch = new Branch(boss);
            this.growables.add(newBranch);
            display.println("It grows a branch...");
        } else {
            Leaf newLeaf = new Leaf(boss);
            this.growables.add(newLeaf);
            display.println("It grows a leaf...");
            boss.heal(HEAL_AMOUNT);
            display.println(boss + " is healed.");
        }

        for (Growable growable : existingGrowables) {
            growable.grow();
        }
    }
}
