package org.example.in.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс реализующий показания счетчиков
 */
public class Counters {
    /**
     * Переменная отвечающая за счетчики, {@code Map.Entry} состоит из названия счетчика и его значения
     */
    private final Map<String, Integer> counters;

    public Counters(Map<String, Integer> counters) {
        this.counters = counters;
    }

    public Counters(Counters oldCounters) {
        counters = new HashMap<>();
        for (String title : oldCounters.getCounters().keySet()) {
            counters.put(title, oldCounters.getCounters().get(title));
        }
    }

    public Map<String, Integer> getCounters() {
        return counters;
    }
}
