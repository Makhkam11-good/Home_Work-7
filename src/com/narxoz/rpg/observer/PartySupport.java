package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;
import java.util.Random;

public class PartySupport implements GameObserver {

    private final List<Hero> heroes;
    private final Random random = new Random();
    private static final int HEAL_AMOUNT = 30;

    public PartySupport(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP) {
            healAlly();
        }
    }

    private void healAlly() {
        List<Hero> livingHeroes = heroes.stream()
            .filter(Hero::isAlive)
            .toList();

        if (livingHeroes.isEmpty()) {
            return;
        }

        Hero heroToHeal = livingHeroes.get(random.nextInt(livingHeroes.size()));
        heroToHeal.heal(HEAL_AMOUNT);
        System.out.println("[SUPPORT] " + heroToHeal.getName() + " was healed for " + HEAL_AMOUNT + " HP!");
    }
}
