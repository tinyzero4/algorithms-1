import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author zenind
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;

    private Node<Item> last;

    private int size;

    public Deque() { }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (isEmpty()) {
            first = new Node<Item>(item, null, null);
            last = first;
        } else {
            Node<Item> newFirst = new Node<Item>(item, first, null);
            first.next = newFirst;
            first = newFirst;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (isEmpty()) {
            last = new Node<Item>(item, null, null);
            first = last;
        } else {
            Node<Item> newLast = new Node<Item>(item, null, last);
            last.prev = newLast;
            last = newLast;
        }

        size++;
    }


    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node<Item> item = first;
        first = first.prev;
        if (first != null) {
            first.next = null;
        }

        size--;

        if (isEmpty()) {
            first = null;
            last = null;
        }

        return item.value;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node<Item> item = last;
        last = last.next;
        if (last != null) {
            last.prev = null;
        }
        size--;

        if (isEmpty()) {
            first = null;
            last = null;
        }

        return item.value;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {

        private Node<Item> first = Deque.this.first;

        @Override
        public boolean hasNext() {
            return first != null;
        }

        @Override
        public Item next() {
            if (first == null) {
                throw new NoSuchElementException();
            }

            Item value = first.value;
            first = first.prev;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class Node<Item> {

        private Item value;

        private Node<Item> prev;

        private Node<Item> next;

        public Node(Item value, Node<Item> prev, Node<Item> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public Item getValue() {
            return value;
        }

        public Node<Item> getPrev() {
            return prev;
        }

        public Node<Item> getNext() {
            return next;
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> queue1 = new Deque<Integer>();

        // Check iterator() after intermixed calls to addFirst(), addLast(),
        // removeFirst(), and removeLast()
        queue1.addFirst(1);
        queue1.addFirst(2);
        queue1.addFirst(3);
        queue1.addFirst(4);
        queue1.addFirst(5);
        queue1.addFirst(6);
        queue1.addFirst(7);
        queue1.addFirst(8);

        queue1.addLast(9);

        queue1.removeFirst();
        queue1.removeFirst();
        queue1.removeLast();
        queue1.removeLast();
        queue1.removeLast();
        queue1.removeLast();


        queue1.size();

//        Iterator<Integer> it = queue1.iterator();

//        System.out.println(queue1.removeFirst());

//        System.out.println(queue1.size);

        for (int i : queue1) {
            System.out.println(i);
        }
    }
}
