package tetris;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LeaderboardFrame extends JFrame{
  private JButton btnMainMenu;
  private JTable leaderboard;
  private DefaultTableModel tableModel;
  private JScrollPane scrollPane;

  public LeaderboardFrame(){
    btnMainMenu = new JButton("Main Menu");

    String[] columns = {"Player", "Score"};
    tableModel = new DefaultTableModel(columns, 0);
    leaderboard = new JTable(tableModel);

    leaderboard.setRowHeight(25);
    leaderboard.getTableHeader().setReorderingAllowed(false);
    leaderboard.getTableHeader().setResizingAllowed(false);
    scrollPane = new JScrollPane(leaderboard);
    scrollPane.setBounds(31, 95, 478, 373);


    btnMainMenu.setBounds(20, 30, 108, 40);
    btnMainMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event){
        onMainMenuButtonClick(event);
      }
    });
    add(btnMainMenu);
    initComponents();
  }

  public void addPlayerName(String playerName, int score){
    this.setVisible(true);
  }

  private void onMainMenuButtonClick(ActionEvent event){
    this.setVisible(false);
    Tetris.showStartup();
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
      public void run(){
        new LeaderboardFrame().setVisible(true);
      }
    });
  }
  
}
