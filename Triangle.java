package imagemanipulator;
import java.awt.Point;
import delaunay_triangulation.Delaunay_Triangulation;
import delaunay_triangulation.Point_dt;

public class Triangle {
  public Point[] points = new Point[3];
  public Triangle(Point a, Point b, Point c) {
    points[0] = a;
    points[1] = b;
    points[2] = c;
  }
}
