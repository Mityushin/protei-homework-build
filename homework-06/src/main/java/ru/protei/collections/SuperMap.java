package ru.protei.collections;

public interface SuperMap<K, V> {
    int size();

    boolean isEmpty();

    V get(K key);

    V put(K key, V value);

    V remove(K key);

    void clear();
}
