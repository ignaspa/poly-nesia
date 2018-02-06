package gui;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;

public class window extends Frame
{

  public window(BufferedImage pic) {
    ImagePanel pan = new ImagePanel(pic);
    this.add(pan);
    this.pack();
    this.setVisible(true);
    pan.addMouseListener(new MouseEventHandler());
  }



  private class MouseEventHandler extends MouseAdapter
  {
    window W;
    public void MouseEventHandler(window wndw){
      this.W = wndw;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
      System.out.println("We got clicked!");
      //this.W.BlurredImage();
    }
  }


}
