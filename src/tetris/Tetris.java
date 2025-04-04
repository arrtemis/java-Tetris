package tetris;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

public class Tetris {
  private static GameFrame gf;
  private static StartupFrame sf;
  private static LeaderboardFrame lf;

  public static void start(){
    gf.setVisible(true);
    gf.startGame();
  }

  public static void showLeaderboard(){
    lf.setVisible(true);
  }

  public static void showStartup(){
    sf.setVisible(true);
  }

  public static void gameOver(int score){
    String playerName = JOptionPane.showInputDialog("Game over\nPlease Enter your name.");
    gf.setVisible(false);
    lf.addPlayerName(playerName, score);
  }

  public static void main(String[] args) {


    EventQueue.invokeLater(new Runnable() {
      public void run() {
        gf = new GameFrame();
        sf = new StartupFrame();
        lf = new LeaderboardFrame();

        sf.setVisible(true);
      }
    });
  }
}
