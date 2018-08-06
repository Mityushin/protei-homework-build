package ru.protei.collections;

public interface SuperMap<K, V> {
    int size();

    boolean isEmpty();

    V get(Object var1);

    V put(K key, V value);

    V remove(Object var1);

    void clear();
}
