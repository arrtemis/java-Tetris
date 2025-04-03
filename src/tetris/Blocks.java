package tetris;

import java.awt.Color;

public class Blocks {
  private int[][] shape;
  private Color color;
  private int x, y;

  public Blocks(int[][] shape, Color color){
    this.shape = shape;
    this.color = color;
  }

  public void spawn(int gridWidth){
    x = (gridWidth - getWidth()) / 2;
    y = -getHeight();
  }

  //movements
  public void moveDown(){
    y++;
  }
  public void moveLeft(){
    x--;
  }
  public void moveRight(){
    x++;
  }

  //accessors
  public int[][] getShape(){
    return shape;
  }
  public Color getColor(){
    return color;
  }
  public int getHeight(){
    return shape.length;
  }
  public int getWidth(){
    return shape[0].length;
  }
  public int getX(){
    return x;
  }
  public int getY(){
    return y;
  }
  public int getBottom(){
    return y + getHeight();
  }
}
