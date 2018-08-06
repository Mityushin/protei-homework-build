package ru.protei.collections;

public interface HashFunctionChangeable {
    void changeHashFunction(int newCapacity, HashFunction function);
}
