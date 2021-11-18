package controller;

import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

  private void playerStats(Player inputPlayer) {

    try {
      out.append("\n<---VITALS--->\n");
      out.append("\nPlayer Treasure: ")
          .append(String.valueOf(inputPlayer.getPlayerTreasure()));
      out.append("\nPlayer Arrows: ")
          .append(String.valueOf(inputPlayer.getPlayerCrookedArrows().size()));
      out.append("\nPlayer Alive?: ")
          .append(String.valueOf(inputPlayer.isAlive()))
          .append("\n");
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
          out.append("\nA ").append(treasure.toString());
        }
        return true;
      }

    }

    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private boolean checkArrows(Dungeon d, Point2D inputPoint) {

    try {
      List<CrookedArrow> caveCrookedArrows = d.peekCaveCrookedArrows(inputPoint);
      if (caveCrookedArrows == null || caveCrookedArrows.size() == 0) {
        out.append("\nNo arrows in the cave");
        return false;
      }

      else {
        out.append("\nThere are arrows in the room! ")
            .append(String.valueOf(caveCrookedArrows.size()))
            .append(" arrow(s)");
//        out.append("\nPlayer just picks up: " + caveCrookedArrows.size() + " arrows");
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

  private void playerPickCrookedArrowsFromCave(Player inputPlayer,
                                               List<CrookedArrow> crookedArrowsList) {

    try {

      inputPlayer.pickUpCrookedArrows(crookedArrowsList);
      out.append("\nPlayer picks up arrows!");

      if (crookedArrowsList.size() > 0) {
        out.append("\nPlayer currently has arrows (number): " + crookedArrowsList.size());
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

      d.resetSmell();

      if (d.isMajorSmell(inputPoint)) {
        out.append("\nThere is a super bad smell nearby!");
      }

      else if (d.isMinorSmell(inputPoint)) {
        out.append("\nThere is a bad smell nearby!");
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



    try {

      try {
        inputPlayer.shoot(inputDistance, inputDirection);
      }

      catch (IllegalStateException e) {
        out.append("You have no arrows to shoot!");
        return;
      }

      int shootingResult = d.shootCrookedArrow(inputPlayer.getPlayerLocation(), inputDirection,
          inputDistance);

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

  private String getUserMotive() {

    try {
      out.append("\nMove, Pickup, Shoot, or Quit? (M/P/S/Q)? ");
    }
    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

    return scan.nextLine();

  }

  private String getUserDirection() {

    try {
      out.append("\nDirection? ");
    }
    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

    return scan.nextLine();

  }

  private int getUserShootingDistance() {
    try {
      out.append("\nNumber of caves? (HAS TO BE A NUMBER!) ");
    }
    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

    return Integer.parseInt(scan.nextLine());

  }

  private void invalidInputMessage() {
    try {
      out.append("\nInvalid option! Try again!\n");
    }

    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }


  }

  private void quitGame() {
    try {
      out.append("\nThanks for playing! See you later!\n");
    }

    catch (IOException ioe) {
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

      }

      else {
        out.append("Invalid direction!: ").append(inputDirection);
        return false;
      }

    }
    catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }


  private void endgame(Dungeon d, Player inputPlayer) {
    try {

      if (d.gameFinished(inputPlayer.getPlayerLocation()) && inputPlayer.isAlive()) {
        out.append("\nCongrats! You just finished the maze!");
      }

      else if (d.gameFinished(inputPlayer.getPlayerLocation()) && !inputPlayer.isAlive()) {
        out.append("\nSo close yet so far. You reached the end, but you are dead!");
      }

      else if (!inputPlayer.isAlive()) {
        out.append("\n*Chomp, chomp, chomp*, you are eaten by an Otyugh!\n" +
            "Better luck next time!");
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
  public void playGame(Dungeon d, Player player) {


    while (player.isAlive() && !d.gameFinished(player.getPlayerLocation())) {

      playerStats(player);

      boolean treasureInCave = checkTreasure(d, player.getPlayerLocation());

      boolean arrowsInCave = checkArrows(d, player.getPlayerLocation());

      checkSmell(d, player.getPlayerLocation());

      checkMonsters(d, player);

      getPossibleMoves(d, player);

      String userMotive = getUserMotive();

      if (userMotive.equals("M")) {
        parseMove(player, getUserDirection());
      }

      else if (userMotive.equals("P")) {
        if (treasureInCave) {
          playerPickTreasureFromCave(player, d.expungeCaveTreasure(player.getPlayerLocation()));
        }

        if (arrowsInCave) {
          playerPickCrookedArrowsFromCave(player, d.expungeCaveCrookedArrows(player.getPlayerLocation()));
        }
      }

      else if (userMotive.equals("S")) {
        String userDirection = getUserDirection();
        if (verifyDirection(userDirection)) {
          shoot(d, player, getUserShootingDistance(), userDirection);
        }

      }

      else if (userMotive.equals("Q") || userMotive.equals("q")) {
        quitGame();
        return;
      }

      else {
        invalidInputMessage();
      }

    }

    endgame(d, player);

  }


}
