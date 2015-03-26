import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author zenind
 */
@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;

    private int n;

    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = resize(null, 1);
        n = 0;
        size = 0;
    }

    private RandomizedQueue(Item[] items, int n, int size) {
        this.items = resize(items, items.length);
        this.n = n;
        this.size = size;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (n == items.length) {
            items = resize(items, items.length * 2);
        }

        items[n] = item;
        n++;
        size++;
    }


    public Item dequeue() {
        return dequeue(true);
    }

    // remove and return a random item
    private Item dequeue(boolean compact) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int i = StdRandom.uniform(n);
        Item item = items[i];

        items[i] = items[n - 1];
        items[--n] = null;

        size--;

        if (compact && size == items.length / 4) {
            items = compact(items, size);
            n = items.length;
        }

        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int i = StdRandom.uniform(n);
        return items[i];
    }


    private Item[] compact(Item[] oldItems, int itSize) {
        Item[] newItems = (Item[]) new Object[itSize];

        int newSize = 0;
        for (int i = 0; i < oldItems.length; i++) {
            if (oldItems[i] != null) {
                newItems[newSize++] = oldItems[i];
            }
        }

        return newItems;
    }

    private Item[] resize(Item[] oldItems, int capacity) {
        int newCapacity = capacity == 0 ? 1 : capacity;
        Item[] newItems = (Item[]) new Object[newCapacity];

        if (oldItems == null) {
            return newItems;
        }

        for (int i = 0; i < oldItems.length; i++) {
            newItems[i] = oldItems[i];
        }

        return newItems;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(new RandomizedQueue<>(items, n, size));
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private final RandomizedQueue<Item> queue;

        public RandomizedQueueIterator(RandomizedQueue<Item> queue) {
            this.queue = queue;
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Item next() {
            return queue.dequeue(false);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue1 = new RandomizedQueue<Integer>();
        queue1.enqueue(1);
        queue1.enqueue(2);
        queue1.enqueue(3);
        queue1.enqueue(4);
        queue1.enqueue(5);
        queue1.enqueue(6);
        queue1.enqueue(7);
        queue1.dequeue();
        queue1.enqueue(8);
        queue1.enqueue(9);

        queue1.dequeue();
        queue1.dequeue();
        queue1.dequeue();
        queue1.dequeue();
        queue1.dequeue();
        queue1.dequeue();
        queue1.dequeue();
        queue1.dequeue();

        queue1.enqueue(1);
        queue1.dequeue();
        queue1.enqueue(221);

        for (int i : queue1) {
            System.out.println(i);
        }
    }
}
