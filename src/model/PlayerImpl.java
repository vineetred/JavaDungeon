package model;

import java.util.ArrayList;

/**
 * The implementation of the player. This player when created has a location and treasure list that
 * will be filled as they move from cave to cave.
 */
public class PlayerImpl implements Player {
//  private int playerLocation;
//  private int row;
//  private int col;
  private Point2D playerLocation;
  private ArrayList<Treasure> playerTreasure;
  private final Dungeon dungeon;

  public PlayerImpl(Point2D inputPoint, Dungeon inputDungeon) {
//    this.playerLocation = startLocationIndex;
//    this.row = row;
//    this.col = col;
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

    if (dungeon.isMoveValid(this.playerLocation, "N")) {
      this.playerLocation = new Point2D(this.playerLocation.getRow() - 1,
          this.playerLocation.getColumn());
    }
    else {
      throw new IllegalStateException("Illegal move! There's a wall there");
    }

  }

  @Override
  public void moveSouth() {
    if (dungeon.isMoveValid(this.playerLocation, "S")) {
      this.playerLocation = new Point2D(this.playerLocation.getRow() + 1,
          this.playerLocation.getColumn());
    }
    else {
      throw new IllegalStateException("Illegal move! There's a wall there");
    }
  }

  @Override
  public void moveEast() {
    if (dungeon.isMoveValid(this.playerLocation, "E")) {
      this.playerLocation = new Point2D(this.playerLocation.getRow(),
          this.playerLocation.getColumn() - 1);
    }
    else {
      throw new IllegalStateException("Illegal move! There's a wall there");
    }

  }

  @Override
  public void moveWest() {
    if (dungeon.isMoveValid(this.playerLocation, "W")) {
      this.playerLocation = new Point2D(this.playerLocation.getRow(),
          this.playerLocation.getColumn() + 1);
    }
    else {
      throw new IllegalStateException("Illegal move! There's a wall there");
    }

  }
}
