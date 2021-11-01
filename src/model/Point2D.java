package model;

/**
 * Represent a 2d point with the given coordinates that are the row and column of the cave. This
 * implementation allows for expansion to the functionality of Point2D without having it break
 * any other classes that rely upon it. As of now, it only has two functions that let objects
 * easily query their row and column.
 *
 */
public interface Point2D {
  int getRow();

  int getColumn();
}
