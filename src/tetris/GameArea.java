package tetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GameArea extends JPanel {
  private int gridColumns;
  private int gridRows;
  private int gridCellSize;
  private int[][] block = {
    {1, 1},
    {0, 1},
    {0, 1}
  };

  public GameArea(int columns) {
    setBounds(150, 12, 240, 456);
    setBackground(new Color(238, 238, 238));
    setBorder(BorderFactory.createLineBorder(Color.black));

    gridColumns = columns;
    gridCellSize = this.getBounds().width / gridColumns;
    gridRows = this.getBounds().height / gridCellSize;
  }

  public void drawBlock(Graphics g){
    for (int row = 0; row < block.length; row++) {
      for (int col = 0; col < block[0].length; col++) {
        if(block[row][col] == 1){
          g.setColor(Color.cyan);
          g.fillRect(col * gridCellSize, row * gridCellSize, gridCellSize, gridCellSize);
          g.setColor(Color.black);
          g.drawRect(col * gridCellSize, row * gridCellSize, gridCellSize, gridCellSize);
        }
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    
    drawBlock(g);
  }
}
