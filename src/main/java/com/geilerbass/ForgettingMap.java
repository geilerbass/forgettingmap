package com.geilerbass;

import java.util.Optional;

public interface ForgettingMap<K, V> {

    void add(K key, V content);
    Optional<V> find(K key);
}
