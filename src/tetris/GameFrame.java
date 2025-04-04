package tetris;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameFrame extends JFrame {
  private GameArea ga;

  public GameFrame() {
    ga = new GameArea(10);
    initComponents();
    initControls();
    add(ga);
    startGame();
  }

  private void initControls() {
    InputMap im = this.getRootPane().getInputMap();
    ActionMap am = this.getRootPane().getActionMap();

    im.put(KeyStroke.getKeyStroke("LEFT"), "left");
    im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
    im.put(KeyStroke.getKeyStroke("UP"), "up");
    im.put(KeyStroke.getKeyStroke("DOWN"), "down");

    am.put("left", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ga.moveBlockLeft();
      }
    });
    am.put("right", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ga.moveBlockRight();
      }
    });
    am.put("up", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ga.rotateBlock();
      }
    });
    am.put("down", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ga.dropBlockDown();
      }
    });
  }

  public void startGame() {
    new GameThread(ga).start();
  }

  public void initComponents() {
    setTitle("Tetris");
    getContentPane().setLayout(null);
    setSize(540, 512);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        new GameFrame().setVisible(true);
      }
    });
  }
}
