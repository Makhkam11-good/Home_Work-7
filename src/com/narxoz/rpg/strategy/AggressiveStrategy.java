package com.narxoz.rpg.strategy;

public class AggressiveStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) (basePower * 1.4);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) (baseDefense * 0.6);
    }

    @Override
    public String getName() {
        return "Aggressive";
    }
}
