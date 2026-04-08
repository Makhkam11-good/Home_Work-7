## UML Diagram 2: Observer Pattern

```
┌────────────────────────────────────┐
│     <<interface>>                  │
│      GameObserver                  │
├────────────────────────────────────┤
│ + onEvent(GameEvent): void         │
└────────────────────────────────────┘
          ▲
          │ implements
          │
    ┌─────┴─────────────────────────────────────────┐
    │                                               │
┌───┴──────────────────┐          ┌────────────────┴─────────────┐
│  BattleLogger         │          │  AchievementTracker          │
├──────────────────────┤          ├──────────────────────────────┤
│ + onEvent(GameEvent) │          │ - unlockedAchievements: Set  │
│                      │          │ - attackCount: int            │
│ Logs all 5 event     │          │ + onEvent(GameEvent)         │
│ types to console     │          │                              │
└──────────────────────┘          │ Unlocks achievements on:     │
                                  │ - ATTACK_LANDED              │
                                  │ - BOSS_DEFEATED              │
                                  └──────────────────────────────┘

          ┌─────────────────────────────────────────────────────────┐
          │                                                         │
    ┌────┴──────────────────┐                    ┌─────────────────┴────────────┐
    │  PartySupport          │                    │  HeroStatusMonitor           │
    ├────────────────────────┤                    ├──────────────────────────────┤
    │ - heroes: List<Hero>   │                    │ - heroes: List<Hero>         │
    │ + onEvent(GameEvent)   │                    │ + onEvent(GameEvent)         │
    │                        │                    │                              │
    │ Heals random living    │                    │ Prints hero party status     │
    │ hero on HERO_LOW_HP    │                    │ on HERO_LOW_HP or HERO_DIED  │
    └────────────────────────┘                    └──────────────────────────────┘
          │
          └─────────────┬───────────────┐
                        │
                    ┌───┴──────────────────┐
                    │   LootDropper        │
                    ├──────────────────────┤
                    │ + onEvent(GameEvent) │
                    │                      │
                    │ Drops loot on:       │
                    │ - BOSS_PHASE_CHANGED │
                    │ - BOSS_DEFEATED      │
                    └──────────────────────┘


    ┌──────────────────────────────────────────────────────────────┐
    │              EventPublisher (Central Hub)                    │
    ├──────────────────────────────────────────────────────────────┤
    │ - observers: List<GameObserver>                              │
    ├──────────────────────────────────────────────────────────────┤
    │ + registerObserver(GameObserver): void                        │
    │ + unregisterObserver(GameObserver): void                      │
    │ + fireEvent(GameEvent): void                                 │
    └──────────────────────────────────────────────────────────────┘
          │
          │ fires events to
          │
          ▼
    ┌──────────────────────────────────────────────────────────────┐
    │                GameEvent (Event Data Class)                  │
    ├──────────────────────────────────────────────────────────────┤
    │ - type: GameEventType                                        │
    │ - sourceName: String                                         │
    │ - value: int                                                 │
    ├──────────────────────────────────────────────────────────────┤
    │ + getType(): GameEventType                                   │
    │ + getSourceName(): String                                    │
    │ + getValue(): int                                            │
    └──────────────────────────────────────────────────────────────┘
          │
          │ type of
          │
          ▼
    ┌──────────────────────────────────────────────────────────────┐
    │               GameEventType (Enumeration)                    │
    ├──────────────────────────────────────────────────────────────┤
    │ • ATTACK_LANDED                                              │
    │ • HERO_LOW_HP                                                │
    │ • HERO_DIED                                                  │
    │ • BOSS_PHASE_CHANGED                                         │
    │ • BOSS_DEFEATED                                              │
    └──────────────────────────────────────────────────────────────┘


    ┌──────────────────────────────────┐
    │       DungeonBoss                 │
    ├──────────────────────────────────┤
    │ implements GameObserver           │
    │ - publisher: EventPublisher       │◄──────┐
    │ + onEvent(GameEvent): void        │       │
    │                                   │       │
    │ Reacts to BOSS_PHASE_CHANGED      │       │
    │ and switches strategy             │       │
    └──────────────────────────────────┘       │
                    │                          │
                    └──────────────────────────┘
              Registers itself with EventPublisher


    ┌──────────────────────────────────┐
    │       DungeonEngine               │
    ├──────────────────────────────────┤
    │ - publisher: EventPublisher       │
    │ - heroes: List<Hero>              │
    │ - boss: DungeonBoss               │
    ├──────────────────────────────────┤
    │ + runEncounter(): EncounterResult │
    │                                   │
    │ Fires events at correct moments:  │
    │ • ATTACK_LANDED (each attack)     │
    │ • HERO_LOW_HP (hero <30% HP)      │
    │ • HERO_DIED (hero HP=0)           │
    │ • BOSS_PHASE_CHANGED (threshold) │
    │ • BOSS_DEFEATED (boss HP=0)      │
    └──────────────────────────────────┘
          │
          │ fires events to
          │
          ▼
    EventPublisher ──► All Observers
```

## Diagram Explanation

**Observer Interface:** Single method contract
- `onEvent(GameEvent)`: Called when an event occurs

**5 Observer Implementations:**

1. **BattleLogger** - Logs all events
   - Reacts to: All 5 event types
   - Prints: Formatted log messages for each event

2. **AchievementTracker** - Achievement system
   - Reacts to: ATTACK_LANDED, BOSS_DEFEATED
   - Tracks: Number of attacks, unlocked achievements
   - Maintains: Set of unlocked achievement names

3. **PartySupport** - Healing support
   - Reacts to: HERO_LOW_HP
   - Action: Heals random living hero for 30 HP
   - Requires: Hero list access

4. **HeroStatusMonitor** - Status tracking
   - Reacts to: HERO_LOW_HP, HERO_DIED
   - Prints: Current HP and status of all heroes
   - Requires: Hero list access

5. **LootDropper** - Loot generation
   - Reacts to: BOSS_PHASE_CHANGED, BOSS_DEFEATED
   - Action: Drops different loot types for each phase
   - Varies: Phase loot vs. boss defeat loot

**EventPublisher (Central Hub):**
- Maintains list of all observers
- registerObserver(): Add observer to list
- fireEvent(): Notify ALL observers when event occurs
- Single point of distribution

**GameEvent (Data Carrier):**
- Carries event type, source name, and value
- Passed to all observers
- Contains: type, sourceName, value

**GameEventType (Event Types):**
1. ATTACK_LANDED - Attack succeeds, carries damage dealt
2. HERO_LOW_HP - Hero HP < 30% of max
3. HERO_DIED - Hero HP reaches 0
4. BOSS_PHASE_CHANGED - Boss crosses HP threshold
5. BOSS_DEFEATED - Boss HP reaches 0

**DungeonEngine:**
- Orchestrates combat
- Fires events at correct moments
- Triggers boss phase changes → EventPublisher → DungeonBoss receives and reacts

**Critical Design:** DungeonBoss is both Publisher and Observer
- Registers itself with EventPublisher in constructor
- Listens for BOSS_PHASE_CHANGED events
- Automatically switches strategy when phase changes
- No direct engine-to-boss strategy-change calls (decoupled)
