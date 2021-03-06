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

  /** Check if the given Point2D object of the cave has some minor smell in it. If it does, we
   * get true; else false.
   * @param inputCavePoint the Point2D object that we check for
   * @return true if minor smell is present, else false.
   */
  boolean isMinorSmell(Point2D inputCavePoint);

  /** Check if the given Point2D object of the cave has some major smell in it. If it does, we
   * get true; else false.
   * @param inputCavePoint the Point2D object that we check for
   * @return true if major smell is present, else false.
   */
  boolean isMajorSmell(Point2D inputCavePoint);

  /** Resets the smell units across the dungeon to account for any dead monsters. It ignores any
   * monsters that are dead during the smell filling.
   */
  void resetSmell();

  /** Get all possible moves that are possible from the given
   * point as directions.
   * @param inputCavePoint the Point2D object that we check the moves for
   * @return List of Strings that depict the four cardinal directions (N, S, E, W)
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

  /** Returns whatever monster that is held at the cave at
   * the given point. A new List is created leaving the actual cave monsters untouched.
   *
   * @param inputCavePoint the Point2D object that we expunge the treasure from
   * @return List of monsters that exist in the cave; null if empty! Make sure to catch it.
   */
  List<Monster> peekCaveMonsters(Point2D inputCavePoint);

  /** Returns whatever crooked arrows that are held at the cave at
   * the given Point2D object. A new List is created leaving the actual cave arrows untouched.
   * Useful when we want to check if there are any arrows before we pick!
   *
   * @param inputCavePoint the Point2D object that we want to peek in
   * @return List of monsters
   */
  List<Weapon> peekCaveWeapons(Point2D inputCavePoint);

  /** Returns whatever crooked arrows that are held at the cave at
   * the given Point2D object. It also resets the cave's crooked arrow collection as it is
   * returned to whatever calls it. This can then be passed on to the player to pick up.
   *
   * @param inputCavePoint the Point2D object that we expunge the crooked arrows from
   * @return List of monsters
   */
  List<Weapon> expungeCaveWeapons(Point2D inputCavePoint);

  /** Shoot a crooked arrow in the given direction from the given point and to a given distance.
   * @param inputCavePoint the Point2D object that we check the moves for
   * @param direction the character direction as N, S, E, W
   * @param distance the distance that the arrow is to be shot!
   * @return integer that is 0 if a wall/monster not found, 1 if hit, 2 if hit and kill
   * @throws IllegalArgumentException if direction is not a thing or no cave exists there
   */
  int shootWeapon(Point2D inputCavePoint, String direction, int distance);

}