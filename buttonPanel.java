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

public class buttonPanel extends Panel {

  public buttonPanel() {
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    Button incBlur = new Button("+ BLUR");
    Button decBlur = new Button("- BLUR");
    add(incBlur);
    add(decBlur);



  }
}
