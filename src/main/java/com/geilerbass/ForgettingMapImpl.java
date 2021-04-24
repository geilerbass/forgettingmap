package com.geilerbass;

import java.util.Optional;

public class ForgettingMapImpl<K, V> implements ForgettingMap<K, V> {
    private final int capacity;
    private final PopularMap<K, V> popularMap = new PopularMap<K, V>();

    public ForgettingMapImpl(int x) {
        capacity = x;
    }

    public synchronized void add(K key, V content) {
        if (popularMap.getCurrentSize() < capacity) {
            popularMap.addToMap(key, content);
        } else {
            popularMap.removeAndAdd(key, content);
        }
    }

    public synchronized Optional<V> find(K key) {
        return popularMap.findContentFor(key);
    }

}
