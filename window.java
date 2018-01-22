package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class window extends Frame
{

public window(BufferedImage pic){
  ImagePanel pan = new ImagePanel(pic);
  this.add(pan);
  this.pack();
  this.setVisible(true);

}






}
