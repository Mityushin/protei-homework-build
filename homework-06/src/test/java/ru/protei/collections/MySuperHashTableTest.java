package ru.protei.collections;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySuperHashTableTest {

    private MySuperHashTable<String, String> hashTable;

    @Before
    public void setUp() throws Exception {
        hashTable = new MySuperHashTable<>();
    }

    @org.junit.Test
    public void size() {
        assertEquals(
                0,
                hashTable.size()
        );
        hashTable.put("key", "value");
        assertEquals(
                1,
                hashTable.size()
        );
    }

    @org.junit.Test
    public void isEmpty() {
        assertTrue(hashTable.isEmpty());
        hashTable.put("key", "value");
        assertFalse(hashTable.isEmpty());
    }

    @org.junit.Test
    public void get() {
        hashTable.put("key", "value");
        assertEquals(
                "value",
                hashTable.get("key")
        );
    }

    @org.junit.Test
    public void put() {
        assertEquals(
                null,
                hashTable.put("key", "value")
        );
        assertEquals(
                "value",
                hashTable.put("key", "value2")
        );
    }

    @org.junit.Test
    public void remove() {
        assertEquals(
                null,
                hashTable.remove("key")
        );
        hashTable.put("key", "value");
        assertEquals(
                "value",
                hashTable.remove("key")
        );
    }

    @org.junit.Test
    public void clear() {
        hashTable.put("key", "value");
        hashTable.clear();
        assertEquals(
                0,
                hashTable.size()
        );
    }

    @Test
    public void changeHashFunction() {
        hashTable.put("key", "value");
        hashTable.put("key1", "value1");
        hashTable.put("key2", "value2");

        int oldSize = hashTable.size();
        int oldCapacity = hashTable.getCapacity();

        hashTable.changeHashFunction(hashTable.getCapacity() * 2, (o) -> 0);

        assertEquals(
                oldSize,
                hashTable.size()
        );
        assertNotEquals(
                oldCapacity,
                hashTable.getCapacity()
        );
    }
}