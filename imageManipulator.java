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

public class imageManipulator
{


  public static void saveImage(int[][][] blurryboi, String path){
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

    return 1.0 / Math.sqrt(2 * Math.PI * sigma2) *
      Math.exp(-Math.pow(x, 2) / 2.0 / sigma2);
  }

  private static double[][] gaussianRadius(int radius){
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
  public static int[][][] sobel(int[][][] image){
    int[] y = {1, 2, 1,
               0, 0, 0,
              -1, -2, -1,};

    int[] x = {1, 0, -1,
               2, 0, -2,
               1, 0, -1};
    int[][][] sobs = new int[image.length][image[0].length][image[0][0].length];
    for(int k = 1; k < image.length - 1; k++){
      for(int i = 1; i < image[0].length - 1; i++){
        double[][] accumulator = {{0, 0, 0}, {0, 0, 0}};
        for(int w = 0; w < 3; w++){
          for(int v = 0; v < 3; v++){
            for(int z = 0; i < image[0][0].length; i++){

            }
          }
        }
      }
    }


  }

  public static int[][][] gaussianBlur(int[][][] image, int radius) {
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
    return new Color(color.getBlue(), color.getRed(), color.getGreen());
  }

}
