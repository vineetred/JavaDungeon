package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player and all the associated actions.
 */
public interface Player {

  /**
   * The player pickups up the given treasure.
   * @param inputTreasure an ArrayList of Treasure type.
   */
  public void pickUpTreasure(ArrayList<Treasure> inputTreasure);

  /**
   * The player's treasure is returned in an arraylist.
   * @return ArrayList of treasure objects.
   */
  public List<Treasure> getPlayerTreasure();

  /**
   * The current location of the player.
   * @return a Point2D object of the current player location.
   */
  public Point2D getPlayerLocation();

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
