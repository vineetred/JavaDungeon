package view;

import model.Dungeon;

import java.util.ArrayList;
import java.util.List;

public interface ViewInterface {

  void generateHUD(Dungeon inputDungeon, int inputRows, int inputCols, List<Boolean> visited);

  void refreshHUD(Dungeon inputDungeon, int inputRows, int inputCols, List<Boolean> visited);

  List<String> startNewGame();

  String getUserIntention();

  String getUserDirection();

}
