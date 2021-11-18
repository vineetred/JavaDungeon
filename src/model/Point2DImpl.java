package model;

/**
 * Represent a 2d point with the given coordinates that are the row and column of the cave. It
 * implements the interface Point2D to allow easy expansion down the line without it having to
 * require any refactoring in the dependent code classes.
 *
 */
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

  @Override
  public int getColumn() {
    return column;
  }

  @Override
  public String toString() {
    return this.row + "," + this.column;
  }
}