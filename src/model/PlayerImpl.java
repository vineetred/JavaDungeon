package model;

import java.util.ArrayList;

/**
 * The implementation of the player. This player when created has a location and treasure list that
 * will be filled as they move from cave to cave.
 */
public class PlayerImpl implements Player {
  private Location playerLocation;
  private ArrayList treasureList;

  public PlayerImpl(Location playerLocation, ArrayList treasureList) {
    this.playerLocation = playerLocation;
    this.treasureList = treasureList;
  }

  /**
   * The player moves north.
   */
  @Override
  public void moveNorth() {
    //verify player can move north
    //reduce row by 1


  }

  /**
   * The player moves south.
   */
  @Override
  public void moveSouth() {
    //verify player can move south
    //increase row by 1
  }

  /**
   * The player moves east.
   */
  @Override
  public void moveEast() {
    //verify player can move east
    //reduce column by 1

  }

  /**
   * The player moves west.
   */
  @Override
  public void moveWest() {
    //verify player can move west
    //increase column by 1

  }
}
