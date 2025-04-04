package tetris;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameFrame extends JFrame {
  private GameArea ga;
  private JLabel scoreDisplay, levelDisplay, score, level;

  public GameFrame() {
    scoreDisplay = new JLabel("Score");
    levelDisplay = new JLabel("Level");
    score = new JLabel("10");
    level = new JLabel("1");
    ga = new GameArea(10);

    scoreDisplay.setBounds(439, 378, 55, 24);
    levelDisplay.setBounds(446, 70, 61, 26);
    score.setBounds(458, 402, 25, 26);
    level.setBounds(461, 96, 25, 26);

    Font font = new Font("Roboto Mono", Font.PLAIN, 20);
    scoreDisplay.setFont(font);
    levelDisplay.setFont(font);
    score.setFont(font);
    level.setFont(font);

    initComponents();
    initControls();

    add(ga);
    add(scoreDisplay);
    add(levelDisplay);
    add(score);
    add(level);

    startGame();
  }

  public void startGame() {
    new GameThread(ga, this).start();
  }

  public void updateScore(int score) {
    this.score.setText(String.valueOf(score));
  }

  public void updateLevel(int level) {
    this.level.setText(String.valueOf(level));
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