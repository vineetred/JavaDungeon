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
   * @param inputCrookedArrows an ArrayList of CrookedArray type.
   */
  void pickUpCrookedArrows(List<CrookedArrow> inputCrookedArrows);

  /**
   * The player's treasure is returned in an arraylist.
   * @return ArrayList of treasure objects.
   */
  List<Treasure> getPlayerTreasure();

  /**
   * The player's arrows are returned in an arraylist.
   * @return ArrayList of CrookedArrow objects.
   */
  List<CrookedArrow> getPlayerCrookedArrows();

  /**
   * The current location of the player.
   * @return a Point2D object of the current player location.
   */
  Point2D getPlayerLocation();

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
   * The boolean that is true if player is alive; false if dead.
   */
  boolean isAlive();

  /**
   * The player shoots an arrow from their quiver!
   * @param inputDistance an integer distance.
   * @param inputDirection string direction!
   */
  void shoot(int inputDistance, String inputDirection);


}
