package gui;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

import java.util.ArrayList;
import java.awt.Point;

import imagemanipulator.imageManipulator;

public class ImagePanel extends Panel {

  BufferedImage photo;
  int margin;
  ArrayList<Point> selection;
  Color selectionColor;

  public ImagePanel(BufferedImage picture, ArrayList<Point> selection) {
    this.selection = selection;
    this.photo = picture;
    this.margin = 20;
    this.setPreferredSize(new Dimension(picture.getWidth() + 2 *
                          this.margin, picture.getHeight() + 2 *
                          this.margin));
  }

  public void paint(Graphics g) {
    g.drawImage(this.photo, this.margin, this.margin, null);

    if (selection.size() == 1) {
      int pointRadius = 5;
      Point p = selection.get(0);
      g.setColor(Color.BLACK);
      g.fillOval((int) p.getX() - pointRadius, (int) p.getY() - pointRadius, pointRadius * 2, pointRadius * 2);
    } else {
      int[] xPoints = new int[selection.size()];
      int[] yPoints = new int[selection.size()];
      for (int i = 0; i < selection.size(); i++) {
        xPoints[i] = (int) selection.get(i).getX() + this.margin;
        yPoints[i] = (int) selection.get(i).getY() + this.margin;
      }
      g.setColor(Color.BLACK);
      g.drawPolygon(xPoints, yPoints, selection.size());

    }

  }
  public void setImage(BufferedImage newOne){
    photo = newOne;
    repaint();
  }
  public int getMargin(){
    return this.margin;
  }
}
