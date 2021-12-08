package view;

import model.Dungeon;
import model.Player;
import model.PlayerImpl;
import model.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ViewInterface {

  void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                   Map<Point2D, Boolean> visited,
                   PlayerImpl inputPlayer);

  void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols, Map<Point2D, Boolean> visited,
                  PlayerImpl inputPlayer);

  List<String> startNewGame();

  String getUserIntention();

  void resetUserDirection();

  String getUserDirection();

  List<String> getUserShootingParameters();

  void closeProgram();

  void resetUserPickUp();

  void resetUserMove();

  void resetUserShoot();

  void displayUserMessage(String inputString);

}
