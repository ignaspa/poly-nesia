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

  public int[] boundingbox(){
    // minx,maxx,miny,maxy
    return new int[] {
      minOfThree(points[0].x, points[1].x, points[2].x),
      maxOfThree(points[0].x, points[1].x, points[2].x),
      minOfThree(points[0].y, points[1].y, points[2].y),
      maxOfThree(points[0].y, points[1].y, points[2].y)
    };
  }

  private int minOfThree(int a, int b, int c) {
    if (a < b && a < c) return a;
    if (b <= a && b < c) return b;
    return c;
  }

  private int maxOfThree(int a, int b, int c) {
    if (a > b && a > c) return a;
    if (b >= a && b > c) return b;
    return c;
  }
}
