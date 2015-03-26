import java.util.Arrays;

/**
 * @author zenind
 */
public class BH<Key extends Comparable<Key>> {

    private final Key[] heap;

    private int i = 0;

    public BH(int capacity) {
        this.heap = (Key[]) new Comparable[capacity];
    }

    public void insert(Key...keys) {
        for (Key k : keys) {
            insert(k);
        }
    }

    public void insert(Key key) {
        heap[++i] = key;
        swim(i);
    }

    public Key delMax() {
        Key max = heap[1];

        heap[1] = heap[i--];
        sink(1);
        heap[i + 1] = null;

        return max;
    }

    private void sink(int n) {
        while (2 * n <= i) {
            int j = 2 * n;
            if (j < i && less(j, j + 1)) {
                j = j +1;
            }

            if (!less(n, j)) {
                break;
            }

            exchange(n, j);
            n = j;
        }
    }

    private void swim(int n) {
        while (n > 1  && less(n/2 , n)) {
            exchange(n, n / 2);
            n = n / 2;
        }
    }

    private void exchange(int i, int j) {
        Key old = heap[i];

        heap[i] = heap[j];
        heap[j] = old;
    }

    private boolean less(int i, int j) {
        return heap[i].compareTo(heap[j]) < 0;
    }

    public String toString() {
        return Arrays.toString(this.heap);
    }

    public static void main(String[] args) {
        BH<Integer> heap1 = new BH<Integer>(14);
        heap1.insert(51, 42, 31, 40, 39, 25, 13, 21, 32, 30);
        System.out.println(heap1);

        heap1.delMax();
        heap1.delMax();
        heap1.delMax();
        System.out.println(heap1);
    }
}
