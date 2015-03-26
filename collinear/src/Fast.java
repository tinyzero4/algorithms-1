import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A faster, sorting-based solution. Remarkably, it is possible to solve the problem much faster than the brute-force solution described above. Given a point p, the following method determines whether p participates in a set of 4 or more collinear points.
 * <p/>
 * Think of p as the origin.
 * For each other point q, determine the slope it makes with p.
 * Sort the points according to the slopes they makes with p.
 * Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.
 * Applying this method for each of the N points in turn yields an efficient algorithm to the problem. The algorithm solves the problem because points that have equal slopes with respect to p are collinear, and sorting brings such points together. The algorithm is fast because the bottleneck operation is sorting.
 *
 * @author zenind
 */
public class Fast {

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        List<Point> points = new ArrayList<Point>();
        while (n-- > 0) {
            int x = in.readInt();
            int y = in.readInt();
            points.add(new Point(x, y));
        }

        Collections.sort(points);

        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        Set<PFK> processedSlopes = new HashSet<PFK>();
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            p.draw();
            FK[] slopes = new FK[points.size()];
            for (int j = 0; j < points.size(); j++) {
                slopes[j] = new FK(points.get(j), p.slopeTo(points.get(j)));
            }

            Merge.sort(slopes);
            int count = 1;

            Set<Point> pp = new HashSet<Point>();
            for (int j = 0; j < slopes.length - 1; j++) {
                double slopej = slopes[j].getSlope();
                double slopej1 = slopes[j + 1].getSlope();
                if (slopej < slopej1) {
                    PFK pfk = new PFK(pp);
                    if (count >= 3 && !processedSlopes.contains(pfk)) {
                        for (int k = j - count + 1; k <= j; k++) {
                            if (k == j - count + 1) {
                                StdOut.print(p);
                            }
                            StdOut.print(" -> ");
                            StdOut.print(slopes[k].getP());
                        }
                        p.drawTo(slopes[j].getP());
                        processedSlopes.add(pfk);
                        StdOut.println();
                    }
                    count = 1;
                } else {
                    pp.add(p);
                    count++;
                }

            }
        }

        StdDraw.show(0);
    }

    private final static class PFK {

        private final Set<Point> points;

        public PFK(Set<Point> points) {
            this.points = new TreeSet<Point>(points);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            PFK pfk = (PFK) o;

            if (points != null ? !points.equals(pfk.points) : pfk.points != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return points != null ? points.hashCode() : 0;
        }
    }

    private final static class FK implements Comparable<FK> {
        private final Point p;
        private final double slope;

        public FK(Point p, double slope) {
            this.p = p;
            this.slope = slope;
        }

        public Point getP() {
            return p;
        }

        public double getSlope() {
            return slope;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            FK fk = (FK) o;

            if (Double.compare(fk.slope, slope) != 0) {
                return false;
            }
            if (p != null ? !p.equals(fk.p) : fk.p != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = p != null ? p.hashCode() : 0;
            temp = Double.doubleToLongBits(slope);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public int compareTo(FK o) {
            if (getSlope() < o.getSlope()) {
                return -1;
            } else if (getSlope() > o.getSlope()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
