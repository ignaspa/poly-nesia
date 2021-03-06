package imagemanipulator;
import java.awt.Point;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import delaunay_triangulation.Delaunay_Triangulation;
import delaunay_triangulation.Point_dt;
import delaunay_triangulation.Triangle_dt;
import java.util.Iterator;
import java.util.Arrays;


public class imageManipulator
{

 // This class is the class we use to do the primary modifications on the
 // input image.



  public static void saveImage(int[][][] blurryboi, String path){
    //This method saves the image once we are done editing it.
    BufferedImage image = new BufferedImage(blurryboi.length, blurryboi[0].length, BufferedImage.TYPE_INT_RGB);
    for(int k = 0; k < blurryboi.length; k++){
      for(int n = 0; n < blurryboi[0].length; n++){
        Color color = new Color(blurryboi[k][n][0], blurryboi[k][n][1], blurryboi[k][n][2]);
        int colnum = color.getRGB();
        image.setRGB(k, n, colnum);
      }
    }
    try
    {
      ImageIO.write(image, "png", new File(path));
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }




  public static BufferedImage load(String path){
    //this method takes the input image file name and creates the
    //element for us to manipulate.
    BufferedImage img = null;

    try
    {
      img = ImageIO.read(new File(path));
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    return img;
  }





  public static int[][][] getImageData(BufferedImage img) {
    //This method takes the BufferedImage we got from the source, and
    // proceeds to make a 3 dimensional array for it so that
    // we have access to the RGB color data for each pixel.
    int wid = img.getWidth();
    int hite = img.getHeight();

    int[][][] pix = new int[wid][hite][3];

    for(int k = 0; k < wid; k++){
      for(int n = 0; n < hite; n++){
        Color col = new Color(img.getRGB(k,n));
        pix[k][n][0] = col.getRed();
        pix[k][n][1] = col.getGreen();
        pix[k][n][2] = col.getBlue();
      }
    }

    return pix;
  }




  public static BufferedImage enclosedImage(List<Point> outline, BufferedImage pic){
    // this method makes a new BufferedImage based off of the pixels
    // in list of pixels within the enclosed area chosen by the user.
    BufferedImage enclosed = new BufferedImage(pic.getWidth(), pic.getHeight(), BufferedImage.TYPE_INT_RGB);
    for(int i = 0; i < pic.getWidth(); i++){
      for(int k = 0; k < pic.getHeight(); k++){
        Point z = new Point(i,k);
        if (imageManipulator.pointInOutline(z, outline)){
          enclosed.setRGB(i,k, pic.getRGB(i,k));
        }
      }
    }
    return enclosed;
  }




  private static boolean pointInOutline(Point j, List<Point> outline){
    // this method finds if a point is within the outline
    // by checking how many times a line from it intersects
    // with rays that make up the outline, and it uses rayLineIntersect to do so.
    int countsect = 0;
    for(int i = 0; i < outline.size(); i++){
      Point r = outline.get(i);
      Point t = outline.get((i+1) % outline.size());
      if (imageManipulator.rayLineIntersect(j.getX(), j.getY(), r.getX(), r.getY(), t.getX(), t.getY())){
        countsect++;
      }
    }
    return (countsect % 2 != 0);
  }




  private static boolean rayLineIntersect(double ox, double oy, double x1, double y1,
                                          double x2, double y2){
    //this method checks if lines from a point intersect with other lines,
    //aka rays, which in this case are the lines that make up the outline.
    oy += .5;
    double dx = 1;
    double dy = 0;
    double lx = x2 - x1; //x difference between points of edge
    double ly = y2 - y1; //y difference between points of edge
    double cross = (dx * ly);
    if (cross == 0){ //for line is parallel
      if (oy == y1){
        if ((x1 >= ox) ^ (x2 >= ox)){
          return true;
        }
      }
      return false;
    }
    double u = (oy - y1) / cross;
    double t = (((x1 - ox) * ly) - ((y1 - oy) * lx)) / cross;

    return (u > 0 && u <= 1) && t >= 0;

  }




  private static double gauss(double x, double sigma2) {
    //this method creates a double that is a value for a given
    //gaussian distribution, using the formula.
    return 1.0 / Math.sqrt(2 * Math.PI * sigma2) *
      Math.exp(-Math.pow(x, 2) / 2.0 / sigma2);
  }





  private static double[][] gaussianRadius(int radius){
    //this method applies the gauss method to
    //weight values around a pixel to simulate a gaussian curve for
    //use in blurring.
    int mid = radius + 1;
    double[][] val = new double[2*radius + 1][2*radius + 1];
    double sigma2 = Math.pow(radius / 3.0, 2);
    for(int k = 0; k < 2 * radius + 1;k++){
      for(int n = 0; n < 2 * radius + 1;n++){
        double dist = Math.sqrt(Math.pow(k-mid,2) + Math.pow(n-mid,2));
        val[k][n] = gauss(dist, sigma2);
        if (dist > radius){
          val[k][n] = 0;
        }
      }
    }
    return val;
  }




  public static double[][] sobel(int[][][] image){
    // this will return a all the pixels with a given "intensity"
    // based on how much the color is changing on that pixel,
    // both in the x and y direction. The method uses kernels to
    // basically map vertical and horizontal changes.
    // https://en.wikipedia.org/wiki/Sobel_operator

    int[] y = {1, 2, 1,
               0, 0, 0,
              -1, -2, -1,};

    int[] x = {1, 0, -1,
               2, 0, -2,
               1, 0, -1};

    double[][] sobs = new double[image.length][image[0].length];

    for(int k = 1; k < image.length - 1; k++){
      for(int i = 1; i < image[0].length - 1; i++){
        // this is how much the each color changes in the x and y
        // direction. Basically - x:RGB and y:RGB
        double[][] accumulator = {{0, 0, 0}, {0, 0, 0}};

        for(int w = 0; w < 3; w++){
          for(int v = 0; v < 3; v++){
            int[] color = image[k + w - 1][i + v - 1];
            int sobind = v*3 + w;
            for(int z = 0; z < image[0][0].length; z++){
              accumulator[0][z] += color[z] * x[sobind];

              accumulator[1][z] += color[z] * y[sobind];
            }
          }
        }

        double magnitude = 0;
        // taking the sum of the squares for all six and getting an avg.
        // maginitude of a six dimensional vector.
        for(int u = 0; u < accumulator.length; u++){
          for(int h = 0; h < accumulator[0].length; h++){
            magnitude += Math.pow(accumulator[u][h], 2);
          }
        }
        magnitude = Math.sqrt(magnitude);
        sobs[k][i] = magnitude;
      }
    }

    // Normalize!
    double maxDensity = 0;
    double minDensity = 1000;
    for (int i = 0; i < sobs.length; i++) {
      for (int j = 0; j < sobs[0].length; j++) {
        if (sobs[i][j] > maxDensity) {
          maxDensity = sobs[i][j];
        }
        if (sobs[i][j] < minDensity) {
          minDensity = sobs[i][j];
        }
      }
    }
    // adjusting all points to be within 0 and 1 being those min/max densities
    for (int i = 0; i < sobs.length; i++) {
      for (int j = 0; j < sobs[0].length; j++) {
        sobs[i][j] = (sobs[i][j] - minDensity) / (maxDensity - minDensity);
      }
    }

    return sobs;
  }

  public static List<Point> distributePoisson(double[][] density, double radius) {
    // this method will basically distribute a bunch of points randomly,
    // so as to figure out the vertices for the triangles to make the poly art
    Random rand = new Random();
    ArrayList<Point> points = new ArrayList();

    int cuttoff = 1000;

    for (int rejectedCount = 0; rejectedCount < cuttoff;) {
      Point sample = new Point(rand.nextInt(density.length), rand.nextInt(density[0].length));
      double sampleDensity = density[sample.x][sample.y];

      boolean reject = false;
      for (Point p : points) {
        double averageDensity = (sampleDensity + density[p.x][p.y]) / 2;
        double minDist = (0.05 + 0.95 * Math.pow(1-averageDensity,5)) * radius;
        double minDist2 = minDist*minDist;

        double dist2 = (sample.x - p.x) * (sample.x - p.x) + (sample.y - p.y) * (sample.y - p.y);
        if (dist2 < minDist2) {
          reject = true;
          break;
        }
      }
      //if (sampleDensity<0.5) {
      //  reject = true;
      //}
      if (reject) {
        rejectedCount++;
        continue;
      }
      points.add(sample);
      rejectedCount = 0;
    }

    return points;
  }




  public static int[][][] gaussianBlur(int[][][] image, int radius) {
    // this will use the gaussian radius method to weight the colors around
    // each pixel and then create the average color for each pixel, and thus
    // basically imitate a blur across the image.
    int[][][] blurredImage = new int[image.length][image[0].length][3];
    double[][] weights = gaussianRadius(radius);
    for(int k = 0; k < image.length; k++) {
      for (int n = 0; n < image[0].length; n++) {
        double r = 0;
        double g = 0;
        double b = 0;
        double weightSum = 0;
        for (int i = 0; i < radius * 2 + 1; i++) {
          for (int j = 0; j < radius * 2 + 1; j++) {
            int col = k + i - radius;
            int row = n + j - radius;
            if (0 <= col && col < image.length) {
              if (0 <= row && row < image[0].length) {
                r += image[col][row][0] * weights[i][j];
                g += image[col][row][1] * weights[i][j];
                b += image[col][row][2] * weights[i][j];
                weightSum += weights[i][j];
              }
            }
          }
        }
        r /= weightSum;
        g /= weightSum;
        b /= weightSum;
        blurredImage[k][n][0] = (int) r;
        blurredImage[k][n][1] = (int) g;
        blurredImage[k][n][2] = (int) b;
      }
    }
    return blurredImage;
  }

  public static Color averageColor(BufferedImage image) {
    // finds the average color around the pixel.
    int r = 0;
    int g = 0;
    int b = 0;

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Color pixel = new Color(image.getRGB(x, y));
        r += pixel.getRed();
        g += pixel.getGreen();
        b += pixel.getBlue();
      }
    }

    int pixelCount = image.getWidth() * image.getHeight();
    r /= pixelCount;
    g /= pixelCount;
    b /= pixelCount;

    return new Color(r, g, b);
  }

  public static Color oppositeColor(Color color) {
    //inverses the color measurements, thus "flipping the color"
    return new Color(color.getBlue(), color.getRed(), color.getGreen());
  }

  public static List<Triangle> delaunay(List<Point> points){
    // takes the poing as previously determined for the vertices
    // then makes the the Delaunay Grid using an API we found
    // https://www.cs.bgu.ac.il/~benmoshe/DT/
    Point_dt[] newlist = new Point_dt[points.size()];
    for(int i = 0; i < points.size(); i++) {
      Point p = points.get(i);
      newlist[i] = new Point_dt(p.x, p.y);
    }
    Delaunay_Triangulation dt = new Delaunay_Triangulation(newlist);

    ArrayList<Triangle> tl = new ArrayList(dt.trianglesSize());
    Iterator<Triangle_dt> it = dt.trianglesIterator();
    while (it.hasNext()) {
      Triangle_dt t = it.next();
      if (t.p1() != null && t.p2() != null && t.p3() != null){
        Point p1 = new Point((int)t.p1().x(), (int)t.p1().y());
        Point p2 = new Point((int)t.p2().x(), (int)t.p2().y());
        Point p3 = new Point((int)t.p3().x(), (int)t.p3().y());

        tl.add(new Triangle(p1,p2,p3));
      }
    }
    return tl;
  }


  public static BufferedImage triangulize(List<Triangle> tris, int[][][] imagedata){
    BufferedImage out = new BufferedImage(imagedata.length,imagedata[0].length, BufferedImage.TYPE_INT_RGB);
    Graphics gr = out.getGraphics();

    for(Triangle t : tris){
      int r = 0;
      int g = 0;
      int b = 0;
      int counter = 1;
      int[] bounds = t.boundingbox();
      for(int x = bounds[0]; x <= bounds[1]; x++){
        for(int y = bounds[2]; y <= bounds[3]; y++){
          if (pointInOutline(new Point(x,y), Arrays.asList(t.points))){
            r += imagedata[x][y][0];
            g += imagedata[x][y][1];
            b += imagedata[x][y][2];
            counter++;
          }
        }
      }

      Color avgcolor = new Color(r/counter, g/counter,b/counter );
      gr.setColor(avgcolor);
      int[] x = new int[] {t.points[0].x, t.points[1].x, t.points[2].x};
      int[] y = new int[] {t.points[0].y, t.points[1].y, t.points[2].y};
      gr.fillPolygon(x, y, 3);
    }
    return out;
  }
}
