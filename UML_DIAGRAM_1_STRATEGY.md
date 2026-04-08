## UML Diagram 1: Strategy Pattern

```
┌──────────────────────────────────┐
│      <<interface>>               │
│     CombatStrategy              │
├──────────────────────────────────┤
│ + calculateDamage(int): int      │
│ + calculateDefense(int): int     │
│ + getName(): String              │
└──────────────────────────────────┘
          ▲                ▲
          │                │
          │        ┌───────┴─────────┐
          │        │                 │
   ┌──────┴──────┐ │        ┌────────┴─────────┐
   │   Hero      │ │        │  DungeonBoss    │
   │ Strategies  │ │        │  Strategies     │
   └─────────────┘ │        └─────────────────┘
                   │
        ┌──────────┼──────────────┐
        │          │              │
┌───────┴────────┐│┌──────────────┴────────┐
│ Aggressive     │││ Phase 1-3 Strategy    │
│ Strategy       │││ Implementations       │
└────────────────┘││                       │
                  │└──────────────────────┘
    ┌─────────────┴────────┐
    │                      │
┌───┴───────┐      ┌───────┴──────┐
│ Defensive │      │ Balanced     │
│ Strategy  │      │ Strategy     │
└───────────┘      └──────────────┘


    ┌──────────────────┐          ┌───────────────────┐
    │      Hero        │          │   DungeonBoss     │
    ├──────────────────┤          ├───────────────────┤
    │ - name: String   │          │ - name: String    │
    │ - hp: int        │          │ - hp: int         │
    │ - maxHp: int     │          │ - maxHp: int      │
    │ - strategy: CS◄──┼──────────┤ - strategy: CS◄───┤
    │ - attackPower    │  uses    │ - phase: int      │
    │ - defense        │          │ - publisher       │
    └──────────────────┘          └───────────────────┘

Key Relations:
- Hero.strategy: CombatStrategy (1:1)
- DungeonBoss.strategy: CombatStrategy (1:1, changes per phase)
- Both use CombatStrategy interface for damage/defense calculations
```

## Diagram Explanation

**Strategy Interface:** Defines the contract for combat algorithms
- `calculateDamage()`: Apply multiplier to attack power
- `calculateDefense()`: Apply multiplier to defense stat
- `getName()`: Return strategy name for logging

**Hero Strategies:**
- **AggressiveStrategy:** 1.4x damage, 0.6x defense (attack-focused)
- **DefensiveStrategy:** 0.6x damage, 1.4x defense (defense-focused)
- **BalancedStrategy:** 1.0x damage, 1.0x defense (neutral)

**Boss Phase Strategies:**
- **Phase1Strategy:** 1.0x damage, 1.0x defense (measured, 100-60% HP)
- **Phase2Strategy:** 1.5x damage, 0.8x defense (aggressive, 60-30% HP)
- **Phase3Strategy:** 2.0x damage, 0.3x defense (desperate, <30% HP)

**Composition:**
- Hero and DungeonBoss each contain a CombatStrategy instance
- Strategies are stateless and can be swapped at runtime
- Boss strategy changes automatically when phase changes (via observer mechanism)
