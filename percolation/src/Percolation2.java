/**
 * @author zenind
 */
public class Percolation2 {

    private final WeightedQuickUnionUF quickUnion;

    private final WeightedQuickUnionUF quickUnion2;

    private final int n;

    private final int sitesCount;

    private final boolean[] sitesState;

    private int openSites = 0;

    // create N-by-N grid, with all sites blocked
    public Percolation2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N <= 0");
        }
        this.n = n;
        this.sitesCount = n * n;
        this.sitesState = new boolean[sitesCount];
        this.quickUnion = new WeightedQuickUnionUF(sitesCount + 2);
        this.quickUnion2 = new WeightedQuickUnionUF(sitesCount + 2);

        for (int i = 1; i <= n; i++) {
            quickUnion.union(sitesCount, getSiteAt(1, i));
            quickUnion2.union(sitesCount, getSiteAt(1, i));
            quickUnion.union(sitesCount + 1, getSiteAt(n, i));
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        checkSiteIndexes(i, j);
        int siteAt = getSiteAt(i, j);
        if (!isOpen(i, j)) {
            sitesState[siteAt] = true;
            openSites++;
            if (isInBounds(i - 1, j) && isOpen(i - 1, j)) {
                quickUnion.union(getSiteAt(i - 1, j), getSiteAt(i, j));
                quickUnion2.union(getSiteAt(i - 1, j), getSiteAt(i, j));
            }

            if (isInBounds(i + 1, j) && isOpen(i + 1, j)) {
                quickUnion.union(getSiteAt(i + 1, j), getSiteAt(i, j));
                quickUnion2.union(getSiteAt(i + 1, j), getSiteAt(i, j));
            }

            if (isInBounds(i, j - 1) && isOpen(i, j - 1)) {
                quickUnion.union(getSiteAt(i, j - 1), getSiteAt(i, j));
                quickUnion2.union(getSiteAt(i, j - 1), getSiteAt(i, j));
            }

            if (isInBounds(i, j + 1) && isOpen(i, j + 1)) {
                quickUnion.union(getSiteAt(i, j + 1), getSiteAt(i, j));
                quickUnion2.union(getSiteAt(i, j + 1), getSiteAt(i, j));
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkSiteIndexes(i, j);
        return sitesState[getSiteAt(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkSiteIndexes(i, j);
        return isOpen(i, j) && quickUnion2.connected(getSiteAt(i, j), sitesCount);
    }

    // does the system percolate?
    public boolean percolates() {
        return openSites > 0 && quickUnion.connected(sitesCount, sitesCount + 1);
    }

    private int getSiteAt(int i, int j) {
        return (i - 1) * n + j - 1;
    }

    private void checkSiteIndexes(int i, int j) {
        if (!isInBounds(i, j)) {
            throw new IndexOutOfBoundsException(String.format("i and j should be in interval [1, N]"));
        }
    }

    private boolean isInBounds(int i, int j) {
        return (i >= 1 && i <= n) && (j >= 1 && j <= n);
    }

}
