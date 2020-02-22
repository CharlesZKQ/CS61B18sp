package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Charles
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp > 0) {
            return getHelper(key, p.right);
        } else if (cmp < 0) {
            return getHelper(key, p.left);
        } else {
            return p.value;
        }

        //throw new UnsupportedOperationException();
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.value = value;
        }
        return p;
        //throw new UnsupportedOperationException();
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        //throw new UnsupportedOperationException();
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
        //throw new UnsupportedOperationException();
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        keySetHelper(keySet, root);
        return keySet;
        //throw new UnsupportedOperationException();
    }
    private void keySetHelper (Set<K> keySet, Node p) {
        if (p == null) {
            return;
        }
        keySet.add(p.key);
        keySetHelper(keySet, p.left);
        keySetHelper(keySet, p.right);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        V valRemoved = get(key);
        removeHelper(key, root);
        return valRemoved;
        //throw new UnsupportedOperationException();
    }

    //removes the key value pair from a tree whose root is p
    private void removeHelper(K key, Node p) {
        int cmp = key.compareTo(p.key);
        if (cmp > 0) {
            removeHelper(key, p.right);
        } else if (cmp < 0) {
            removeHelper(key, p.left);
        } else {
            size -= 1;
            switch (deleteCase(p)) {
                case 1:
                    p = p.left;
                    break;
                case 2:
                    p = p.right;
                    break;
                case 3:
                    Node leftMax = findMaxNode(p.left);
                    p.key = leftMax.key;
                    p.value = leftMax.value;
                    removeHelper(leftMax.key, p.left);
                    break;
                default:
                    p = null;
                    break;
            }
        }
    }

    /** private class help us to determine the tree
     * if tree p doesn't have any child, return 0;
     * @param p which is the input BST
     * @return return the case which is non-child, one left child, one right child, both children.
     */
    private int deleteCase(Node p) {
        if (p.left == null && p.right == null) {
            return 0;
        } else if (p.right == null && p.left != null) {
            return 1;
        } else if (p.right != null && p.left == null) {
            return 2;
        } else {
            return 3;
        }
    }

    private Node findMaxNode(Node p) {
        if (p.right == null) {
            return p;
        }
        return findMaxNode(p.right);
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V valRemoved = get(key);
        if (valRemoved == null || valRemoved != value) {
            return null;
        }
        removeHelper(key, root);
        return valRemoved;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
