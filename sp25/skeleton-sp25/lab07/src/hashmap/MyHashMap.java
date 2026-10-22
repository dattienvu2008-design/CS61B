package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Have not completed (Downsize (when remove too much) incomplete)
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Dat dep trai s1tg:>
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int numBuckets = 16, size;
    private double lfThreshold = 0.75;
    private HashSet<K> keys = new HashSet<>();

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[numBuckets];
    }

    public MyHashMap(int initialCapacity) {
        numBuckets = initialCapacity;
        buckets = new Collection[numBuckets];
    }
    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        numBuckets = initialCapacity;
        lfThreshold = loadFactor;
        buckets = new Collection[numBuckets];
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;
        int hash;

        Node(K k, V v, int h) {
            key = k;
            value = v;
            hash = h;

        }
    }

    private int hashToBucket(int hash, int numBuckets) {
        return Math.floorMod(hash, numBuckets);
    }

    private void resize(int multiplier) {
        int newNumBuckets = numBuckets * multiplier;
        Collection<Node>[] newBucket = new Collection[newNumBuckets];
        int currentBucket;
        Node thisNode;
        for (K key : keys) {
            thisNode = getNode(key);
            currentBucket = hashToBucket(thisNode.hash, newNumBuckets);
            if (newBucket[currentBucket] == null) newBucket[currentBucket] = createBucket();
            newBucket[currentBucket].add(thisNode);
        }
        buckets = newBucket;
        numBuckets = newNumBuckets;
    }

    @Override
    public void put(K key, V value) {
        int keyHash = key.hashCode();
        int currentBucketIndex = hashToBucket(keyHash, numBuckets);
        if (!containsKey(key)) {
            Node currentNode = new Node(key, value, keyHash);
            if (buckets[currentBucketIndex] == null) buckets[currentBucketIndex] = createBucket();
            buckets[currentBucketIndex].add(currentNode);
            keys.add(key);
            size++;
            if (((double) size) / ((double) numBuckets) >= lfThreshold) resize(2);
        }
        if (containsKey(key)) {
            Node needModified = getNode(key);
            needModified.value = value;
        }
    }

    public Node getNode(K key) {
        int keyHash = key.hashCode();
        int currentBucketIndex = hashToBucket(keyHash, numBuckets);
        for (Node node : buckets[currentBucketIndex]) {
            if (node.key.equals(key)) return node;
        }
        return null;
    }

    @Override
    public V get(K key) {
        try {
            return getNode(key).value;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        numBuckets = 16;
        keys = new HashSet<>();
        buckets = new Collection[16];
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        keys.remove(key);
        int currentBucketIndex = hashToBucket(key.hashCode(), numBuckets);
        ArrayList<Node> currentBucket = (ArrayList<Node>) buckets[currentBucketIndex];
        if (currentBucket == null) return null;
        Node currentNode;
        for (int i = 0; i < currentBucket.size(); i++) {
            currentNode = currentBucket.get(i);
            if (currentNode.key.equals(key)) {
                currentBucket.remove(currentNode);
                keys.remove(key);
                size--;
                /*
                 * Downsize by resize(0.5) when condition meet. Have not implemented bc I don't know how to choose
                 * the condition x_x
                 * */
                return currentNode.value;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
