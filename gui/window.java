package gui;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import imagemanipulator.imageManipulator;

public class window extends Frame {
  BufferedImage pic;
  ImagePanel pan;
  public ArrayList<Point> selection = new ArrayList();

  public window(BufferedImage pic) {
    this.pic = pic;
    pan = new ImagePanel(pic, selection);
    this.add(pan);
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
      switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER: //enter

        this.W.poly();
        break;

      }



    }



  }
}
