package tetris;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
  private GameArea ga;
  private GameThread thread;
  private JLabel scoreDisplay, levelDisplay, score, level;
  private JButton btnMainMenu;

  public GameFrame() {
    scoreDisplay = new JLabel("Score");
    levelDisplay = new JLabel("Level");
    score = new JLabel("0");
    level = new JLabel("1");
    ga = new GameArea(10);
    btnMainMenu = new JButton("Main Menu");

    btnMainMenu.setFocusable(false);
    btnMainMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event){
        onMainMenuButtonClick(event);
      }
    });

    scoreDisplay.setBounds(439, 378, 55, 24);
    levelDisplay.setBounds(446, 70, 61, 26);
    score.setBounds(458, 402, 25, 26);
    level.setBounds(461, 96, 25, 26);
    btnMainMenu.setBounds(20, 30, 108, 40);

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
    add(btnMainMenu);
  }

  public void startGame() {
    ga.initBackground();
    thread = new GameThread(ga, this);
    thread.start();
  }

  public void updateScore(int score) {
    this.score.setText(String.valueOf(score));
  }

  public void updateLevel(int level) {
    this.level.setText(String.valueOf(level));
  }

  private void onMainMenuButtonClick(ActionEvent event){
    this.setVisible(false);
    thread.interrupt();
    Tetris.showStartup();
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