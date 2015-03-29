import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author zenind
 */
public class KdTree {

    /**
     * Compares two points by x-coordinate.
     */
    private static final Comparator<Point2D> X_ORDER = new XOrder();

    /**
     * Compares two points by y-coordinate.
     */
    private static final Comparator<Point2D> Y_ORDER = new YOrder();

    private Node<Double, Point2D> root;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        if (!contains(p)) {
            root = doPut(root, p, true, new RectHV(0, 0, 1, 1));
        }
    }

    private Node<Double, Point2D> doPut(Node<Double, Point2D> x, Point2D p, boolean xLevel, RectHV area) {
        if (x == null) {
            return new Node<Double, Point2D>(xLevel ? p.x() : p.y(), p, null, null, xLevel, 1, area);
        }

        Comparator<Point2D> comparator = x.isxLevel() ? X_ORDER : Y_ORDER;
        int compare = comparator.compare(p, x.getValue());
        double splitValue = x.isxLevel() ? x.getValue().x() : x.getValue().y();
        if (compare <= 0 ) {
            RectHV leftArea = x.isxLevel() ?
                new RectHV(area.xmin(), area.ymin(), splitValue, area.ymax()) :
                new RectHV(area.xmin(), area.ymin(), area.xmax(), splitValue);
            x.left = doPut(x.left, p, !xLevel, leftArea);
        } else if (compare > 0) {
            RectHV rightArea = x.isxLevel() ?
                new RectHV(splitValue, area.ymin(), area.xmax(), area.ymax()) :
                new RectHV(area.xmin(), splitValue, area.xmax(), area.ymax());
            x.right = doPut(x.right, p, !xLevel, rightArea);
        }

        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    private Node<Double, Point2D> get(Node<Double, Point2D> x, Point2D p) {
        if (x == null) {
            return null;
        }

        if (x.value.equals(p)) {
            return x;
        }

        Comparator<Point2D> comparator = x.isxLevel() ? X_ORDER : Y_ORDER;
        int compare = comparator.compare(x.getValue(), p);
        if (compare >= 0) {
            return get(x.left, p);
        } else {
            return get(x.right, p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return get(root, p) != null;
    }

    // draw all points to standard draw
    public void draw() {
        doDraw(root);
    }

    private void doDraw(Node<Double, Point2D> x) {
        if (x != null) {
            x.value.draw();
            if (x.left != null) {
                doDraw(x.left);
            }
            if (x.right != null) {
                doDraw(x.right);
            }
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }

        List<Point2D> result = new ArrayList<Point2D>();
        doRange(root, rect, result);
        return result;
    }

    private void doRange(Node<Double, Point2D> x, RectHV rectHV, List<Point2D> points) {
        if (x == null) {
            return;
        }

        if (rectHV.contains(x.getValue())) {
            points.add(x.getValue());
        }


        if (x.isxLevel()) {
            if (rectHV.xmin() <= x.getValue().x()) {
                doRange(x.left, rectHV, points);
            }

            if (rectHV.xmax() > x.getValue().x()) {
                doRange(x.right, rectHV, points);
            }
        } else {
            if (rectHV.ymin() <= x.getValue().y()) {
                doRange(x.left, rectHV, points);
            }
            if (rectHV.ymax() > x.getValue().y()) {
                doRange(x.right, rectHV, points);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            return null;
        }
        return doNearest(p, root, root).getValue();
    }

    private Node<Double, Point2D> doNearest(Point2D p, Node<Double, Point2D> x, Node<Double, Point2D> nearest) {
        if (x == null) {
            return nearest;
        }
        double distance = p.distanceTo(nearest.getValue());
        double dist = p.distanceTo(x.getValue());
        if (dist < distance) {
            nearest = x;
            distance = dist;
        }

        double splitValue = x.isxLevel() ? x.getValue().x() : x.getValue().y();
        double value = x.isxLevel() ? p.x() : p.y();

        if (value < splitValue) {
            nearest = doNearest(p, x.left, nearest);
            if (x.right != null && x.right.getArea().distanceTo(p) < distance) {
                nearest = doNearest(p, x.right, nearest);
            }
        } else {
            nearest = doNearest(p, x.right, nearest);
            if (x.left != null && x.left.getArea().distanceTo(p) < distance) {
                nearest = doNearest(p, x.left, nearest);
            }
        }
        return nearest;
    }

    private static class Node<Key extends Comparable<Key>, Value> {
        private Key key;
        private Value value;
        private Node<Key, Value> left, right;
        private int N;
        private final boolean xLevel;
        private RectHV area;

        public Node(Key key, Value value, Node<Key, Value> left, Node<Key, Value> right, boolean xLevel, int n, RectHV area) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.xLevel = xLevel;
            this.N = n;
            this.area = area;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public Node<Key, Value> getLeft() {
            return left;
        }

        public Node<Key, Value> getRight() {
            return right;
        }

        public boolean isxLevel() {
            return xLevel;
        }

        public int getN() {
            return N;
        }

        public RectHV getArea() {
            return area;
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

//        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();

        for (int i = 0; i < 100; i++) {
            double x =StdRandom.uniform(0.0, 1.0);
            double y =StdRandom.uniform(0.0, 1.0);
            Point2D p = new Point2D(x, y);
            System.out.println(p);
            kdtree.insert(p);
            kdtree.insert(p);
            kdtree.insert(p);
            System.out.println(kdtree.contains(p));
        }

        kdtree.insert(new Point2D(0, 1));
        System.out.println(kdtree.size());
        System.out.println(kdtree.contains(new Point2D(0, 1)));

        kdtree.insert(new Point2D(0, 0.5));
        System.out.println(kdtree.size());
        System.out.println(kdtree.contains(new Point2D(0, 0.5)));

        kdtree.insert(new Point2D(0, 0.3));
        System.out.println(kdtree.size());
        kdtree.insert(new Point2D(0, 0.3));
        kdtree.insert(new Point2D(0, 0.3));
        kdtree.insert(new Point2D(0, 0.3));
        System.out.println(kdtree.size());
        System.out.println(kdtree.contains(new Point2D(0, 0.3)));


//        while (!in.isEmpty()) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            Point2D p = new Point2D(x, y);
//            kdtree.insert(p);
//            brute.insert(p);
//
//        }
//
//        Point2D p = new Point2D(0.2832, 0.5625);
//        Point2D nearestBrute = brute.nearest(p);
//        System.out.println(nearestBrute + " : " + p.distanceTo(nearestBrute));
//        Point2D nearestKD = kdtree.nearest(p);
//        System.out.println(nearestKD + " : " + p.distanceTo(nearestKD));

    }

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.x() < q.x()) return -1;
            if (p.x() > q.x()) return +1;
            return 0;
        }
    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.y() < q.y()) return -1;
            if (p.y() > q.y()) return +1;
            return 0;
        }
    }

}
