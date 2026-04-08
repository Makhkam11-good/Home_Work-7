package com.narxoz.rpg.strategy;

public class DefensiveStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) (basePower * 0.6);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) (baseDefense * 1.4);
    }

    @Override
    public String getName() {
        return "Defensive";
    }
}
