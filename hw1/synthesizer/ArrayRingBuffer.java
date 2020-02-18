// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
// package <package name>;
import java.util.Iterator;


//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()){
            throw new RuntimeException("Ring buffer overflow");
        } else{
            rb[last] = x;
            last = (last + 1) % capacity();
            fillCount += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if(isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        } else{
            T itemToReturn = rb[first];
            rb[first] = null;
            first = (first + 1) % capacity();
            fillCount -= 1;
            return itemToReturn;
        }

    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (isEmpty()){
            throw new RuntimeException("Ring buffer is empty");
        }
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    public Iterator<T> iterator(){
       return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;
        private int count;


        public ArrayRingBufferIterator(){
            pos = first;
            count = 0;
        }

        public boolean hasNext() {
            return count < fillCount();
        }

        public T next() {
            T returnItem = rb[pos];
            pos = (pos + 1) % capacity();
            count += 1;
            return returnItem;
        }

    }
    @Override
    public boolean equals(Object other){

        if (other == this) {// optimization check.

            return true;

        }

        if (other == null) {//null can never be equalled.

            return false;

        }

        if (other.getClass() != this.getClass()) { //check if these two objects come from the same class

            return false;

        }
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) other;
        if (o.fillCount() != this.fillCount()){
            return false;
        }
        Iterator<T> oIterator = o.iterator();
        Iterator<T> thisIterator = this.iterator();
        while (oIterator.hasNext() && thisIterator.hasNext()){
            if (oIterator.next() != thisIterator.next()){
                return false;
            }
        }
        return true;
    }
}
