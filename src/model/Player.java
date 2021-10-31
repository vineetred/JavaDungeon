package model;

import java.util.ArrayList;

/**
 * Represents a player and all the associated actions.
 */
public interface Player {

  public void pickUpTreasure(ArrayList<Treasure> inputTreasure);

  public ArrayList<Treasure> getPlayerTreasure();

  public int getPlayerLocation();

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
