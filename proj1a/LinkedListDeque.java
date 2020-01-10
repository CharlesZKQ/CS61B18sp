public class LinkedListDeque<T> {
    private class DoubleTNode {
        public T item;
        public DoubleTNode next;
        public DoubleTNode prev;
        public DoubleTNode(DoubleTNode pN, T i, DoubleTNode nN){
            prev = pN;
            item = i;
            next = nN;
        }
    }
    /* the first item should be at sentinel.next, the last item should be at sentinel.prev */
    private DoubleTNode sentinel;
    private int size;

    /* create an empty deque */
    public LinkedListDeque(){
        sentinel = new DoubleTNode(null,null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

//    public LinkedListDeque(int x){
//        sentinel = new DoubleTNode(null,66, null);
//        sentinel.next = new DoubleTNode(null, x, null);
//        sentinel.prev = new DoubleTNode(x, null);
//        size = 1;
//    }

    /*Adds an item of type T to the front of the deque */
    public void addFirst(T x){
        sentinel.next = new DoubleTNode(sentinel, x, sentinel.next);
        //sentinel.next.next.prev = sentinel.next;
        sentinel.prev = sentinel.next;
        size = size + 1;
    }

    /* Adds an item of type T to the back of the deque.*/
    public void addLast(T x) {
        size = size + 1;
        sentinel.prev = new DoubleTNode(sentinel.prev, x, sentinel);
    }

    /*Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty(){
        return size == 0;
    }

    /* Returns the number of items in the deque.8*/
    public int size(){
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        DoubleTNode toPrint = sentinel.next;
        for (int i = 0; i < size; i++){
            System.out.print(toPrint.item + " ");
            toPrint = toPrint.next;
        }
    }

    /* Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst(){
        T remove_first = sentinel.next.item;
        //sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        if (!isEmpty()){
            size -= 1;
        }
         return remove_first;

    }

    /* Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast(){
        T remove_last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        if(!isEmpty()){
            size -= 1;
        }
        return remove_last;
    }

    /*Gets the item at the given index, where 0 is the front,
      *1 is the next item, and so forth. If no such item exists,
      *returns null. Must not alter the deque!
     */
    public T get(int index){
        DoubleTNode p = sentinel.next;
        for(int i = 0; i < index; i++){
            p = p.next;
        }

        return p.item;
    }

    /* Same as get, but uses recursion. */
    private T getRecursive_helper(int index, DoubleTNode cur){
        if(index == 0){
            return cur.item;
        }
        return getRecursive_helper(index - 1, cur.next);
    }

    public T getRecursive(int index){
        return getRecursive_helper(index, sentinel.next);
    }
//    public static void main(String[] args) {
//        LinkedListDeque L = new LinkedListDeque();
//        L.addFirst(5);
//        L.addFirst(10);
//        L.addFirst(15);
//        System.out.println(L.size());
//    }
}
