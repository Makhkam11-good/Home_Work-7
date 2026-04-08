package com.narxoz.rpg;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.engine.EventPublisher;
import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.strategy.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== THE CURSED DUNGEON: BOSS ENCOUNTER ===\n");

        EventPublisher publisher = new EventPublisher();

        List<Hero> heroes = new ArrayList<>();
        Hero hero1 = new Hero("Aragorn", 120, 35, 15);
        hero1.setStrategy(new AggressiveStrategy());
        
        Hero hero2 = new Hero("Legolas", 100, 40, 10);
        hero2.setStrategy(new DefensiveStrategy());
        
        Hero hero3 = new Hero("Gimli", 150, 30, 20);
        hero3.setStrategy(new BalancedStrategy());
        
        heroes.add(hero1);
        heroes.add(hero2);
        heroes.add(hero3);

        System.out.println("Heroes created:");
        System.out.println("  - " + hero1.getName() + " (HP: " + hero1.getMaxHp() + ", Strategy: " + hero1.getStrategy().getName() + ")");
        System.out.println("  - " + hero2.getName() + " (HP: " + hero2.getMaxHp() + ", Strategy: " + hero2.getStrategy().getName() + ")");
        System.out.println("  - " + hero3.getName() + " (HP: " + hero3.getMaxHp() + ", Strategy: " + hero3.getStrategy().getName() + ")");
        System.out.println();

        DungeonBoss boss = new DungeonBoss("Ancient Dragon", 500, 45, 12, publisher);
        System.out.println("Boss created: " + boss.getName() + " (HP: " + boss.getMaxHp() + ")");
        System.out.println();

        BattleLogger battleLogger = new BattleLogger();
        AchievementTracker achievementTracker = new AchievementTracker();
        PartySupport partySupport = new PartySupport(heroes);
        HeroStatusMonitor heroStatusMonitor = new HeroStatusMonitor(heroes);
        LootDropper lootDropper = new LootDropper();

        publisher.registerObserver(battleLogger);
        publisher.registerObserver(achievementTracker);
        publisher.registerObserver(partySupport);
        publisher.registerObserver(heroStatusMonitor);
        publisher.registerObserver(lootDropper);

        System.out.println("All 5 observers registered with the EventPublisher.\n");

        System.out.println("--- HERO STRATEGY SWITCH ---");
        System.out.println("Legolas switches from Defensive to Aggressive strategy!");
        hero2.setStrategy(new AggressiveStrategy());
        System.out.println();

        System.out.println("=== ENCOUNTER START ===\n");

        DungeonEngine engine = new DungeonEngine(heroes, boss, publisher);
        EncounterResult result = engine.runEncounter();

        System.out.println("\n=== ENCOUNTER END ===\n");

        if (result.isHeroesWon()) {
            System.out.println("VICTORY! The heroes defeated the boss!");
        } else {
            System.out.println("DEFEAT! The heroes fell to the boss!");
        }

        System.out.println("Rounds played: " + result.getRoundsPlayed());
        System.out.println("Heroes surviving: " + result.getSurvivingHeroes());

        System.out.println("\nFinal party status:");
        for (Hero hero : heroes) {
            String status = hero.isAlive() ? "ALIVE" : "DEAD";
            System.out.println("  - " + hero.getName() + ": " + hero.getHp() + "/" + hero.getMaxHp() + " HP [" + status + "]");
        }
    }
}
