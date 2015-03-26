import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zenind
 */
public class QuickUnion implements FindUnion {

    private int[] id;

    private int[] sz;

    public QuickUnion(int n) {
        this.id = new int[n];
        this.sz = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    @Override
    public boolean find(int p, int q) {
        return root(p) == root(q);
    }

    @Override
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);

        if (i == j) return;

        if (sz[i] < sz[j]) {
            sz[j] += sz[i];
            id[i] = j;
        } else {
            sz[i] += sz[j];
            id[j] = i;
        }
    }

    private int root(int p) {
        while (id[p] != p) {
//            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

    public static void main(String[] args) {
        QuickUnion quickUnion = new QuickUnion(10);

        //0-2 1-9 2-5 4-7 6-2 5-3 1-4 0-1 4-8
        quickUnion.union(0, 2);
        quickUnion.union(1, 9);
        quickUnion.union(2, 5);
        quickUnion.union(4, 7);
        quickUnion.union(6, 2);
        quickUnion.union(5, 3);
        quickUnion.union(1, 4);
        quickUnion.union(0, 1);
        quickUnion.union(4, 8);
        System.out.println(Arrays.toString(quickUnion.id).replaceAll(",", ""));

        Map<Integer, Integer> sum = new LinkedHashMap<Integer, Integer>();
        for (int i=1; i< 10; i++) {
            sum.put(i, sum(i));
        }

        System.out.println(sum);


    }

    private static int sum(int N) {
        int sum = 0;
        for (int i = 0; i < N; i++)
            for (int j = i; j < N; j++)
                for (int k = i; k <= j; k++)
                    sum++;

        return sum;
    }
}
