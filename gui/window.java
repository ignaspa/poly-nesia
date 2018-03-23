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
import javax.swing.BoxLayout;


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
    pan.addKeyListener(new KeyEventHandler(this));
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

  public void poly(){
    BufferedImage k = imageManipulator.enclosedImage(selection, pic);
    pan.setImage(k);

  }
  public void sobel(){
    int[][][] k = imageManipulator.getImageData(pic);
    double[][] k2 = imageManipulator.sobel(k);

    // Test the point generation
    List<Point> points = imageManipulator.distributePoints(k2, 4000);
    trianglePoints = points;
    
    for(int x = 0; x < pic.getWidth();x++){
      for(int y = 0; y < pic.getHeight(); y++){
        int intensity = (int)(k2[x][y]*255);
        Color color = new Color(intensity, intensity, intensity);
        pic.setRGB(x,y,color.getRGB());
      }
    }

    Color pointColor = Color.RED;
    Graphics g = pic.getGraphics();
    g.setColor(pointColor);
    for (Point p : points) {
      g.drawRect(p.x - 1, p.y - 1, 3, 3);
      //pic.setRGB((int) p.getX(), (int) p.getY(), pointColor.getRGB());
    }

    pan.repaint();
  }

  //handler classes fam
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
  private class KeyEventHandler extends KeyAdapter{
    window W;

    public KeyEventHandler(window wndw) {
      this.W = wndw;
    }
    @Override
    public void keyPressed(KeyEvent e){
      System.out.print(e.getKeyCode());
      switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER: //enter

        this.W.poly();
        break;

      case KeyEvent.VK_S: //s

        this.W.sobel();
        System.out.print("hello, its me");
        break;


      }
    }
  }
}
