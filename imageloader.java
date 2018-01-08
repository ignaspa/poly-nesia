import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
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

    }



}
