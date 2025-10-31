package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actors.creatures.boss.BedOfChaos;

import java.util.Random;

/**
 * Intrinsic weapon for BedOfChaos. Deals base damage plus additional
 * attack power from the boss’s growables.
 *
 * @author Arielle Ocampo
 */
public class BedOfChaosWeapon extends IntrinsicWeapon {

    private static final int BASE_DAMAGE = 25;
    private static final int HIT_RATE = 75;
    private final BedOfChaos boss;
    private final Random random = new Random();

    /**
     * Constructs a new BedOfChaosWeapon.
     *
     * @param boss BedOfChaos
     */
    public BedOfChaosWeapon(BedOfChaos boss) {
        super(BASE_DAMAGE, "smacks", HIT_RATE);
        this.boss = boss;
    }

    /**
     * Attempts to attack the target. Rolls a random number [0,100).
     * If roll is less than HIT_RATE, deals damage = BASE_DAMAGE + boss.getTotalAttackPower().
     * Otherwise, misses.
     *
     * @param attacker actor performing the attack (boss)
     * @param target   actor being attacked
     * @param map      game map
     * @return a string describing a hit and damage dealt/a miss
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        int damage = BASE_DAMAGE + boss.getTotalAttackPower();

        if (random.nextInt(100) >= this.hitRate) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);
        return String.format("%s %s %s for %d damage",
                attacker, verb, target, damage);
    }
}
