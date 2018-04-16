package gui;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import imagemanipulator.imageManipulator;
import imagemanipulator.Triangle;
import javax.swing.BoxLayout;
import java.util.Arrays;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class window extends Frame {
  BufferedImage pic;
  ImagePanel pan;
  public ArrayList<Point> selection = new ArrayList();
  public List<Point> trianglePoints;
  public int radius = 0;


  public window(BufferedImage pic) {
    this.pic = pic;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    pan = new ImagePanel(pic, selection);
    this.add(pan);

    buttonPanel bp = new buttonPanel(this);
    this.add(bp);

    this.pack();
    this.setVisible(true);
    pan.addMouseListener(new MouseEventHandler(this));

    addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
                  // Terminate the program after the close button is clicked.
                  System.exit(0);
          }
        });
  }




  public void selectPoint(Point p) {
    Point q = new Point((int)p.getX() - pan.getMargin(), (int)p.getY() - pan.getMargin());
    selection.add(q);
    pan.repaint();
  }




  public void undoSelect() {
    if (selection.size() == 0)
      return;
    selection.remove(selection.size() - 1);
    pan.repaint();
  }




  public void enclosed(){
    BufferedImage k = imageManipulator.enclosedImage(selection, pic);
    pan.setImage(k);

  }

  public void blur(int radius){
    int[][][] img = imageManipulator.getImageData(pic);
    int[][][] blurred = imageManipulator.gaussianBlur(img, radius);
    BufferedImage k = new BufferedImage(blurred.length,blurred[0].length, BufferedImage.TYPE_INT_RGB);
    for(int i = 0; i < blurred.length; i++){
      for(int m = 0; m < blurred[0].length; m++){
        Color blurcolor = new Color(blurred[i][m][0], blurred[i][m][1], blurred[i][m][2]);
        int blur = blurcolor.getRGB();
        k.setRGB(i, m, blur);
      }
    }
    pan.setImage(k);

  }



  public void polygonize(){
    int[][][] imagedata = imageManipulator.getImageData(pic);
    double[][] sobeldata = imageManipulator.sobel(imagedata);

    // Test the point generation
    //List<Point> points = imageManipulator.distributePoints(k2, 4000);
    List<Point> points = imageManipulator.distributePoisson(sobeldata, 40);
    trianglePoints = points;
    List<Triangle> tris = imageManipulator.delaunay(points);

    pan.setImage(imageManipulator.triangulize(tris,imagedata));

    // for(int x = 0; x < pic.getWidth();x++){
    //   for(int y = 0; y < pic.getHeight(); y++){
    //     int intensity = (int)(sobeldata[x][y]*255);
    //     Color color = new Color(intensity, intensity, intensity);
    //     pic.setRGB(x,y,color.getRGB());
    //   }
    // }

    // Color pointColor = Color.RED;
    // Graphics g = pic.getGraphics();
    // g.setColor(pointColor);
    // for (Point p : points) {
    //   g.drawRect(p.x - 1, p.y - 1, 2, 2);
    //   //pic.setRGB((int) p.getX(), (int) p.getY(), pointColor.getRGB());
    // }

    // g.setColor(Color.GREEN);
    // for (Triangle t : tris) {
    //   int[] x = new int[] {t.points[0].x, t.points[1].x, t.points[2].x};
    //   int[] y = new int[] {t.points[0].y, t.points[1].y, t.points[2].y};
    //   g.drawPolygon(x, y, 3);
    // }

    pan.repaint();
  }





  //handler classes
  private class MouseEventHandler extends MouseAdapter {
    window W;

    public MouseEventHandler(window wndw) {
      this.W = wndw;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

      switch (e.getModifiers()) {
      case InputEvent.BUTTON1_MASK: // left
        this.W.selectPoint(e.getPoint());
        break;
      case InputEvent.BUTTON3_MASK: // right
        this.W.undoSelect();
        break;
      }

    }

  }

}
