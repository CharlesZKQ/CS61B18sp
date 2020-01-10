public class ArrayDeque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
    }

    /*move pointer to the previous index, be careful, this is a circular array.*/
    private int minus_one(int index) {
        return (index - 1 + items.length) % items.length;
    }

    /*move pointer to the next index */
    private  int plus_one(int index) {
        return (index + 1) % items.length;
    }

    /* check if the array is full or not. */
    private boolean is_full() {
        return size == items.length;
    }

    private boolean is_sparse() {
        return items.length >= 16 && (size/items.length) < 0.25;
    }

    private void resize(int capacity) {
        T[] new_items = (T[]) new Object[capacity];
        int old_index = plus_one(nextFirst); // pointer is always one stop ahead than the index
        for (int new_index = 0; new_index < size; new_index++) {
            new_items[new_index] = items[old_index]; //new index starts from 0, which is much easier than the way to replicate the original list.
            old_index = plus_one(old_index); // always remember do not simply add index by 1, since this is a circular array!
        }
        items = new_items;
        nextFirst = capacity - 1; // since the new deque is starting from true 0 index.
        nextLast = size;
    }

    /*Adds an item of type T to the front of the deque */
    public void addFirst(T x) {
        if (is_full()) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        nextFirst = minus_one(nextFirst);
        size += 1;
    }

    /* Adds an item of type T to the back of the deque.*/
    public void addLast(T x) {
        if (is_full()) {
            resize(size * 2);
        }
        items[nextLast] = x;
        nextLast = plus_one(nextLast);
        size += 1;
    }

    /*Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of items in the deque.8*/
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        for (int i = nextFirst; i < items.length; i++) {
            System.out.print(items[i] + " ");
        }
    }

    /* Removes and returns the item at the front of the deque.
    If no such item exists, returns null. */
    public T removeFirst() {
        if (is_sparse()) {
            resize(items.length/2);
        }
        nextFirst = plus_one(nextFirst);
        T toRemove = items[nextFirst];
        items[nextFirst] = null;
        if (!isEmpty()) {
            size -= 1;
        }
        return toRemove;
    }

    /* Removes and returns the item at the back of the deque.
    If no such item exists, returns null. */
    public T removeLast() {
        if (is_sparse()) {
            resize(items.length/2);
        }
        nextLast = minus_one(nextLast);
        T toRemove = items[nextLast];
        items[nextLast] = null;
        if(!isEmpty()) {
            size -= 1;
        }
        return toRemove;
    }

    /*Gets the item at the given index, where 0 is the front,
     *1 is the next item, and so forth. If no such item exists,
     *returns null. Must not alter the deque!
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int start = minus_one(nextFirst);
        return items[(start + index) % items.length];
    }

}
