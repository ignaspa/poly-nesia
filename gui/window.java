package gui;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;

import java.awt.Point;
import java.util.ArrayList;

public class window extends Frame {

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

  public void undoSelect() {
    if (selection.size() == 0)
      return;
    selection.remove(selection.size() - 1);
    pan.repaint();
  }

  private class MouseEventHandler extends MouseAdapter {
    window W;

    public MouseEventHandler(window wndw) {
      this.W = wndw;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

      switch (e.getModifiers()) {
      case InputEvent.BUTTON1_MASK: // left
        System.out.println("We got clicked!");
        this.W.selectPoint(e.getPoint());
        break;
      case InputEvent.BUTTON3_MASK: // right
        this.W.undoSelect();
        break;
      }

    }
  }

}
