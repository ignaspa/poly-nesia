package gui;

import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImagePanel extends Panel{

BufferedImage photo;
int margin;

public ImagePanel(BufferedImage picture){
this.photo = picture;
this.margin = 20;
this.setSize(picture.getWidth() + 2*this.margin, picture.getHeight() + 2*this.margin);


}
public void paint(Graphics g){
  g.drawImage(this.photo, this.margin, this.margin, null);
}



}
