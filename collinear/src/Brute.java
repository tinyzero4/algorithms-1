import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zenind
 */
public class Brute {

    /**
     * Brute force.
     * Write a program Brute.java that examines 4 points at a time and checks whether they all lie on the same line segment,
     * printing out any such line segments to standard output and drawing them using standard drawing.
     * To check whether the 4 points p, q, r, and s are collinear,
     * check whether the slopes between p and q, between p and r, and between p and s are all equal.
     *
     * @param args
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        List<Point> points = new ArrayList<Point>();

        while (n-- > 0) {
            int x = in.readInt();
            int y = in.readInt();
            points.add(new Point(x, y));
        }

        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        Collections.sort(points);

        boolean[] drawn = new boolean[points.size()];
        for (int p0 = 0; p0 < points.size(); p0++) {
            for (int p1 = p0 + 1; p1 < points.size(); p1++) {
                for (int p2 = p1 + 1; p2 < points.size(); p2++) {
                    for (int p3 = p2 + 1; p3 < points.size(); p3++) {
                        double slopeIJ = points.get(p0).slopeTo(points.get(p1));
                        double slopeIK = points.get(p0).slopeTo(points.get(p2));
                        double slopeIL = points.get(p0).slopeTo(points.get(p3));
                        // if they all are equal then it is line segment
                        if ((slopeIJ == slopeIK) && (slopeIJ == slopeIL)) {
                            System.out.println(points.get(p0) + " " + "->" + " " +
                                points.get(p1) + " " + "->" + " " +
                                points.get(p2) + " " + "->" + " " +
                                points.get(p3));
                            // draw this line segment
                            points.get(p0).drawTo(points.get(p3));
                        }

                        if (!drawn[p0]) {
                            points.get(p0).draw();
                            drawn[p0] = true;
                        }
                        if (!drawn[p1]) {
                            points.get(p1).draw();
                            drawn[p1] = true;
                        }
                        if (!drawn[p2]) {
                            points.get(p2).draw();
                            drawn[p2] = true;
                        }
                        if (!drawn[p3]) {
                            points.get(p3).draw();
                            drawn[p3] = true;
                        }
                    }
                }
            }
        }

        StdDraw.show(0);
    }
}
