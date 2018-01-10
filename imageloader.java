import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Color;
import org.apache.commons.math3.analysis.function.Gaussian;

public class imageloader
{
  public static void main(String[] args)
  {
    int[][][] img = imageloader.load("notlimegReEn.jpg");
    int[][][] blurred = imageloader.gaussianBlur(img, 3);
  }

  public static int[][][] load(String path) {
    BufferedImage img = null;

    try
    {
      img = ImageIO.read(new File(path));
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }

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

  private static double gauss(double x) {
    sigma2 = 1.0;
    return 1.0 / Math.sqrt(2 * Math.PI * sigma2) * 
      Math.exp(-Math.pow(x, 2) / 2.0 / sigma2);
  }

  private static double[][] gaussianRadius(int radius){
    int mid = radius + 1;
    double[][] val = new double[2*radius + 1][2*radius + 1];
    for(int k = 0; k < 2 * radius + 1;k++){
      for(int n = 0; n < 2 * radius + 1;n++){
        double dist = Math.sqrt(Math.pow(k-mid,2) + Math.pow(n-mid,2));
        val[k][n] = gauss(dist);
      }
    
    }


  }

  private static int[][][] gaussianBlur(int[][][] image, int radius) {
    for(int k = 0)
    return null;
  }

}
