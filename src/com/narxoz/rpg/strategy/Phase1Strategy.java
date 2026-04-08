package com.narxoz.rpg.strategy;

public class Phase1Strategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) (basePower * 1.0);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) (baseDefense * 1.0);
    }

    @Override
    public String getName() {
        return "Phase 1: Measured";
    }
}
