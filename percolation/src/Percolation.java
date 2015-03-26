/**
 * @author zenind
 */
public class Percolation {

    private final WeightedQuickUnionUF quickUnion;

    private final int n;

    private final int sitesCount;

    private final SiteInfo[] siteInfos;

    private int openSites = 0;

    // create N-by-N grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N <= 0");
        }
        this.n = n;
        this.sitesCount = n * n;
        this.siteInfos = new SiteInfo[sitesCount];
        this.quickUnion = new WeightedQuickUnionUF(sitesCount + 2);

        for (int i = 1; i <= n; i++) {
            siteInfos[getSiteAt(1, i)] = new SiteInfo(false, getSiteAt(1, i), true);
            quickUnion.union(sitesCount, getSiteAt(1, i));
            quickUnion.union(sitesCount + 1, getSiteAt(n, i));
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        checkSiteIndexes(i, j);
        int siteAt = getSiteAt(i, j);
        if (!isOpen(i, j)) {

            siteInfos[siteAt].setOpen(true);
            openSites++;

            int siteAt1 = getSiteAt(i - 1, j);
            SiteInfo siteLeft = siteInfos[siteAt1];
            if (isInBounds(i - 1, j) && isOpen(i - 1, j)) {
                quickUnion.union(siteAt1, getSiteAt(i, j));
            }

            int siteAt2 = getSiteAt(i + 1, j);
            SiteInfo siteTop = siteInfos[siteAt1];
            if (isInBounds(i + 1, j) && isOpen(i + 1, j)) {

                quickUnion.union(siteAt2, getSiteAt(i, j));
            }

            if (isInBounds(i, j - 1) && isOpen(i, j - 1)) {
                quickUnion.union(getSiteAt(i, j - 1), getSiteAt(i, j));
            }

            if (isInBounds(i, j + 1) && isOpen(i, j + 1)) {
                quickUnion.union(getSiteAt(i, j + 1), getSiteAt(i, j));
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkSiteIndexes(i, j);
        int siteAt = getSiteAt(i, j);
        SiteInfo siteInfo = siteInfos[siteAt];
        if (siteInfo == null) {
            siteInfo = new SiteInfo(false, siteAt, false);
            siteInfos[siteAt] = siteInfo;
        }

        return siteInfo.isOpen();
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkSiteIndexes(i, j);
        if (!isOpen(i, j)) {
            return false;
        }

//        int old = quickUnion.find(sitesCount + 1);
//        quickUnion
        boolean connected = quickUnion.connected(getSiteAt(i, j), sitesCount);

        for (int it = 1; it <= n; it++) {
            quickUnion.union(sitesCount + 1, getSiteAt(n, it));
        }

        return connected;
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

    private static class SiteInfo {

        private boolean open;

        private int root;

        private final boolean connectedToTop;

        public SiteInfo(boolean open, int root, boolean connectedToTop) {
            this.open = open;
            this.root = root;
            this.connectedToTop = connectedToTop;
        }

        public boolean isOpen() {
            return open;
        }

        public int getRoot() {
            return root;
        }

        public boolean isConnectedToTop() {
            return connectedToTop;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public void setRoot(int root) {
            this.root = root;
        }
    }

}
