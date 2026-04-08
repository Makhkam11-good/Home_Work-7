package com.narxoz.rpg.observer;

public class LootDropper implements GameObserver {

    private static final String[] PHASE_LOOT = {
        "Iron Sword",
        "Leather Armor",
        "Health Potion",
        "Mana Crystal",
        "Enchanted Ring"
    };

    private static final String[] BOSS_LOOT = {
        "Legendary Axe",
        "Plate Armor of Power",
        "Elixir of Immortality",
        "Crown of the Fallen",
        "Dragon Scale Shield"
    };

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            dropPhaseLoot(event.getValue());
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            dropBossLoot();
        }
    }

    private void dropPhaseLoot(int phase) {
        String loot = PHASE_LOOT[phase % PHASE_LOOT.length];
        System.out.println("[LOOT] Phase " + phase + " transition dropped: " + loot);
    }

    private void dropBossLoot() {
        String loot = BOSS_LOOT[(int) (Math.random() * BOSS_LOOT.length)];
        System.out.println("[LOOT] Boss defeated! Legendary loot obtained: " + loot);
    }
}
