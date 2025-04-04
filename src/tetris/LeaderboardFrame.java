package tetris;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.RowSorter.SortKey;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class LeaderboardFrame extends JFrame {
  private JButton btnMainMenu;
  private JTable leaderboard;
  private DefaultTableModel tableModel;
  private JScrollPane scrollPane;
  private String leaderboardFile = "leaderboard";
  private TableRowSorter<TableModel> sorter; 

  // Custom table model that defines the Score column as Integer
  class ScoreTableModel extends DefaultTableModel {
    public ScoreTableModel(Object[] columnNames, int rowCount) {
      super(columnNames, rowCount);
    }
    
    public ScoreTableModel(Vector columnNames, int rowCount) {
      super(columnNames, rowCount);
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
      // Define the Score column (index 1) as Integer
      return columnIndex == 1 ? Integer.class : String.class;
    }
  }

  public LeaderboardFrame() {
    String[] columns = { "Player", "Score" };
    tableModel = new ScoreTableModel(columns, 0);
    leaderboard = new JTable(tableModel);
    scrollPane = new JScrollPane(leaderboard);
    leaderboard.setRowHeight(25);
    leaderboard.getTableHeader().setReorderingAllowed(false);
    leaderboard.getTableHeader().setResizingAllowed(false);
    scrollPane.setBounds(31, 95, 478, 373);
    btnMainMenu = new JButton("Main Menu");

    btnMainMenu.setBounds(20, 30, 108, 40);
    btnMainMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        onMainMenuButtonClick(event);
      }
    });

    add(btnMainMenu);
    add(scrollPane);
    initComponents();
    initTableData();
    initTableSorter();
  }

  private void initTableSorter(){
    sorter = new TableRowSorter<>(tableModel);
    leaderboard.setRowSorter(sorter);
    ArrayList<SortKey> keys = new ArrayList<>();
    keys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
    sorter.setSortKeys(keys);
  }

  private void initTableData(){
    Vector ci = new Vector();
    ci.add("Player");
    ci.add("Score");
    try {
      FileInputStream fs = new FileInputStream(leaderboardFile);
      ObjectInputStream os = new ObjectInputStream(fs);
      
      Vector<Vector> data = (Vector)os.readObject();
      
      // Convert scores from String to Integer if needed
      for (Vector row : data) {
        if (row.get(1) instanceof String) {
          try {
            row.set(1, Integer.parseInt((String)row.get(1)));
          } catch (NumberFormatException e) {
            // If conversion fails, keep as is
          }
        }
      }
      
      tableModel.setDataVector(data, ci);
      fs.close();
      os.close();
      
    } catch (Exception e){
      // If file doesn't exist or error occurs, table will be empty
    }
  }

  public void addPlayerName(String playerName, int score) {
    // Make sure playerName isn't null or empty
    if (playerName == null || playerName.trim().isEmpty()) {
      playerName = "Anonymous";
    }
    
    tableModel.addRow(new Object[] { playerName, score });
    saveData();
    sorter.sort();
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
      System.err.println("Error saving leaderboard data: " + e.getMessage());
    }
  }

  public void initComponents() {
    setTitle("Tetris - Leaderboard");
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