package ru.protei.concurrency;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.function.Function;

public class InMemoryCache<K, V> implements Cache<K, V> {

    private static final int DEFAULT_MAX_SIZE = 11;

    private final int maxSize;
    private volatile int size;

    private HashMap<K, V> pages;
    private PriorityQueue<Node<K>> pageAccessTimeQueue;

    private final Object lock;

    public InMemoryCache() {
        this(DEFAULT_MAX_SIZE);
    }

    public InMemoryCache(int maxSize) {
        this.size = 0;
        this.maxSize = maxSize;

        this.pages = new HashMap<>();
        this.pageAccessTimeQueue = new PriorityQueue<>(new NodeComparator());
        this.lock = new Object();
    }

    @Override
    public void put(K key, V value) {

        Node<K> node = new Node<>(key);

        synchronized (lock) {

            int localSize = this.size();
            if (localSize == this.maxSize) {

                Node<K> oldNode = pageAccessTimeQueue.poll();

                if (oldNode != null) {
                    pages.remove(oldNode.getKey());
                }

            } else {
                this.size = localSize + 1;
            }

            pages.put(key, value);
            pageAccessTimeQueue.add(node);
            System.out.println(this);
        }
    }

    @Override
    public V get(K key, Function<K, V> getIfNotInCache) {

        synchronized (lock) {
            V value = pages.get(key);

            if (value != null) {

                if (this.size() == this.maxSize) {
                    pageAccessTimeQueue.poll();
                }
                pageAccessTimeQueue.add(new Node<>(key));
                return value;
            }
        }
        V value = getIfNotInCache.apply(key);
        put(key, value);

        return value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        synchronized (lock) {
            size = 0;
            pages.clear();
            pageAccessTimeQueue.clear();
        }
    }

    @Override
    public String toString() {
        return "InMemoryCache{" +
                "maxSize=" + maxSize +
                ", size=" + size +
                ", pages=" + pages +
                '}';
    }

    private static final class Node<K> implements Comparable<Node<K>> {
        private final K key;
        private final long pageAccessTime;

        private Node(K key) {
            this.key = key;
            this.pageAccessTime = System.currentTimeMillis();
        }

        public K getKey() {
            return key;
        }

        public long getPageAccessTime() {
            return pageAccessTime;
        }

        @Override
        public int compareTo(Node<K> node) {
            return Long.compare(this.getPageAccessTime(), node.getPageAccessTime());
        }
    }

    private static final class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node node1, Node node2) {
            return Long.compare(node1.getPageAccessTime(), node2.getPageAccessTime());
        }
    }
}
