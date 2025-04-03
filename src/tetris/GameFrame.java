package tetris;

import javax.swing.JFrame;

import java.awt.*;

public class GameFrame extends JFrame{
  private GameArea ga;

  public GameFrame(){
    ga = new GameArea(10);
    initComponents();
    add(ga);
    startGame();
  }

  public void startGame(){
    new GameThread(ga).start();
  }

  public void initComponents(){
    setTitle("Tetris");
    getContentPane().setLayout(null);
    setSize(540,512);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String[] args){
    EventQueue.invokeLater(new Runnable() {
      public void run(){
        new GameFrame().setVisible(true);
      }
    });
  }
}
