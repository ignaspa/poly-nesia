import gui.window;
import imagemanipulator.imageManipulator;
import java.util.Scanner;
import java.awt.image.BufferedImage;

public class main
{
  //write sh scripts/compile.sh in the terminal to compile this project
  //write sh scripts/run.sh in the terminal to run this project

public static void main(String[] args)
{
  /*Scanner scannyboi = new Scanner(System.in);
  System.out.println("Please submit path in / file to blur");
  String pathin = scannyboi.nextLine();
  System.out.println("Please submit path out / output file");
  String pathout = scannyboi.nextLine();
  System.out.println("Please submit radius / blur spread");
  int radius = scannyboi.nextInt();
  */
  String pathin = "example/mountain.jpg";
  //String pathout= "example/flower.png";
  //int radius = 15;
  BufferedImage mg = imageManipulator.load(pathin);
  //int[][][] img = imageManipulator.getImageData(mg);
  //int[][][] blurred = imageManipulator.gaussianBlur(img, radius);
  //imageManipulator.saveImage(blurred, pathout);
  window gui = new window(mg);
  frame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
                // Terminate the program after the close button is clicked.
                System.exit(0);
        }
      });


}

}
