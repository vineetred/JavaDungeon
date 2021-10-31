package model;


public class Point2D {
  private int row;
  private int column;

  /**
   * Represent a 2d point with the given coordinates that are the row and column of the cave.
   *
   * @param row the x-coordinate of this cave.
   * @param column the y-coordinate of this cave.
   */
  public Point2D(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  /**
   * Return the y-coordinate of this point.
   *
   * @return y-coordinate of this point
   */
  public int getColumn() {
    return column;
  }
}