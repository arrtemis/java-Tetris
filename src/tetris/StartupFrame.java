package tetris;

import java.awt.EventQueue;
import java.awt.Desktop.Action;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;

public class StartupFrame extends JFrame{
  private JButton btnStart, btnQuit, btnShowLeaderboard;

  public StartupFrame(){
    btnStart = new JButton("Start Game");
    btnQuit = new JButton("Quit Game");
    btnShowLeaderboard = new JButton("Leaderboard");

    btnStart.setBounds(156, 148, 228, 46);
    btnShowLeaderboard.setBounds(156, 217, 228, 46);
    btnQuit.setBounds(156, 286, 228, 46);

    btnStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event){
        onStartButtonClick(event);
      }
    });

    btnShowLeaderboard.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event){
        onLeaderboardButtonClick(event);
      }
    });

    btnQuit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event){
        onQuitButtonClick(event);
      }
    });

    add(btnStart);
    add(btnQuit);
    add(btnShowLeaderboard);
    initComponents();
  }

  public void initComponents() {
    setTitle("Tetris");
    getContentPane().setLayout(null);
    setSize(540, 512);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  private void onStartButtonClick(ActionEvent event){
    this.setVisible(false);
    Tetris.start();
  }

  private void onLeaderboardButtonClick(ActionEvent event){
    this.setVisible(false);
    Tetris.showLeaderboard();
  }

  private void onQuitButtonClick(ActionEvent event){
    System.exit(0);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run(){
        new StartupFrame().setVisible(true);
      }
    });
  }
}
