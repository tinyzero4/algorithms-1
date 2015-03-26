/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {

            //The SLOPE_ORDER comparator should compare points by the slopes they make with the invoking point (x0, y0).
            // Formally, the point (x1, y1) is less than the point (x2, y2) if and only
            // if the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0).
            // Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.
            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);

            if (slope1 > slope2) {
                return 1;
            } else if (slope1 < slope2) {
                return -1;
            }

            return 0;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that.y == y && that.x != x) return +0;
        if (that.x == x && that.y != y) return Double.POSITIVE_INFINITY;
        if (that.y == y) return Double.NEGATIVE_INFINITY;
        //(y1 − y0) / (x1 − x0)
        return (that.y - y) / (double) (that.x - x);
//        return new BigDecimal(that.y).subtract(new BigDecimal(y)).divide(new BigDecimal(that.x).subtract(new BigDecimal(x)), 4, RoundingMode.HALF_UP).doubleValue();
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if ((y < that.y) || (y == that.y && x < that.x)) {
            return -1;
        } else if ((y > that.y || (that.y == y && x > that.x))) {
            return 1;
        } else {
            return 0;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}