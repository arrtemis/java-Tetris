package tetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GameArea extends JPanel {
  private int gridColumns;
  private int gridRows;
  private int gridCellSize;
  private Blocks block;

  public GameArea(int columns) {
    setBounds(150, 12, 240, 456);
    setBackground(new Color(238, 238, 238));
    setBorder(BorderFactory.createLineBorder(Color.black));

    gridColumns = columns;
    gridCellSize = this.getBounds().width / gridColumns;
    gridRows = this.getBounds().height / gridCellSize;

    spawnBlock();
  }

  private boolean atBottom(){
    if(block.getBottom() == gridRows){
      return true;
    }
    return false;
  }

  public void moveBlockDown(){
    if(atBottom()){
      return;
    }else{
      block.moveDown();
    }
    repaint();
  }

  public void spawnBlock() {
    block = new Blocks(new int[][] {
        { 1, 0 },
        { 1, 0 },
        { 1, 1 } }, Color.blue);
    block.spawn(gridColumns);
  }

  public void drawBlock(Graphics g) {
    //block details
    int height = block.getHeight();
    int width = block.getWidth();
    Color color = block.getColor();
    int[][] shape = block.getShape();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (shape[row][col] == 1) {
          int xPos = (block.getX() + col) * gridCellSize;
          int yPos = (block.getY() + row) * gridCellSize;

          g.setColor(color);
          g.fillRect(xPos,yPos, gridCellSize, gridCellSize);
          g.setColor(Color.black);
          g.drawRect(xPos, yPos, gridCellSize, gridCellSize);
        }
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    drawBlock(g);
  }
}
