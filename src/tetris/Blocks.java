package tetris;

import java.awt.Color;

public class Blocks {
  private int[][] shape;
  private int[][][] shapes;
  private Color color;
  private int x, y;
  private int currentRotation;

  public Blocks(int[][] shape, Color color) {
    this.shape = shape;
    this.color = color;
    initShapes();
  }

  private void initShapes() {
    shapes = new int[4][][];

    for (int i = 0; i < 4; i++) {
      int r = shape[0].length;
      int c = shape.length;
      if (i == 0) {
        shapes[i] = shape;
      } else {
        shapes[i] = new int[r][c];

        for (int y = 0; y < r; y++) {
          for (int x = 0; x < c; x++) {
            shapes[i][y][x] = shape[c - 1 - x][y];
          }
        }
        shape = shapes[i];
      }
    }
  }

  public void spawn(int gridWidth) {
    currentRotation = 0;
    shape = shapes[currentRotation];
    x = (gridWidth - getWidth()) / 2;
    y = -getHeight();
  }

  // movements
  public void moveDown() {
    y++;
  }

  public void moveLeft() {
    x--;
  }

  public void moveRight() {
    x++;
  }

  public void rotate() {
    currentRotation++;
    if (currentRotation > 3) {
      currentRotation = 0;
    }
    shape = shapes[currentRotation];
  }

  // accessors
  public int[][] getShape() {
    return shape;
  }

  public Color getColor() {
    return color;
  }

  public int getHeight() {
    return shape.length;
  }

  public int getWidth() {
    return shape[0].length;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getBottom() {
    return y + getHeight();
  }

  public int getEdgeR() {
    return x + getWidth();
  }
  
  public int getEdgeL() {
    return x;
  }

  public int getCurrentRotation(){
    return currentRotation;
  }
  public void setRotation(int rotation){
    currentRotation = rotation;
    shape = shapes[currentRotation];
  }
}
