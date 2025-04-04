package tetris;

import java.awt.Color;
import java.util.Random;

public class Blocks {
  private int[][] shape;
  private int[][][] shapes;
  private Color color;
  private int x, y;
  private int currentRotation;
  private Color[] colors = {
      // Standard predefined colors
      Color.black,
      Color.blue,
      Color.cyan,
      Color.darkGray,
      Color.gray,
      Color.green,
      Color.lightGray,
      Color.magenta,
      Color.orange,
      Color.pink,
      Color.red,
      Color.white,
      Color.yellow,

      // Custom RGB colors
      new Color(128, 0, 128),     // Purple
      new Color(0, 128, 128),     // Teal
      new Color(255, 105, 180),   // Hot Pink
      new Color(165, 42, 42),     // Brown
      new Color(0, 255, 127),     // Spring Green
      new Color(75, 0, 130),      // Indigo
      new Color(255, 215, 0),     // Gold
      new Color(173, 216, 230),   // Light Blue
      new Color(240, 230, 140),   // Khaki
      new Color(139, 69, 19),     // Saddle Brown
      new Color(255, 179, 186),   // Pastel Pink
      new Color(255, 223, 186),   // Pastel Peach
      new Color(255, 255, 186),   // Pastel Yellow
      new Color(186, 255, 201),   // Pastel Mint
      new Color(186, 225, 255),   // Pastel Blue
      new Color(204, 204, 255),   // Pastel Lavender
      new Color(255, 204, 229),   // Pastel Rose
      new Color(204, 255, 229),   // Pastel Aqua
      new Color(255, 240, 245),   // Lavender Blush
      new Color(250, 250, 210),   // Light Goldenrod Yellow
      new Color(221, 160, 221),   // Plum (light)
      new Color(255, 250, 205)    // Lemon Chiffon
  };

  public Blocks(int[][] shape) {
    this.shape = shape;
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
    Random rng = new Random();

    currentRotation = rng.nextInt(shapes.length);
    shape = shapes[currentRotation];

    x = rng.nextInt(gridWidth - getWidth());
    y = -getHeight();

    color = colors[rng.nextInt(colors.length)];
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

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
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

  public int getCurrentRotation() {
    return currentRotation;
  }

  public void setRotation(int rotation) {
    currentRotation = rotation;
    shape = shapes[currentRotation];
  }
}
