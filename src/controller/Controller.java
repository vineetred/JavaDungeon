package controller;

import model.Dungeon;
import model.Player;

/**
 * Represents a Controller for the Dungeon Maze game. It handles the input that the user makes
 * and conveys the same to the model by morphing the data to its appropriate forms. Two methods
 * are what is needed to play the game!
 */
public interface Controller {

  /** Build a dungeon with the given parameters and get a Dungeon interface object in return.
   * This is what gets past on to the Controller in the next method!
   * @param wraps boolean that is set true if you want it wrapped
   * @param rows the number of rows
   * @param columns the number of columns
   * @param interconnect the degree of interconnectivity you want the maze to be generated with
   * @param treasurePercentage the percentage denominated out of 100 of treasure filled caves
   * @param numberOfMonsters the number of Otyughs that you want in the maze.
   * @return a Dungeon object with the given parameters
   * @throws IllegalArgumentException if any of the arguments passed are invalid.
   */
  Dungeon buildDungeon(boolean wraps, int rows, int columns, int interconnect,
                       int treasurePercentage, int numberOfMonsters);

  /** The method that gives the controller the actual control over the given Dungeon, and Player
   * interface objects. It then parses the outputs such that a fully playable game is shown to
   * the user. It runs on an infinite loop only broken when the player dies, wins, or quits.
   * @param d the Dungeon object that you want the controller to assume control over.
   * @param player the player object who will join the dungeon.
   */
  void playGame(Dungeon d, Player player);

}