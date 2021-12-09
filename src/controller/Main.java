package controller;

import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import model.Point2D;
import view.ViewImpl;
import view.ViewInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
  public static void main(String[] args) {

    Readable input = new BufferedReader(new InputStreamReader(System.in));
    Appendable output = System.out;

    ViewInterface view = new ViewImpl();
    ArrayList<String> userParams = (ArrayList<String>) view.startNewGame();
    boolean wrapped = Boolean.parseBoolean(userParams.get(5));
    int rows = Integer.parseInt(userParams.get(0));
    int cols = Integer.parseInt(userParams.get(1));
    int degree = Integer.parseInt(userParams.get(2));
    int numberOfMonsters =  Integer.parseInt(userParams.get(3));
    int treasurePercentage =  Integer.parseInt(userParams.get(4));

    ControllerGUI ctrl = new ControllerGUI(input, output, view, rows, cols, degree,
        numberOfMonsters, treasurePercentage, new HashMap<>(), wrapped);


    Dungeon d = ctrl.buildDungeon(wrapped, rows, cols, degree,
        treasurePercentage, numberOfMonsters);
    PlayerImpl player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player, view);

  }
}