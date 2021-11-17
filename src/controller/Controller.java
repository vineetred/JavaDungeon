package controller;

import model.Dungeon;
import model.Player;

public interface Controller {

  Dungeon buildDungeon(boolean wraps, int rows, int columns, int interconnect,
                       int treasurePercentage, int numberOfMonsters);

  void playGame(Dungeon d, Player player);

//  void outHelper(String printString);

  //give the model to the controller with playGame method

  //run the game
}