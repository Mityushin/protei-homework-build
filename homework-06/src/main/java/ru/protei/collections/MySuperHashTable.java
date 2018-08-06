package ru.protei.collections;

import java.util.Arrays;
import java.util.Map;

public class MySuperHashTable<K, V> implements SuperMap<K, V>, HashFunctionChangeable {

    private static final int DEFAULT_CAPACITY = 89;

    private static int DEFAULT_HASH_FUNC_0(Object object) {
        int hash = 0;
        for (char ch : String.valueOf(object).toCharArray()) {
            hash = (hash * 27 + ch) % DEFAULT_CAPACITY;
        }
        return hash;
    }

    private int capacity;
    private int size;
    private double loadFactor;

    private Entry<K, V>[] table;

    private HashFunction hashFunc;

    public MySuperHashTable() {
        this(DEFAULT_CAPACITY, MySuperHashTable::DEFAULT_HASH_FUNC_0);
    }

    public MySuperHashTable(int capacity) {
        this(capacity, MySuperHashTable::DEFAULT_HASH_FUNC_0);
    }

    public MySuperHashTable(HashFunction function) {
        this(DEFAULT_CAPACITY, function);
    }

    public MySuperHashTable(int capacity, HashFunction function) {
        this.capacity = capacity;
        this.size = 0;
        this.loadFactor = 0;
        this.hashFunc = function;
        this.table = (Entry<K, V>[]) new Entry[capacity];
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    private double updateLoadFactor() {
        return loadFactor = (double) (size / capacity);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public V get(Object o) {
        int hash = hashFunc.apply(o);
        MySuperHashTable.Entry<K, V> entry = table[hash];
        return entry != null ? entry.getValue() : null;
    }

    @Override
    public V put(K key, V value) {
        size++;
        updateLoadFactor();
        int hash = hashFunc.apply(key);
        MySuperHashTable.Entry<K, V> entry = table[hash];
        table[hash] = new MySuperHashTable.Entry<K, V>(hash, key, value, entry);
        return entry != null ? entry.getValue() : null;
    }

    @Override
    public V remove(Object o) {
        int hash = hashFunc.apply(o);
        MySuperHashTable.Entry<K, V> entry = table[hash];
        if (entry != null) {
            size--;
            updateLoadFactor();
            table[hash] = entry.next;
        }
        return entry != null ? entry.getValue() : null;
    }

    @Override
    public void clear() {
        size = 0;
        loadFactor = 0;
        table = (Entry<K, V>[]) new Entry[capacity];
    }

    @Override
    public void changeHashFunction(int newCapacity, HashFunction function) {

        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newCapacity];

        for (Entry<K, V> entry : table) {
            if (entry != null) {
                int hash = function.apply(entry.getKey());
                newTable[hash] = new Entry<>(hash, entry.getKey(), entry.getValue(), newTable[hash]);
            }
        }
        table = newTable;
        hashFunc = function;
        capacity = newCapacity;
        updateLoadFactor();
    }

    @Override
    public String toString() {
        return "MySuperHashTable{" +
                "capacity=" + capacity +
                ", size=" + size +
                ", loadFactor=" + loadFactor +
                ", table=" + Arrays.toString(table) +
                ", hashFunc=" + hashFunc +
                '}';
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        MySuperHashTable.Entry<K, V> next;

        Entry(int hash, K key, V value, MySuperHashTable.Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            return this.value = v;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "hash=" + hash +
                    ", key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    '}';
        }
    }
}
