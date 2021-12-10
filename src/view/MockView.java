package view;

import model.Dungeon;
import model.Player;
import model.PlayerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Represents the ViewInterface but only used for development purposes as this allows the user
 * to test the controller in isolation and induce a desired state within the view so that actions
 * of the controller can be tested.
 */
public class MockView implements ViewInterface {

  private String userIntention;
  private String userDirection;
  private ArrayList<String> shootingParams;
  private Player player;
  private boolean flagger;

  /** Represents the empty MockView ViewInterface object that takes in the log argument to store
   * game state which can be used to compare states.
   */
  public MockView(Appendable log) {
    this.userIntention = "";
    this.userDirection = "";
    this.shootingParams = new ArrayList<>();
  }

  @Override
  public void generateHUD(Dungeon inputDungeon, int inputRows,
                          int inputCols, Map<String, Boolean> visited, PlayerImpl inputPlayer) {

    this.player = inputPlayer;

  }


  @Override
  public void refreshHUD(Dungeon inputDungeon, int inputRows,
                         int inputCols, Map<String, Boolean> visited, PlayerImpl inputPlayer) {
    flagger = true;
  }

  @Override
  public List<String> startNewGame() {
    return null;
  }

  @Override
  public String getUserIntention() {
    return userIntention;
  }

  @Override
  public void resetUserDirection() {
    flagger = false;
  }

  @Override
  public String getUserDirection() {
    return userDirection;
  }

  @Override
  public List<String> getUserShootingParameters() {

    return shootingParams;
  }

  @Override
  public void resetUserPickUp() {
    flagger = true;
  }

  @Override
  public void resetUserMove() {
    flagger = false;
  }

  @Override
  public void resetUserShoot() {
    flagger = true;
  }

  @Override
  public void displayUserMessage(String inputString) {
    flagger = false;
  }

  @Override
  public void resetUserChangeGame() {
    flagger = false;
  }


  /** Simulates the player moving a tile up, that is, North. This is used to induce a desired
   * state within this mock view.
   */
  public void simulateMovePlayer() {
    this.player.moveNorth();
  }

  /** Simulates the player shooting two tiles up, that is, North. This is used to induce a desired
   * state within this mock view.
   */
  public void simulatePlayerShoot() {
    this.player.useWeapon(2, "N");
  }

}
