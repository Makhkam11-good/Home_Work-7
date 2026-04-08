package com.narxoz.rpg.engine;

import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.DungeonBoss;
import java.util.List;

public class DungeonEngine {

    private final List<Hero> heroes;
    private final DungeonBoss boss;
    private final EventPublisher publisher;
    private static final int MAX_ROUNDS = 100;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, EventPublisher publisher) {
        this.heroes = heroes;
        this.boss = boss;
        this.publisher = publisher;
    }

    public EncounterResult runEncounter() {
        int roundCount = 0;

        while (boss.isAlive() && hasLivingHeroes() && roundCount < MAX_ROUNDS) {
            roundCount++;

            heroesAttackBoss();

            if (!boss.isAlive()) {
                publisher.fireEvent(new GameEvent(GameEventType.BOSS_DEFEATED, boss.getName(), roundCount));
                break;
            }

            bossAttacksHeroes();
        }

        int survivingHeroes = countLivingHeroes();
        boolean heroesWon = !boss.isAlive();

        return new EncounterResult(heroesWon, roundCount, survivingHeroes);
    }

    private void heroesAttackBoss() {
        for (Hero hero : heroes) {
            if (!hero.isAlive()) continue;

            int baseDamage = hero.getAttackPower();
            int actualDamage = hero.getStrategy().calculateDamage(baseDamage);

            int bossDefense = boss.getStrategy().calculateDefense(boss.getDefense());
            int finalDamage = Math.max(1, actualDamage - bossDefense);

            boss.takeDamage(finalDamage);
            publisher.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), finalDamage));

            checkAndFirePhaseChange();

            if (!boss.isAlive()) {
                break;
            }
        }
    }

    private void checkAndFirePhaseChange() {
        int hpPercentage = (boss.getHp() * 100) / boss.getMaxHp();
        int newPhase;

        if (hpPercentage >= 60) {
            newPhase = 1;
        } else if (hpPercentage >= 30) {
            newPhase = 2;
        } else {
            newPhase = 3;
        }

        if (newPhase != boss.getCurrentPhase()) {
            publisher.fireEvent(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, boss.getName(), newPhase));
        }
    }

    private void bossAttacksHeroes() {
        for (Hero hero : heroes) {
            if (!hero.isAlive()) continue;

            int baseDamage = boss.getAttackPower();
            int actualDamage = boss.getStrategy().calculateDamage(baseDamage);

            int heroDefense = hero.getStrategy().calculateDefense(hero.getDefense());
            int finalDamage = Math.max(1, actualDamage - heroDefense);

            hero.takeDamage(finalDamage);
            publisher.fireEvent(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), finalDamage));

            if (hero.isAlive() && hero.getHp() < hero.getMaxHp() * 0.3) {
                publisher.fireEvent(new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp()));
            }

            if (!hero.isAlive()) {
                publisher.fireEvent(new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0));
            }
        }
    }

    private boolean hasLivingHeroes() {
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private int countLivingHeroes() {
        int count = 0;
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                count++;
            }
        }
        return count;
    }
}
