package ru.protei.concurrency;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

import static org.junit.Assert.*;

public class InMemoryCacheTest {

    private static final Integer KEY = 112;
    private static final String VALUE = "help";

    private static Cache<Integer, String> cache;

    @Before
    public void setUp() throws Exception {
        cache = new InMemoryCache<>();
    }

    @org.junit.Test
    public void put() {
        cache = new InMemoryCache<>(1);
        for (int i = 0; i < 2; i++) {
            cache.put(i, VALUE);
            assertEquals(
                    VALUE,
                    cache.get(i, (o) -> null)
            );
        }
    }

    @org.junit.Test
    public void get() {
        assertEquals(
                0,
                cache.size()
        );
        cache.put(KEY, VALUE);
        assertEquals(
                VALUE,
                cache.get(KEY, (o) -> null)
        );
    }

    @org.junit.Test
    public void size() {
        assertEquals(
                0,
                cache.size()
        );
        cache.put(KEY, VALUE);
        assertEquals(
                1,
                cache.size()
        );
    }

    @org.junit.Test
    public void clear() {
        cache.put(KEY, VALUE);
        cache.clear();
        assertEquals(
                0,
                cache.size()
        );
    }

//    @Test
    public void testConcurrency() {
        final int READ_TREADS_COUNT = 15;
        final int WRITE_TREADS_COUNT = 10;

        List<Callable<String>> callableReadList = new ArrayList<>();
        for (int i = 0; i < READ_TREADS_COUNT; i++) {
            callableReadList.add(new CacheReader<>(cache, i, (o) -> null));
        }

        List<Callable<String>> callableWriteList = new ArrayList<>();
        for (int i = 0; i < WRITE_TREADS_COUNT; i++) {
            callableWriteList.add(new CacheWriter<>(cache, i, VALUE));
        }

        ExecutorService readService = Executors.newFixedThreadPool(READ_TREADS_COUNT);
        ExecutorService writeService = Executors.newFixedThreadPool(WRITE_TREADS_COUNT);

        try {
            List<Future<String>> futureWriteList = writeService.invokeAll(callableWriteList);
            List<Future<String>> futureReadList = readService.invokeAll(callableReadList);

            for (Future<String> future : futureReadList) {
                System.out.println(future.get(40000, TimeUnit.SECONDS));
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private static final class CacheReader<K, V> implements Callable<V> {

        private Cache<K, V> cache;

        private final K key;
        private final Function<K, V> function;

        public CacheReader(Cache<K, V> cache, K key, Function<K, V> function) {
            this.cache = cache;
            this.key = key;
            this.function = function;
        }

        @Override
        public V call() {
            return cache.get(key, function);
        }
    }

    private static final class CacheWriter<K, V> implements Callable<V> {

        private Cache<K, V> cache;

        private final K key;
        private final V value;

        public CacheWriter(Cache<K, V> cache, K key, V value) {
            this.cache = cache;
            this.key = key;
            this.value = value;
        }

        @Override
        public V call() {
            cache.put(key, value);
            return value;
        }
    }
}