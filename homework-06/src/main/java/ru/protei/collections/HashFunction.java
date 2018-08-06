package ru.protei.collections;

public interface HashFunction<K> {
    int apply(K key);
}
