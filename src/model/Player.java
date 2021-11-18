package model;

import java.util.List;

/**
 * Represents a player and all the associated actions. This interface has methods that let the
 * driver, or any other class that needs to use them a way to interact with a player object. Keep
 * in mind that the player interface usually has a Dungeon object associated with it. This helps
 * functionality as the Dungeon object has no real state; using instead for querying.
 */
public interface Player {

  /**
   * The player pickups up the given treasure.
   * @param inputTreasure an ArrayList of Treasure type.
   */
  void pickUpTreasure(List<Treasure> inputTreasure);

  /**
   * The player pickups up the given chest of arrows.
   * @param inputWeapons an ArrayList of CrookedArray type.
   */
  void pickUpWeapons(List<Weapon> inputWeapons);

  /**
   * The player's treasure is returned in an arraylist.
   * @return ArrayList of treasure objects.
   */
  List<Treasure> getPlayerTreasure();

  /**
   * The player's arrows are returned in an arraylist.
   * @return ArrayList of Weapon objects.
   */
  List<Weapon> getPlayerWeapons();

  /**
   * The current location of the player.
   * @return a Point2D object of the current player location.
   */
  Point2D getPlayerLocation();

  /**
   * The boolean that is true if player is alive; false if dead.
   */
  boolean isAlive();

  /**
   * The player moves north.
   */
  void moveNorth();

  /**
   * The player moves south.
   */
  void moveSouth();

  /**
   * The player moves east.
   */
  void moveEast();

  /**
   * The player moves west.
   */
  void moveWest();

  /**
   * The player shoots an arrow from their quiver! Necessary to reduce the quiver count by 1.
   * @param inputDistance an integer distance.
   * @param inputDirection string direction!
   * @throws IllegalArgumentException if bad arguments are given!
   */
  void useWeapon(int inputDistance, String inputDirection);

  /**
   * The player fights the passed monster. If they die during the process, their alive
   * boolean gets changed. So, check if the player is alive at the start/end of every move.
   * @param monster the input monster interface object
   */
  void fightMonster(Monster monster);

}
