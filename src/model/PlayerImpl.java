package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the player. This player when created has a location and treasure list that
 * will be filled as they move from cave to cave.
 */
public class PlayerImpl implements Player {

  private Point2D playerLocation;
  private ArrayList<Treasure> playerTreasure;
  private final Dungeon dungeon;
  private final ArrayList<CrookedArrow> playerCrookedArrows;
  private boolean alive;

  /**
   * The constructor for the player interface implementation.
   * We assign a start 2-d point along with a dungeon.
   * @param inputPoint the Point2D object that the player is currently starting at
   * @param inputDungeon the dungeon a player is associated with
   */
  public PlayerImpl(Point2D inputPoint, Dungeon inputDungeon) {

    if (inputPoint == null || inputDungeon == null) {
      throw new IllegalArgumentException("Wrong arguments!");
    }

    this.playerLocation = inputPoint;
    this.dungeon = inputDungeon;
    this.playerTreasure = new ArrayList<>();
    this.playerCrookedArrows = new ArrayList<>();
    this.alive = true;

    initializeQuiver();

  }

  private void initializeQuiver() {
    // Adding three arrows to start!
    this.playerCrookedArrows.add(new CrookedArrow());
    this.playerCrookedArrows.add(new CrookedArrow());
    this.playerCrookedArrows.add(new CrookedArrow());
  }

  private boolean getFiftyFiftyChance() {
    return Math.random() > 0.5;
  }

  @Override
  public void pickUpTreasure(List<Treasure> inputTreasure) {
    if (inputTreasure == null) {
      throw new IllegalArgumentException("Illegal null treasure");
    }
    // Pick the treasure up
    playerTreasure.addAll(inputTreasure);
  }

  @Override
  public void pickUpCrookedArrows(List<CrookedArrow> inputCrookedArrows) {
    if (inputCrookedArrows == null) {
      throw new IllegalArgumentException("Illegal input to pick up arrows!");
    }
    // Pick the arrows up
    playerCrookedArrows.addAll(inputCrookedArrows);
  }

  @Override
  public boolean isAlive() {
    if (alive) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public Point2D getPlayerLocation() {
    return new Point2DImpl(this.playerLocation.getRow(), this.playerLocation.getColumn());
  }

  @Override
  public List<Treasure> getPlayerTreasure() {
    return new ArrayList<>(this.playerTreasure);
  }

  @Override
  public List<CrookedArrow> getPlayerCrookedArrows() {
    return new ArrayList<>(this.playerCrookedArrows);
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

  @Override
  public void shoot(int inputDistance, String inputDirection) {

    if (inputDistance < 0) {
      throw new IllegalArgumentException("Cannot shoot less than 0");
    }

    if (inputDirection == null) {
      throw new IllegalArgumentException("Must have a direction to shoot");
    }

    if (this.playerCrookedArrows.size() == 0) {
      throw new IllegalArgumentException("Player does not have arrows to shoot!");
    }

    // All checks passed; deduct arrows by 1
    // and invoke the shootArrow method with the passed params
    this.playerCrookedArrows.remove(0);
  }

  @Override
  public void fightMonster(Monster monster) {
    // Check if monster is injured
    if (monster.getHits() == 1) {
      // If so, we take a chance
      if (this.getFiftyFiftyChance()) {
        monster.takeHit();
      }
      // Dead player
      else {
        this.alive = false;
      }
    }

    // Dead player
    else {
      this.alive = false;
    }
  }

}
