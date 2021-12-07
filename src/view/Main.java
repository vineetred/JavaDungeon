package view;

import model.Dungeon;
import model.DungeonImpl;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {

    ViewInterface view = new ViewImpl();
    ArrayList<String> userParams = (ArrayList<String>) view.startNewGame();
    boolean wrapped = Boolean.parseBoolean(userParams.get(5));
    int rows = Integer.parseInt(userParams.get(0));
    int cols = Integer.parseInt(userParams.get(1));
    int degree = Integer.parseInt(userParams.get(2));
    int numberOfMonsters=  Integer.parseInt(userParams.get(3));
    int treasurePercentage =  Integer.parseInt(userParams.get(4));

    Dungeon dungeon = new DungeonImpl(wrapped, rows, cols, degree, treasurePercentage,
        numberOfMonsters);

    view.generateHUD(dungeon, rows,
        cols, null);


  }
}