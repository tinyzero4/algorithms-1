import java.util.Arrays;

/**
 * @author zenind
 */
public class QuickFind implements FindUnion {

    private int[] id;

    public QuickFind(int n) {
        this.id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    @Override
    public boolean find(int p, int q) {
        return connected(p, q);
    }

    @Override
    public void union(int p, int q) {
        if (connected(p, q)) {
            return;
        }

        int oldId = id[p];
        int newId = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == oldId) {
                id[i] = newId;
            }
        }
    }

    private boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public static void main(String[] args) {
        QuickFind quickFind = new QuickFind(10);

        //9-3 6-0 2-1 9-0 8-9 8-1
        quickFind.union(9, 3);
        quickFind.union(6, 0);
        quickFind.union(2, 1);
        quickFind.union(9, 0);
        quickFind.union(8, 9);
        quickFind.union(8, 1);

        System.out.println(Arrays.toString(quickFind.id).replaceAll(",",""));
    }
}
