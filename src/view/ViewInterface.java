package view;

import model.Dungeon;
import model.PlayerImpl;

import java.util.List;
import java.util.Map;

public interface ViewInterface {

  void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                   Map<String, Boolean> visited,
                   PlayerImpl inputPlayer);

  void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols, Map<String, Boolean> visited,
                  PlayerImpl inputPlayer);

  List<String> startNewGame();

  String getUserIntention();

  void resetUserDirection();

  String getUserDirection();

  List<String> getUserShootingParameters();

  void resetUserPickUp();

  void resetUserMove();

  void resetUserShoot();

  void displayUserMessage(String inputString);

  void resetUserChangeGame();

}
