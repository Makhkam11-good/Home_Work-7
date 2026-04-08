package com.narxoz.rpg.observer;

public class BattleLogger implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED:
                System.out.println("[BATTLE] " + event.getSourceName() + " landed an attack! Damage: " + event.getValue());
                break;
            case HERO_LOW_HP:
                System.out.println("[WARNING] " + event.getSourceName() + " is in critical condition! HP: " + event.getValue());
                break;
            case HERO_DIED:
                System.out.println("[DEFEAT] " + event.getSourceName() + " has fallen!");
                break;
            case BOSS_PHASE_CHANGED:
                System.out.println("[PHASE] Boss entered Phase " + event.getValue());
                break;
            case BOSS_DEFEATED:
                System.out.println("[VICTORY] Boss defeated!");
                break;
        }
    }
}
