package controller;

import model.Dungeon;
import model.Player;

import java.io.BufferedReader;

public interface Controller {

  Dungeon buildDungeon(boolean wraps, int rows, int columns, int interconnect,
                       int treasurePercentage, int numberOfMonsters);

  void playGame(Dungeon d, Player player);

}