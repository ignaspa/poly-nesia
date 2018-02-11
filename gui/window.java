package gui;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;

import java.awt.Point;
import java.util.ArrayList;

public class window extends Frame
{

  ImagePanel pan;
  public ArrayList<Point> selection = new ArrayList();

  public window(BufferedImage pic) {
    pan = new ImagePanel(pic, selection);
    this.add(pan);
    this.pack();
    this.setVisible(true);
    pan.addMouseListener(new MouseEventHandler(this));
  }

  public void selectPoint(Point p) {
    selection.add(p);
    pan.repaint();
  }

  private class MouseEventHandler extends MouseAdapter
  {
    window W;
    public MouseEventHandler(window wndw){
      this.W = wndw;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
      
      System.out.println("We got clicked!");
      this.W.selectPoint(e.getPoint());
      //this.W.BlurredImage();
    }
  }


}
