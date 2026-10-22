import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    public class BSTNode{
        public K key;
        public V value;
        public BSTNode left, right, parent;
        public BSTNode(K k, V v, BSTNode p) {
            key = k;
            value = v;
            left = right = null;
            parent = p;
        }

        public void putHelper(K key, V value) {
            int cmp = key.compareTo(this.key);
            if (cmp > 0) {
                if (right != null) right.putHelper(key, value);
                else {
                    size++;
                    right = new BSTNode(key, value, this);
                }
            }
            if (cmp < 0) {
                if (left != null) left.putHelper(key, value);
                else {
                    size++;
                    left = new BSTNode(key, value, this);
                }
            }
        }

        /* Return BSTNode with this.key = key
        *  Return null if key not found
        * */
        public BSTNode findNode(K key) {
            int cmp = key.compareTo(this.key);
            if (cmp == 0) return this;
            if (cmp > 0) {
                if (right != null) return right.findNode(key);
                else return new BSTNode(null, null, null);
            }
            if (cmp < 0) {
                if (left != null) return left.findNode(key);
                else return new BSTNode(null, null, null);
            }
            return null;
        }
    }
    //Attributes:
    private BSTNode first;
    private int size;

    @Override
    public void put(K key, V value) {
        if (first == null) first = new BSTNode(key, value, null);
        else {
            first.putHelper(key, value);
        }
    }

    @Override
    public V get(K key) {
        return first.findNode(key).value;
    }

    @Override
    public boolean containsKey(K key) {
        return first.findNode(key).key != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        first = null;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        //incomplete
        BSTNode removed_node = first.findNode(key);
        if (removed_node.key == null) return null;
        int cmp = key.compareTo(removed_node.parent.key);
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
