package model;

public class Point2DImpl implements Point2D {
  private final int row;
  private final int column;

  /**
   * Represent a 2d point with the given coordinates that are the row and column of the cave.
   *
   * @param row the x-coordinate of this cave.
   * @param column the y-coordinate of this cave.
   */
  public Point2DImpl(int row, int column) {

    this.row = row;
    this.column = column;
  }

  @Override
  public int getRow() {
    return row;
  }

  /**
   * Return the y-coordinate of this point.
   *
   * @return y-coordinate of this point
   */
  @Override
  public int getColumn() {
    return column;
  }

  @Override
  public String toString() { return this.row + "," + this.column; }
}