package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import tetrisblocks.*;

public class GameArea extends JPanel {
  private int gridColumns;
  private int gridRows;
  private int gridCellSize;
  private Blocks block;
  private Blocks[] blocks;
  private Color[][] background;
  private int lastShapeIndex = -1;
  private int secondLastShapeIndex = -1;
  private Color lastColor = null;
  private Color secondLastColor = null;

  public GameArea(int columns) {
    setBounds(150, 12, 240, 456);
    setBackground(new Color(238, 238, 238));
    setBorder(BorderFactory.createLineBorder(Color.black));

    gridColumns = columns;
    gridCellSize = this.getBounds().width / gridColumns;
    gridRows = this.getBounds().height / gridCellSize;
    blocks = new Blocks[] {
        new Lshape(),
        new Jshape(),
        new Ishape(),
        new Oshape(),
        new Tshape(),
        new Sshape(),
        new Zshape()
    };
  }

  public void initBackground(){
    background = new Color[gridRows][gridColumns];
  }

  // movements
  public void moveBlockLeft() { // <-- key
    if (checkLeft() || block == null)
      return;

    block.moveLeft();
    repaint();
  }

  public void moveBlockRight() { // --> key
    if (checkRight() || block == null)
      return;

    block.moveRight();
    repaint();
  }

  public void moveDownFast() { // down arrow key
    if (block == null) return;
    if(checkBottom()) return;
    block.moveDown();
    repaint();
  }

  public void instaDrop(){
    if (block == null) return;
    while(!checkBottom()){
      block.moveDown();
    }
    repaint();
  }

  public void rotateBlock() { // up arrow key
    if (block == null)
      return;

    int currentRotation = block.getCurrentRotation();

    block.rotate();
    int newX = block.getX();
    int newY = block.getY();

    if (newY > 0 && background[newY][newX] != null)
      block.setRotation(currentRotation);
    if (block.getEdgeL() < 0)
      block.setX(0);
    if (block.getEdgeR() >= gridColumns)
      block.setX(gridColumns - block.getWidth());
    if (block.getBottom() >= gridRows)
      block.setY(gridRows - block.getHeight());

    repaint();
  }

  // movement validators
  private boolean checkBottom() {
    if (block.getBottom() == gridRows) {
      return true;
    }

    int height = block.getHeight();
    int width = block.getWidth();
    int[][] shape = block.getShape();
    for (int col = 0; col < width; col++) {
      for (int row = height - 1; row >= 0; row--) {
        if (shape[row][col] != 0) {
          int x = col + block.getX();
          int y = row + block.getY() + 1;
          if (y < 0) {
            break;
          }
          if (background[y][x] != null)
            return true;
          break;
        }
      }
    }

    return false;
  }

  private boolean checkLeft() {
    if (block.getEdgeL() == 0) {
      return true;
    }

    int height = block.getHeight();
    int width = block.getWidth();
    int[][] shape = block.getShape();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (shape[row][col] != 0) {
          int x = col + block.getX() - 1;
          int y = row + block.getY();
          if (y < 0)
            break;
          if (background[y][x] != null)
            return true;
        }
      }
    }

    return false;
  }

  private boolean checkRight() {
    if (block.getEdgeR() == gridColumns) {
      return true;
    }

    int height = block.getHeight();
    int width = block.getWidth();
    int[][] shape = block.getShape();
    for (int row = 0; row < height; row++) {
      for (int col = width - 1; col >= 0; col--) {
        if (shape[row][col] != 0) {
          int x = col + block.getX() + 1;
          int y = row + block.getY();
          if (y < 0)
            break;
          if (background[y][x] != null)
            return true;
        }
      }
    }

    return false;
  }

  public boolean isOutOfBounds() {
    if (block.getY() < 0) {
      block = null;
      return true;
    }
    return false;
  }

  public boolean moveBlockDown() {
    if (checkBottom()) {
      return false;
    }
    block.moveDown();
    repaint();
    return true;
  }

  // spawner
  public void spawnBlock() {
    Random rng = new Random();

    // Get a new block shape that's not the same as the last two
    int nextBlockIndex;
    do {
      nextBlockIndex = rng.nextInt(blocks.length);
    } while (nextBlockIndex == lastShapeIndex && nextBlockIndex == secondLastShapeIndex);

    // Update shape history
    secondLastShapeIndex = lastShapeIndex;
    lastShapeIndex = nextBlockIndex;

    // Set the block
    block = blocks[nextBlockIndex];
    block.spawn(gridColumns);

    // Now ensure the color isn't repeated more than twice in a row
    Color nextColor;
    do {
      // Generate a new color
      int colorIndex = rng.nextInt(block.getColors().length);
      nextColor = block.getColors()[colorIndex];
    } while (nextColor.equals(lastColor) && nextColor.equals(secondLastColor));

    // Update color history
    secondLastColor = lastColor;
    lastColor = nextColor;

    // Set the color
    block.setColor(nextColor);

  }

  // line completion
  public int clearLines() {
    boolean isComplete;
    int clearedLines = 0;
    for (int row = gridRows - 1; row >= 0; row--) {
      isComplete = true;
      for (int col = 0; col < gridColumns; col++) {
        if (background[row][col] == null) {
          isComplete = false;
          break;
        }
      }
      if (isComplete) {
        clearLine(row);
        shiftDown(row);
        clearLine(0);
        row++;
        clearedLines++;
        repaint();
      }
    }
    return clearedLines;
  }

  private void clearLine(int row) {
    for (int i = 0; i < gridColumns; i++) {
      background[row][i] = null;
    }
  }

  private void shiftDown(int r) {
    for (int row = r; row > 0; row--) {
      for (int col = 0; col < gridColumns; col++) {
        background[row][col] = background[row - 1][col];
      }
    }
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
  public void moveBlockToBackground() {
    // block details
    int height = block.getHeight();
    int width = block.getWidth();
    Color color = block.getColor();
    int[][] shape = block.getShape();
    int xPos = block.getX();
    int yPos = block.getY();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
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
