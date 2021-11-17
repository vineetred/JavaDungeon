package controller;

import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ControllerImpl implements Controller{

  private final Appendable out;
  private final Scanner scan;


  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public ControllerImpl(Readable in, Appendable out) {

    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }

    this.out = out;
    scan = new Scanner(in);
  }

  private void welcomeMessage(){

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
          "| |_______|_______|___|___|___|_____|");

      out.append("\nAuthor: Vineet Reddy");
      out.append("\nVersion: 1.0.2");
      out.append("\nYear: 2021");
      out.append("\nGitHub: vineetred");
      out.append("\nWelcome to the Labyrinth!");
    }

    catch (IOException ioe) {
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
    }

    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

  private void parseMove(Player inputPlayer, String inputDirection) {

    try {
      if (Objects.equals(inputDirection, "N")) {
        try {
          inputPlayer.moveNorth();
        }

        catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
        }
      }

      else if (Objects.equals(inputDirection, "S")) {
        try {
          inputPlayer.moveSouth();
        }

        catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
        }
      }

      else if (Objects.equals(inputDirection, "E")) {
          try {
            inputPlayer.moveEast();
          }

          catch (IllegalStateException stateException) {
            out.append("You cannot do that! There's a wall there.");
          }
        }

        else if (Objects.equals(inputDirection, "W")) {
          try {
            inputPlayer.moveWest();
          }

          catch (IllegalStateException stateException) {
            out.append("You cannot do that! There's a wall there.");
          }
        }

        else {
          out.append("Invalid move: ").append(inputDirection);
        }

    }
    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }



  }


  private boolean checkTreasure(Dungeon d, Point2D inputPoint) {

    try {
      List<Treasure> caveTreasure = d.peekCaveTreasure(inputPoint);
      if (caveTreasure == null || caveTreasure.size() == 0) {
        out.append("\nNo treasure in the cave");
        return false;
      }

      else {
        out.append("\nThere is treasure in the room!");
        for (Treasure treasure : caveTreasure) {
          out.append("\nA " + treasure.toString());
        }
        return true;
      }

    }

    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }




  private void playerPickTreasureFromCave(Player inputPlayer,
                                                 List<Treasure> caveTreasure) {

    try {

      if (!(caveTreasure == null) && !(caveTreasure.size() == 0)) {
        out.append("\nPlayer just picks up the treasure!");
        inputPlayer.pickUpTreasure(caveTreasure);
      }

      out.append("\nCurrent player treasure: " + inputPlayer.getPlayerTreasure());
    }

    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void playerCheckAndPickArrows(Player inputPlayer,
                                        List<CrookedArrow> crookedArrowsList) {

    try {

      if (crookedArrowsList == null || crookedArrowsList.size() == 0) {
        out.append("\nNo Arrows in the cave");
      }

      else {
        inputPlayer.pickUpCrookedArrows(crookedArrowsList);
        out.append("\nThere are arrows! in the room!");
        out.append("\nPlayer just picks up: " + crookedArrowsList.size() + " arrows");

      }

      if (crookedArrowsList.size() > 0) {
        out.append("\nCurrent player arrows: " + crookedArrowsList.size());
      }
      else {
        out.append("\nCurrent player arrows: 0");
      }

    }

    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void checkSmell(Dungeon d, Point2D inputPoint) {

    try {
      if (d.isMajorSmell(inputPoint)) {
        out.append("There is a super bad smell nearby!");
      }

      else if (d.isMinorSmell(inputPoint)) {
        out.append("There is a bad smell nearby!");
      }
    }

    catch (IOException ioe) {
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
    }

  }

  private void checkPlayer(Player inputPlayer) {

    if (!inputPlayer.isAlive()) {
      try {
        out.append("You just died fighting an Otyugh!");
      }

      catch (IOException ioe) {
        throw new IllegalStateException("Append failed", ioe);
      }
    }
  }

  private void shoot(Dungeon d, Player inputPlayer, int inputDistance, String inputDirection) {

    inputPlayer.shoot(inputDistance, inputDirection);
    int shootingResult = d.shootCrookedArrow(inputPlayer.getPlayerLocation(), inputDirection,
        inputDistance);

    try {
      if (shootingResult == 0) {
        out.append("You just wasted an arrow!");
      }

      else if (shootingResult == 1) {
        out.append("Nice hit! You injured the monster.");
      }

      else if (shootingResult == 2) {
        out.append("Good job! You just killed the monster.");
      }
    }
    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

  private void getPossibleMoves(Dungeon d, Player inputPlayer) {
    try {

      out.append("\nPossible moves from here - ");
      for (String direction : d.getMovesAtCaveIndex(inputPlayer.getPlayerLocation())) {
        out.append("\n" + direction);
      }

    }

    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  @Override
  public Dungeon buildDungeon(boolean wraps, int rows, int columns, int interconnect,
                              int treasurePercentage, int numberOfMonsters) {

    welcomeMessage();

    dungeonStats(wraps, rows, columns, interconnect, treasurePercentage,
        numberOfMonsters);

    return new DungeonImpl(wraps, rows, columns, interconnect, treasurePercentage,
        numberOfMonsters);

  }


  @Override
  public void playGame(Dungeon d, Player player, Readable input) {


//    while (player.isAlive() && !d.gameFinished(player.getPlayerLocation())) {

      if (checkTreasure(d, player.getPlayerLocation())) {
        playerPickTreasureFromCave(player, d.expungeCaveTreasure(player.getPlayerLocation()));
      }

      playerCheckAndPickArrows(player, d.expungeCaveCrookedArrows(player.getPlayerLocation()));

      checkSmell(d, player.getPlayerLocation());

      checkMonsters(d, player);

      // Check if shoot!

      getPossibleMoves(d, player);

      try {

        parseMove(player, new BufferedReader());
      }

      catch (IOException ioo) {

      }

//    }


    // While (player alive and game not finished)
      // Print what's in the cave
        // Treasure + pick
        // Arrows + pick
        // Smells
        // Monsters
          // if fully healthy, game over. If not, chance

      // Show possible attacks
        // Perform attack if needed
      // Show possible moves
        // Perform player move

    // Print what happened



  }


}
