import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class imageloader

{
    public static void main(String[] args)
    {
      BufferedImage img = null;


      try
      {
        img = ImageIO.read(new File("notlimegReEn.jpg"));
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
        System.out.println(pix[0][0][0]);
    }



}
