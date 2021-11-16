package model;

import java.util.List;

/** This represents a dungeon which is where the player navigates from a
 * start point to an end point.
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

  /** Get all possible moves that are possible from the given
   * point as directions.
   * @param inputCavePoint the Point2D object that we check the moves for
   * @return List of Strings
   */
  List<String> getMovesAtCaveIndex(Point2D inputCavePoint);

  /** Returns whatever treasure that is held at the cave at
   * the given point. This also removes it from the cave, making
   * it empty.
   *
   * @param inputCavePoint the Point2D object that we expunge the treasure from
   * @return List of Treasure enums
   */
  List<Treasure> expungeCaveTreasure(Point2D inputCavePoint);

  /** Returns whatever treasure that is held at the cave at
   * the given point. A new List is created leaving the actual cave treasure untouched.
   *
   * @param inputCavePoint the Point2D object that we expunge the treasure from
   * @return List of Treasure enums
   */
  List<Treasure> peekCaveTreasure(Point2D inputCavePoint);

  /** Return the point 2d object of the cave or tunnel that exists
   * in the given direction.
   * @param inputCavePoint the Point2D object that we check the moves for
   * @param direction the character direction as N, S, E, W
   * @return Point2D object to the direction you want
   * @throws IllegalArgumentException if direction is not a thing or no cave exists there
   */
  Point2D getCaveInDirection(Point2D inputCavePoint, String direction);

  /** Check if the game is finished. This state is only reached when the given
   * point is also the marked end point. The check is done using
   * the derived column and row and not on object equality.
   * @param inputCavePoint the Point2D object that we check for
   * @return true if game is done, else false.
   */
  boolean gameFinished(Point2D inputCavePoint);

  /** Returns whatever monster that is held at the cave at
   * the given point. A new List is created leaving the actual cave monsters untouched.
   *
   * @param inputCavePoint the Point2D object that we expunge the treasure from
   * @return List of Treasure enums
   */
  List<Monster> peekCaveMonsters(Point2D inputCavePoint);

  /** Return the point 2d object of the cave or tunnel that exists
   * in the given direction.
   * @param inputCavePoint the Point2D object that we check the moves for
   * @param direction the character direction as N, S, E, W
   * @param distance the distance that the arrow is to be shot!
   * @return integer that is 0 if a wall/monster not found, 1 if hit, 2 if hit and kill
   * @throws IllegalArgumentException if direction is not a thing or no cave exists there
   */
  int shootCrookedArrow(Point2D inputCavePoint, String direction, int distance);

  void stats();

}