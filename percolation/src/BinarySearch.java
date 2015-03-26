import java.util.Arrays;

/**
 * @author zenind
 */
public class BinarySearch {

    private static int binarySearch(int key, int[] array) {
        int lowBound = 0;
        int highBound = array.length - 1;

        while (lowBound <= highBound) {
            int splitPoint = lowBound + (highBound - lowBound) / 2;
            if (key > array[splitPoint]) lowBound = splitPoint + 1;
            else if (key < array[splitPoint]) highBound = splitPoint - 1;
            else return array[splitPoint];
        }

        return -1;
    }

    public static void main(String[] args) {

        System.out.println((0+15)/2);
        System.out.println(2.0e-6 * 1000000.1);
        System.out.println(true && false || true && true);

        System.out.println(Math.abs(-2147483648));
//        int[] input = In.readInts("tiny.txt");
//
//        StdOut.println("Sort");
//        Arrays.sort(input);
//
//        StdOut.println("Filter");
//        while (!StdIn.isEmpty()) {
//            int key = StdIn.readInt();
//            if (binarySearch(key, input) != -1) {
//                StdOut.println(key);
//            }
//        }
    }

}
