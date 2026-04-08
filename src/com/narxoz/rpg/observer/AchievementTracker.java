package com.narxoz.rpg.observer;

import java.util.HashSet;
import java.util.Set;

public class AchievementTracker implements GameObserver {

    private final Set<String> unlockedAchievements = new HashSet<>();
    private int attackCount = 0;

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED:
                handleAttackLanded();
                break;
            case BOSS_DEFEATED:
                unlockAchievement("Boss Slayer", "Defeated the boss!");
                break;
            case HERO_DIED:
                break;
            default:
                break;
        }
    }

    private void handleAttackLanded() {
        attackCount++;
        
        if (attackCount == 1) {
            unlockAchievement("First Blood", "Landed the first attack!");
        }
        
        if (attackCount == 10) {
            unlockAchievement("Relentless", "Landed 10+ attacks on the boss!");
        }
    }

    private void unlockAchievement(String title, String description) {
        if (!unlockedAchievements.contains(title)) {
            unlockedAchievements.add(title);
            System.out.println("[ACHIEVEMENT UNLOCKED] " + title + " - " + description);
        }
    }
}
