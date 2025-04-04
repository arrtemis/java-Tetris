package tetris;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LeaderboardFrame extends JFrame {
  private JButton btnMainMenu;
  private JTable leaderboard;
  private DefaultTableModel tableModel;
  private JScrollPane scrollPane;
  private String leaderboardFile = "leaderboard";

  public LeaderboardFrame() {
    String[] columns = { "Player", "Score" };

    btnMainMenu = new JButton("Main Menu");
    tableModel = new DefaultTableModel(columns, 0);
    leaderboard = new JTable(tableModel);
    scrollPane = new JScrollPane(leaderboard);

    leaderboard.setRowHeight(25);
    leaderboard.getTableHeader().setReorderingAllowed(false);
    leaderboard.getTableHeader().setResizingAllowed(false);
    scrollPane.setBounds(31, 95, 478, 373);

    btnMainMenu.setBounds(20, 30, 108, 40);
    btnMainMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        onMainMenuButtonClick(event);
      }
    });

    add(btnMainMenu);
    add(scrollPane);
    initComponents();
  }

  public void addPlayerName(String playerName, int score) {
    tableModel.addRow(new Object[] { playerName, score });
    saveData();
    this.setVisible(true);
  }

  private void onMainMenuButtonClick(ActionEvent event) {
    this.setVisible(false);
    Tetris.showStartup();
  }

  private void saveData() {
    try {
      FileOutputStream fs = new FileOutputStream(leaderboardFile);
      ObjectOutputStream os = new ObjectOutputStream(fs);

      os.writeObject(tableModel.getDataVector());

      os.close();
      fs.close();
    } catch (Exception e) {
    }

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
        new LeaderboardFrame().setVisible(true);
      }
    });
  }

}
