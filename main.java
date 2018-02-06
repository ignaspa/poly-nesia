import gui.window;
import imagemanipulator.imageManipulator;
import java.util.Scanner;
import java.awt.image.BufferedImage;
public class main
{

public static void main(String[] args)
{
  /*canner scannyboi = new Scanner(System.in);
  System.out.println("Please submit path in / file to blur");
  String pathin = scannyboi.nextLine();
  System.out.println("Please submit path out / output file");
  String pathout = scannyboi.nextLine();
  System.out.println("Please submit radius / blur spread");
  int radius = scannyboi.nextInt();
  */
  String pathin = "/Users/ignaspan/Desktop/poly-nesia/example/flower.jpg";
  String pathout= "/Users/ignaspan/Desktop/poly-nesia/example/flower.png";
  int radius = 15;
  BufferedImage mg = imageManipulator.load(pathin);
  int[][][] img = imageManipulator.getImageData(mg);
  int[][][] blurred = imageManipulator.gaussianBlur(img, radius);
  imageManipulator.saveImage(blurred, pathout);
  window gui = new window(mg);
}

}
