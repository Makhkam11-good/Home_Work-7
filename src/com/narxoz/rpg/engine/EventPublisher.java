package com.narxoz.rpg.engine;

import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.observer.GameEvent;
import java.util.ArrayList;
import java.util.List;

public class EventPublisher {

    private final List<GameObserver> observers = new ArrayList<>();

    public void registerObserver(GameObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unregisterObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void fireEvent(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}
