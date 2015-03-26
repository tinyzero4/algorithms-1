/**
 * @author zenind
 */
public class PercolationStats {

    private final double[] percentage;

    private final int t;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("n and to should be greater than 0");
        }

        this.t = t;
        this.percentage = new double[t];
        for (int ti = 0; ti < t; ti++) {
            int openSites = 0;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    openSites++;
                }
            }

            percentage[ti] = ((double) openSites) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percentage);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percentage);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(this.t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(this.t);
    }

    /**
     * Launcher.
     */
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        if (args.length != 2) {
            throw new IllegalArgumentException("N and T should be provided");
        }

        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.printf("mean\t = %s\n", stats.mean());
        StdOut.printf("stddev\t = %s\n", stats.stddev());
        StdOut.printf("95 percent confidence interval\t = %s,  %s\n", stats.confidenceLo(), stats.confidenceHi());

        System.out.println(stopwatch.elapsedTime());
    }
}
