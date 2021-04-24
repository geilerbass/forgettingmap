package com.geilerbass;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class PopularMap<K, V> {
    final List<PopularEntry<K, V>> associations = new ArrayList<>();

    public PopularMap() {
    }

    public int getCurrentSize() {
        return associations.size();
    }

    public void addToMap(K key, V content) {
        associations.add(new PopularEntry<K, V>(key, content));
    }

    public void removeAndAdd(K key, V content) {
        removeLeastUsed(associations);
        addToMap(key, content);
    }

    public Optional<V> findContentFor(K key) {
        Optional<PopularEntry<K, V>> content = associations.stream()
                .filter(findByKey(key))
                .findFirst();

        content.ifPresent(PopularEntry::updateAccessData);

        return content.map(PopularEntry::getContent);
    }

    private Predicate<PopularEntry<K, V>> findByKey(K key) {
        return popularEntry -> popularEntry.getKey().equals(key);
    }

    private void removeLeastUsed(List<PopularEntry<K, V>> associations) {
        associations.sort(getComparator());
        associations.remove(0);
    }

    public Comparator<PopularEntry<K, V>> getComparator() {
        Comparator<PopularEntry<K, V>> compareByFrequency = Comparator.comparing(PopularEntry::getFrequency);
        Comparator<PopularEntry<K, V>> compareByTimestamp = Comparator.comparing(PopularEntry::getTimestamp);
        return compareByFrequency.thenComparing(compareByTimestamp);
    }

    static class PopularEntry<K, V> {
        private final K key;
        private final V content;
        private int frequency = 0;
        private Instant timestamp = Instant.now();

        public PopularEntry(K key, V content) {
            this.key = key;
            this.content = content;
        }

        public K getKey() {
            return key;
        }

        public V getContent() {
            return content;
        }

        public int getFrequency() {
            return frequency;
        }

        public void updateAccessData() {
            frequency++;
            timestamp = Instant.now();
        }

        public Instant getTimestamp() {
            return timestamp;
        }
    }
}