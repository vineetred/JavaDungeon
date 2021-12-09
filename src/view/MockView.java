package view;

import model.Dungeon;
import model.PlayerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockView implements ViewInterface {
  @Override
  public void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols, Map<String, Boolean> visited, PlayerImpl inputPlayer) {

  }

  @Override
  public void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols, Map<String, Boolean> visited, PlayerImpl inputPlayer) {

  }

  @Override
  public List<String> startNewGame() {
    return null;
  }

  @Override
  public String getUserIntention() {
    return "S";
  }

  @Override
  public void resetUserDirection() {

  }

  @Override
  public String getUserDirection() {
    return "S";
  }

  @Override
  public List<String> getUserShootingParameters() {

    ArrayList<String> temp = new ArrayList<>();
    temp.add("N");
    temp.add("2");

    return temp;
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
