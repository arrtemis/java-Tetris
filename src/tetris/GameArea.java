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
  private Color[][] background;

  public GameArea(int columns) {
    setBounds(150, 12, 240, 456);
    setBackground(new Color(238, 238, 238));
    setBorder(BorderFactory.createLineBorder(Color.black));

    gridColumns = columns;
    gridCellSize = this.getBounds().width / gridColumns;
    gridRows = this.getBounds().height / gridCellSize;
    background = new Color[gridRows][gridColumns];
  }

  // movements
  public void moveBlockLeft() { // <-- key
    if(atEdgeL()) return;

    block.moveLeft();
    repaint();
  }

  public void moveBlockRight() { // --> key
    if(atEdgeR()) return;

    block.moveRight();
    repaint();
  }

  public void dropBlockDown() { // down arrow key
    while (!atBottom()) {
      block.moveDown();
    }
    repaint();
  }

  public void rotateBlock() { // up arrow key
    int currentRotation = block.getCurrentRotation();

    block.rotate();

    if(!isRotationValid()){
      block.setRotation(currentRotation);
    }
    
    repaint();
  }

  //movement validators
  private boolean atBottom() {
    if (block.getBottom() == gridRows) {
      return true;
    }
    return false;
  }

  private boolean atEdgeL(){
    if(block.getEdgeL() == 0){
      return true;
    }
    return false;
  }

  private boolean atEdgeR(){
    if(block.getEdgeR() == gridColumns){
      return true;
    }
    return false;
  }

  private boolean isRotationValid(){
    int newWidth = block.getWidth();
    int newX = block.getX();

    if(newWidth + newX > gridColumns || newX < 0){
      return false;
    }
    return true;
  }

  public boolean moveBlockDown() {
    if (atBottom()) {
      moveBlockToBackground();
      return false;
    }
    block.moveDown();
    repaint();
    return true;
  }

  public void spawnBlock() {
    block = new Blocks(new int[][] {
        { 1, 0 },
        { 1, 0 },
        { 1, 1 } }, Color.blue);
    block.spawn(gridColumns);
  }

  // foreground
  private void drawBlock(Graphics g) {
    // block details
    int height = block.getHeight();
    int width = block.getWidth();
    Color color = block.getColor();
    int[][] shape = block.getShape();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (shape[row][col] == 1) {
          int xPos = (block.getX() + col) * gridCellSize;
          int yPos = (block.getY() + row) * gridCellSize;
          fill(g, color, xPos, yPos);
        }
      }
    }
  }

  // background
  private void drawBackground(Graphics g) {
    Color color;
    for (int row = 0; row < background.length; row++) {
      for (int col = 0; col < background[0].length; col++) {
        color = background[row][col];

        if (color != null) {
          int xPos = col * gridCellSize;
          int yPos = row * gridCellSize;
          fill(g, color, xPos, yPos);
        }
      }
    }
  }

  // move to background
  private void moveBlockToBackground() {
    // block details
    int height = block.getHeight();
    int width = block.getWidth();
    Color color = block.getColor();
    int[][] shape = block.getShape();
    int xPos = block.getX();
    int yPos = block.getY();

    for (int row = 0; row < shape.length; row++) {
      for (int col = 0; col < shape[0].length; col++) {
        if (shape[row][col] == 1) {
          background[row + yPos][col + xPos] = color;
        }
      }
    }

  }

  private void fill(Graphics g, Color color, int xPos, int yPos) {
    g.setColor(color);
    g.fillRect(xPos, yPos, gridCellSize, gridCellSize);
    g.setColor(Color.black);
    g.drawRect(xPos, yPos, gridCellSize, gridCellSize);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    drawBackground(g);
    drawBlock(g);
  }
}
