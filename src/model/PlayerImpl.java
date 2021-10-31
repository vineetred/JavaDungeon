package model;

import java.util.ArrayList;

/**
 * The implementation of the player. This player when created has a location and treasure list that
 * will be filled as they move from cave to cave.
 */
public class PlayerImpl implements Player {

  private Point2D playerLocation;
  private ArrayList<Treasure> playerTreasure;
  private final Dungeon dungeon;

  /**
   * The constructor for the player interface implementation.
   * We assign a start 2-d point along with a dungeon.
   * @param inputPoint the Point2D object that the player is currently starting at
   * @param inputDungeon the dungeon a player is associated with
   */
  public PlayerImpl(Point2D inputPoint, Dungeon inputDungeon) {
    this.playerLocation = inputPoint;
    this.dungeon = inputDungeon;
    this.playerTreasure = new ArrayList<Treasure>();

  }

  @Override
  public void pickUpTreasure(ArrayList<Treasure> inputTreasure) {
    // Pick the treasure up
    playerTreasure.addAll(inputTreasure);
  }

  @Override
  public Point2D getPlayerLocation() {
    return this.playerLocation;
  }

  @Override
  public ArrayList<Treasure> getPlayerTreasure() {
    return this.playerTreasure;
  }

  @Override
  public void moveNorth() {
    this.playerLocation = dungeon.getCaveInDirection(this.playerLocation, "N");
  }

  @Override
  public void moveSouth() {
    this.playerLocation = dungeon.getCaveInDirection(this.playerLocation, "S");
  }

  @Override
  public void moveEast() {
    this.playerLocation = dungeon.getCaveInDirection(this.playerLocation, "E");
  }

  @Override
  public void moveWest() {
    this.playerLocation = dungeon.getCaveInDirection(this.playerLocation, "W");
  }
}
