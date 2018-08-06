package ru.protei.collections;

public interface HashFunctionChangeable<K> {
    void changeHashFunction(int newCapacity, HashFunction<K> function);
}
