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
import javax.swing.BoxLayout;
import java.awt.event.*;

public class buttonPanel extends Panel {

  public buttonPanel(window w) {
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    Button incBlur = new Button("+ BLUR");
    Button decBlur = new Button("- BLUR");
    Button sel = new Button("Select Area");
    Button poly = new Button("Polygonize");

    add(incBlur);
    add(decBlur);
    add(poly);
    add(sel);
    incBlur.addActionListener(new ActionHandler() {
      public void actionPerformed(ActionEvent event) {
        w.radius += 1;
        w.blur(w.radius);
        //System.out.print(w.radius);
      }
    });
    decBlur.addActionListener(new ActionHandler() {
      public void actionPerformed(ActionEvent event) {
        if (w.radius > 0) {
          w.radius -= 1;
          w.blur(w.radius);
        }
        //System.out.print(w.radius);
      }
    });
    poly.addActionListener(new ActionHandler() {
      public void actionPerformed(ActionEvent event) {
        w.polygonize();

      }
    });

    sel.addActionListener(new ActionHandler() {
      public void actionPerformed(ActionEvent event) {
        w.enclosed();
      }
    });

  }
}
