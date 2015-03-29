import java.util.ArrayList;
import java.util.List;

/**
 * @author zenind
 */
public class PointSET {

    private final SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        this.points = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();

        List<Point2D> point2Ds = new ArrayList<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                point2Ds.add(p);
            }
        }

        return point2Ds;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        Point2D nearest = null;
        double minDistance = Integer.MAX_VALUE;
        for (Point2D c : points) {
            double distanceTo = c.distanceTo(p);
            if (distanceTo < minDistance) {
                minDistance = distanceTo;
                nearest = c;
            }
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}