package view;

import model.Dungeon;
import model.Player;
import model.PlayerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockView implements ViewInterface {

  private String userIntention;
  private String userDirection;
  private ArrayList<String> shootingParams;
  private Appendable log;
  private Player player;
  private Dungeon dungeon;

  public MockView(Appendable log) {
    this.log = log;
    this.userIntention = "";
    this.userDirection = "";
    this.shootingParams = new ArrayList<>();
  }

  @Override
  public void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols, Map<String, Boolean> visited, PlayerImpl inputPlayer) {

    this.player = inputPlayer;
    this.dungeon = inputDungeon;

  }


  @Override
  public void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols, Map<String, Boolean> visited, PlayerImpl inputPlayer) {

  }

  public void simulateMovePlayer() {
    this.player.moveNorth();
  }

  public void simulatePlayerShoot() {
    this.player.useWeapon(2, "N");
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

  }

  @Override
  public void resetUserMove() {

  }

  @Override
  public void resetUserShoot() {

  }

  @Override
  public void displayUserMessage(String inputString) {

  }

  @Override
  public void resetUserChangeGame() {

  }
}
