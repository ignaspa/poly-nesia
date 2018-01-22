package imagemanipulator;

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



}
