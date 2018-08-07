package ru.protei.concurrency;

import java.util.function.Function;

public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key, Function<K, V> getIfNotInCache);

    int size();

    void clear();

}
