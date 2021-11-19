package driver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import controller.Controller;
import controller.ControllerImpl;
import model.Dungeon;
import model.Player;
import model.PlayerImpl;


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

    if (args.length != 6) {
      throw new IllegalArgumentException("Need the six arguments!");
    } else if (!validBool(args[0])) {
      throw new IllegalArgumentException("Missing wrapping boolean!");
    } else if (!validateInput(args[1])) {
      throw new IllegalArgumentException("Must be a positive integer!");
    } else if (!(validateInput(args[2]))) {
      throw new IllegalArgumentException("Must be a positive integer!");
    } else if (!(validateInput(args[3]))) {
      throw new IllegalArgumentException("Must be a positive integer!");
    } else if (!(validateInput(args[4]))) {
      throw new IllegalArgumentException("Must be a positive integer!");
    } else if (!(validateInput(args[5]))) {
      throw new IllegalArgumentException("Must be a positive integer!");
    }

    boolean wraps = Boolean.parseBoolean(args[0]);
    int rows = Integer.parseInt(args[1]);
    int columns = Integer.parseInt(args[2]);
    int degree = Integer.parseInt(args[3]);
    int treasurePercentage = Integer.parseInt(args[4]);
    int numberOfMonsters = Integer.parseInt(args[5]);

    Readable input = new BufferedReader(new InputStreamReader(System.in));
    Appendable output = System.out;

    Controller ctrl = new ControllerImpl(input, output);
    Dungeon d = ctrl.buildDungeon(wraps, rows, columns, degree,
        treasurePercentage, numberOfMonsters);
    Player player = new PlayerImpl(d.getStartPoint(), d);
    ctrl.playGame(d, player);

  }


}

