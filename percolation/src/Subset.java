/**
 * @author zenind
 */
public class Subset {

    // unit testing
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String[] strings = StdIn.readAllStrings();

        for (String string : strings) {
            queue.enqueue(string);
        }

        while (k-- > 0) {
            StdOut.println(queue.dequeue());
        }
    }

}
