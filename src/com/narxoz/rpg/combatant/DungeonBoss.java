package com.narxoz.rpg.combatant;

import com.narxoz.rpg.strategy.*;
import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.engine.EventPublisher;

public class DungeonBoss implements GameObserver {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private int currentPhase;
    private CombatStrategy strategy;
    private final EventPublisher publisher;

    public DungeonBoss(String name, int hp, int attackPower, int defense, EventPublisher publisher) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.currentPhase = 1;
        this.strategy = new Phase1Strategy();
        this.publisher = publisher;
        this.publisher.registerObserver(this);
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            int newPhase = event.getValue();
            if (newPhase != currentPhase) {
                currentPhase = newPhase;
                switchStrategy(newPhase);
            }
        }
    }

    private void switchStrategy(int phase) {
        switch (phase) {
            case 1:
                strategy = new Phase1Strategy();
                break;
            case 2:
                strategy = new Phase2Strategy();
                break;
            case 3:
                strategy = new Phase3Strategy();
                break;
        }
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public String getName()           { return name; }
    public int getHp()                { return hp; }
    public int getMaxHp()             { return maxHp; }
    public int getAttackPower()       { return attackPower; }
    public int getDefense()           { return defense; }
    public int getCurrentPhase()      { return currentPhase; }
    public CombatStrategy getStrategy() { return strategy; }
    public boolean isAlive()          { return hp > 0; }
    public EventPublisher getPublisher() { return publisher; }
}
