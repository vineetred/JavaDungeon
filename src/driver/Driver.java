package driver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import controller.Controller;
import controller.ControllerGUI;
import controller.ControllerCLI;
import model.Dungeon;
import model.Player;
import model.PlayerImpl;
import view.ViewImpl;
import view.ViewInterface;


/**
 * Driver that accepts command line inputs to create and control the model. The driver's sole
 * responsibility is simply validating and parsing these command line inputs so that the
 * controller can be properly instantiated.
 */
public class Driver {

  private static boolean validBool(String next) {
    return next.equalsIgnoreCase("false") || next.equalsIgnoreCase("true");
  }

  private static boolean validateInput(String next) {
    try {
      Integer.parseInt(next);
      return true;
    } catch (NumberFormatException nfe) {
      System.out.println("Not a valid number: " + next + "\n");
      return false;
    }
  }

  /** This is the main for the dungeon model.
   * Order of arguments - wraps, rows, cols, interconnect degree, treasure %, number of monsters
   *
   * @param args this takes in the six arguments needed
   */
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
    boolean CLI =  Boolean.parseBoolean(userParams.get(6));

    if (!CLI) {
      ControllerGUI ctrl = new ControllerGUI(input, output, view, rows, cols, degree,
          numberOfMonsters, treasurePercentage, new HashMap<>(), wrapped);


      Dungeon d = ctrl.buildDungeon(wrapped, rows, cols, degree,
          treasurePercentage, numberOfMonsters);
      PlayerImpl player = new PlayerImpl(d.getStartPoint(), d);
      ctrl.playGame(d, player, view);
    }

    else {

      Controller ctrl = new ControllerCLI(input, output, null);
      Dungeon d = ctrl.buildDungeon(wrapped, rows, cols, degree,
          treasurePercentage, numberOfMonsters);
      Player player = new PlayerImpl(d.getStartPoint(), d);
      ctrl.playGame(d, player, null);

    }


  }


}

