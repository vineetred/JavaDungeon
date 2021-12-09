package controller;

import model.Weapon;
import model.Dungeon;
import model.DungeonImpl;
import model.Monster;
import model.Player;
import model.Point2D;
import model.Treasure;
import view.ViewInterface;

import javax.swing.text.View;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class represent a controller for a Dungeon maze game. It implements the methods described
 * in the controller interface. These methods allow a user to communicate with the game; play
 * as it were. There are some helper methods that also aid in the playing of the game.
 */
public class ControllerCLI implements Controller {

  private final Readable in;
  private final Appendable out;


  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public ControllerCLI(Readable in, Appendable out, ViewInterface view) {

    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }

    this.out = out;
    this.in = in;
  }

  private void welcomeMessage() {

    try {
      out.append("___________________________________  \n" +
          "| _____ |   | ___ | ___ ___ | |   | |\n" +
          "| |   | |_| |__ | |_| __|____ | | | |\n" +
          "| | | |_________|__ |______ |___|_| |\n" +
          "| |_|   | _______ |______ |   | ____|\n" +
          "| ___ | |____ | |______ | |_| |____ |\n" +
          "|___|_|____ | |   ___ | |________ | |\n" +
          "|   ________| | |__ | |______ | | | |\n" +
          "| | | ________| | __|____ | | | __| |\n" +
          "|_| |__ |   | __|__ | ____| | |_| __|\n" +
          "|   ____| | |____ | |__ |   |__ |__ |\n" +
          "| |_______|_______|___|___|___|_____|\n");

      out.append("\nAuthor: Vineet Reddy");
      out.append("\nVersion: 1.0.2");
      out.append("\nYear: 2021");
      out.append("\nGitHub: vineetred");
      out.append("\nWelcome to the Labyrinth!");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void dungeonStats(boolean wraps, int rows, int columns, int interconnect,
                            int treasurePercentage, int numberOfMonsters) {

    try {
      out.append("\nDungeon Params: ");
      out.append("\nWrapping ---> " + wraps);
      out.append("\nRows ---> " + rows);
      out.append("\nColumns ---> " + columns);
      out.append("\nInterconnectedness ---> " + interconnect);
      out.append("\nTreasure ---> " + treasurePercentage + "%");
      out.append("\nArrows ---> " + treasurePercentage + "%");
      out.append("\nMonsters ---> " + numberOfMonsters);
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

  private void playerStats(Player inputPlayer) {

    try {
      out.append("\n<---VITALS--->\n");
      out.append("\nPlayer Treasure: ")
          .append(String.valueOf(inputPlayer.getPlayerTreasure()));
      out.append("\nPlayer Arrows: ")
          .append(String.valueOf(inputPlayer.getPlayerWeapons().size()));
      out.append("\nPlayer Alive?: ")
          .append(String.valueOf(inputPlayer.isAlive()))
          .append("\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

  private void parseMove(Player inputPlayer, String inputDirection) {

    try {
      if (Objects.equals(inputDirection, "N")) {
        try {
          inputPlayer.moveNorth();
        } catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
        }
      } else if (Objects.equals(inputDirection, "S")) {
        try {
          inputPlayer.moveSouth();
        } catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
        }
      } else if (Objects.equals(inputDirection, "E")) {
        try {
          inputPlayer.moveEast();
        } catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
        }
      } else if (Objects.equals(inputDirection, "W")) {
        try {
          inputPlayer.moveWest();
        } catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
        }
      } else {
        out.append("Invalid move: ").append(inputDirection);
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }


  private boolean checkTreasure(Dungeon d, Point2D inputPoint) {

    try {
      List<Treasure> caveTreasure = d.peekCaveTreasure(inputPoint);
      if (caveTreasure == null || caveTreasure.size() == 0) {
        out.append("\nNo treasure in the cave");
        return false;
      } else {
        out.append("\nThere is treasure in the room!");
        for (Treasure treasure : caveTreasure) {
          out.append("\nA ").append(treasure.toString());
        }
        return true;
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private boolean checkArrows(Dungeon d, Point2D inputPoint) {

    try {
      List<Weapon> caveWeapons = d.peekCaveWeapons(inputPoint);
      if (caveWeapons == null || caveWeapons.size() == 0) {
        out.append("\nNo arrows in the cave");
        return false;
      } else {
        out.append("\nThere are arrows in the room! ")
            .append(String.valueOf(caveWeapons.size()))
            .append(" arrow(s)");
        return true;
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void playerPickTreasureFromCave(Player inputPlayer,
                                          List<Treasure> caveTreasure) {

    try {

      if (caveTreasure != null && caveTreasure.size() != 0) {
        out.append("\nPlayer just picks up the treasure!");
        inputPlayer.pickUpTreasure(caveTreasure);
      }

      out.append("\nCurrent player treasure: " + inputPlayer.getPlayerTreasure());
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void playerPickCrookedArrowsFromCave(Player inputPlayer,
                                               List<Weapon> crookedArrowsList) {

    try {

      inputPlayer.pickUpWeapons(crookedArrowsList);
      out.append("\nPlayer picks up arrows!");

      if (crookedArrowsList.size() > 0) {
        out.append("\nPlayer currently has arrows (number): " + crookedArrowsList.size());
      } else {
        out.append("\nCurrent player arrows: 0");
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void checkSmell(Dungeon d, Point2D inputPoint) {

    try {

      d.resetSmell();

      if (d.isMajorSmell(inputPoint)) {
        out.append("\nThere is a super bad smell nearby!");
      } else if (d.isMinorSmell(inputPoint)) {
        out.append("\nThere is a bad smell nearby!");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void checkMonsters(Dungeon d, Player inputPlayer) {

    List<Monster> monsterList = d.peekCaveMonsters(inputPlayer.getPlayerLocation());

    // There is a monster
    if (monsterList.size() > 0) {
      Monster monster = monsterList.get(0);
      // We fight the monster!
      inputPlayer.fightMonster(monster);

      if (monsterList.get(0).monsterType() == 1) {

        try {
          out.append("Oops, all your treasure just got stolen by the " +
              "thief :(.");
          }
        catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

      }
    }

  }

  private void checkPlayer(Player inputPlayer) {

    if (!inputPlayer.isAlive()) {
      try {
        out.append("You just died fighting an Otyugh!");
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed", ioe);
      }
    }
  }

  private void shoot(Dungeon d, Player inputPlayer, int inputDistance, String inputDirection) {

    int shootingResult = -1;
    try {

      try {
        inputPlayer.useWeapon(inputDistance, inputDirection);
        shootingResult = d.shootWeapon(inputPlayer.getPlayerLocation(), inputDirection,
            inputDistance);
      }

      catch (IllegalStateException e) {
        out.append("You have no arrows to shoot!");
        return;
      }

      catch (IllegalArgumentException e) {
        out.append("That is an invalid distance! Try again.");
      }





      if (shootingResult == 0) {
        out.append("You just wasted an arrow!");
      } else if (shootingResult == 1) {
        out.append("Nice hit! You injured the monster.");
      } else if (shootingResult == 2) {
        out.append("Good job! You just killed the monster.");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

  private void getPossibleMoves(Dungeon d, Player inputPlayer) {
    try {

      out.append("\nPossible moves from here - ");
      for (String direction : d.getMovesAtCaveIndex(inputPlayer.getPlayerLocation())) {
        out.append("\n" + direction);
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private String getUserMotive(Scanner inputScannerObject) {

    try {
      out.append("\nMove, Pickup, Shoot, or Quit? (M/P/S/Q)? ");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

    return inputScannerObject.next();

  }

  private String getUserDirection(Scanner inputScannerObject) {

    try {
      out.append("\nDirection? ");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

    return inputScannerObject.next();

  }

  private int getUserShootingDistance(Scanner inputScannerObject) {
    try {
      out.append("\nNumber of caves? (HAS TO BE A NUMBER!) ");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

    return Integer.parseInt(inputScannerObject.next());

  }

  private void invalidInputMessage() {
    try {
      out.append("\nInvalid option! Try again!\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }


  }

  private void quitGame() {
    try {
      out.append("\nThanks for playing! See you later!\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private boolean verifyDirection(String inputDirection) {
    try {
      if (Objects.equals(inputDirection, "N")
          || Objects.equals(inputDirection, "S")
          || Objects.equals(inputDirection, "E")
          || Objects.equals(inputDirection, "W")) {

        return true;

      } else {
        out.append("Invalid direction!: ").append(inputDirection);
        return false;
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }


  private void endgame(Dungeon d, Player inputPlayer) {
    try {

      checkMonsters(d, inputPlayer);
      if (d.gameFinished(inputPlayer.getPlayerLocation()) && inputPlayer.isAlive()) {
        out.append("\nCongrats! You just finished the maze!");
      } else if (d.gameFinished(inputPlayer.getPlayerLocation()) && !inputPlayer.isAlive()) {
        out.append("\nSo close yet so far. You reached the end, but you are dead!");
      } else if (!inputPlayer.isAlive()) {
        out.append("\n*Chomp, chomp, chomp*, you are eaten by an Otyugh!\n" +
            "Better luck next time!");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void checkDungeonInvariants(boolean wraps, int rows, int columns, int interconnect,
                                      int treasure, int numberOfMonsters) {
    // A lot of exception handling to ensure that the dungeon is never
    // created with invalid params
    if (rows < 1 || columns < 1) {
      throw new IllegalArgumentException("Cannot have 0 rows or columns!");
    } else if (rows == 1 && columns < 6 || rows < 6 && columns == 1) {
      throw new IllegalArgumentException("Need to have minimum of 6 rows/columns.");
    } else if (rows == 2 && columns < 3) {
      throw new IllegalArgumentException("You don't seem to have enough places for at least six " +
          "nodes");
    }

    if (treasure < 20) {
      throw new IllegalArgumentException("You must have at least 20% treasure. " +
          "This is not an acceptable threshold.");
    }

    if (numberOfMonsters <= 0) {
      throw new IllegalArgumentException("You must have at least one Otyugh!");
    }

    if (interconnect < 0) {
      throw new IllegalArgumentException("The interconnectivity cannot be negative!");
    }

    if (interconnect > 0 && !wraps) {

      int maxEdges = 2 * rows * columns - rows - columns;
      if (interconnect > maxEdges -  (rows  * columns - 1)) {
        throw new IllegalArgumentException("The interconnectivity is too high based on the number" +
            " of rows and columns!");
      }
    } else if (interconnect > 0 && wraps) {

      int maxEdges = 2 * rows * columns;
      if (interconnect > maxEdges) {
        throw new IllegalArgumentException("The interconnectivity is too high based on the number" +
            " of rows and columns!");
      }
    }
  }

  @Override
  public Dungeon buildDungeon(boolean wraps, int rows, int columns, int interconnect,
                              int treasurePercentage, int numberOfMonsters) {

    // Make sure input given is null safe
    checkDungeonInvariants(wraps, rows, columns, interconnect, treasurePercentage,
        numberOfMonsters);

    welcomeMessage();

    dungeonStats(wraps, rows, columns, interconnect, treasurePercentage,
        numberOfMonsters);

    return new DungeonImpl(wraps, rows, columns, interconnect, treasurePercentage,
        numberOfMonsters);

  }


  @Override
  public void playGame(Dungeon d, Player player, ViewInterface view) {


    if (d == null || player == null) {
      throw new IllegalArgumentException("Dungeon/Player cannot be null!");
    }

    Scanner scan = new Scanner(in);


    while (player.isAlive() && !d.gameFinished(player.getPlayerLocation())) {

      playerStats(player);

      boolean treasureInCave = checkTreasure(d, player.getPlayerLocation());

      boolean arrowsInCave = checkArrows(d, player.getPlayerLocation());

      checkSmell(d, player.getPlayerLocation());

      checkMonsters(d, player);

      getPossibleMoves(d, player);

      String userMotive = getUserMotive(scan);

      if (userMotive.equals("M")) {
        parseMove(player, getUserDirection(scan));
      } else if (userMotive.equals("P")) {
        if (treasureInCave) {
          playerPickTreasureFromCave(player, d.expungeCaveTreasure(player.getPlayerLocation()));
        }

        if (arrowsInCave) {
          playerPickCrookedArrowsFromCave(player,
              d.expungeCaveWeapons(player.getPlayerLocation()));
        }
      } else if (userMotive.equals("S")) {
        String userDirection = getUserDirection(scan);
        if (verifyDirection(userDirection)) {
          shoot(d, player, getUserShootingDistance(scan), userDirection);
        }

      } else if (userMotive.equals("Q") || userMotive.equals("q")) {
        quitGame();
        return;
      } else {
        invalidInputMessage();
      }

    }

    endgame(d, player);
    scan.close();

  }


}
