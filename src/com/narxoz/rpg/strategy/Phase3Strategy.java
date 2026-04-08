package com.narxoz.rpg.strategy;

public class Phase3Strategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) (basePower * 2.0);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) (baseDefense * 0.3);
    }

    @Override
    public String getName() {
        return "Phase 3: Desperate";
    }
}
