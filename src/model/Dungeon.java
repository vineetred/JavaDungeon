package model;

import java.util.ArrayList;

/** This represents a dungeon which is where the player navigates from a start point to an end point.
 *
 */
public interface Dungeon {

  /** Get the current Dungeon's designated start point.
   * @return Point2D object
   */
  Point2D getStartPoint();

  /** Get the current Dungeon's designated end point.
   * @return Point2D object
   */
  Point2D getEndPoint();

  /** Get the current Dungeon's designated cave nodes as indices
   * in an ArrayList of integers.
   * @return ArrayList of integers.
   */
  ArrayList<Integer> getAllCaves();

  /** Get all possible moves that are possible from the given
   * point as directions.
   * @param inputCavePoint the Point2D object that we check the moves for
   * @return ArrayList of Strings
   */
  ArrayList<String> getMovesAtCaveIndex(Point2D inputCavePoint);

  /** Returns whatever treasure that is held at the cave at
   * the given point. This also removes it from the cave, making
   * it empty.
   * @param inputCavePoint the Point2D object that we expunge the treasure from
   * @return ArrayList of Treasure enums
   */
  ArrayList<Treasure> returnCaveTreasure(Point2D inputCavePoint);

  /** Get all possible moves that are possible from the given
   * point as directions.
   * @param inputCavePoint the Point2D object that we check the moves for
   * @param direction the character direction as N, S, E, W
   * @return true if possible, else false
   */
  boolean isMoveValid(Point2D inputCavePoint, String direction);

  /** Check if the game is finished. This state is only reached when the given
   * point is also the marked end point. The check is done using
   * the derived column and row and not on object equality.
   * @param inputCavePoint the Point2D object that we check for
   * @return true if game is done, else false.
   */
  boolean gameFinished(Point2D inputCavePoint);

}