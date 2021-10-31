package model;

/**
 * Represents a player and all the associated actions.
 */
public interface Player {

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


}
