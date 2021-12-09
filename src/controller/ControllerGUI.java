package controller;

import model.PlayerImpl;
import model.Weapon;
import model.Dungeon;
import model.DungeonImpl;
import model.Monster;
import model.Player;
import model.Point2D;
import model.Treasure;
import view.ViewImpl;
import view.ViewInterface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class represent a controller for a Dungeon maze game. It implements the methods described
 * in the controller interface. These methods allow a user to communicate with the game; play
 * as it were. There are some helper methods that also aid in the playing of the game.
 */
public class ControllerGUI {

  private final Readable in;
  private final Appendable out;
  private int rows;
  private int cols;
  private int degree;
  private int numberOfMonsters;
  private int treasurePercentage;
  private boolean wrapped;
  private Map<String, Boolean> visited;
  private ViewInterface view;
  private Dungeon dungeon;
  private Dungeon savePointDungeon;


  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public ControllerGUI(Readable in, Appendable out, ViewInterface view, int rows, int cols,
                       int degree, int numberOfMonsters, int treasurePercentage, Map<String,
      Boolean> visited, boolean wrapped)  {

    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }


    this.out = out;
    this.in = in;
    this.view = view;
    this.rows = rows;
    this.cols = cols;
    this.degree = degree;
    this.numberOfMonsters = numberOfMonsters;
    this.treasurePercentage = treasurePercentage;
    this.visited = visited;
    this.wrapped = wrapped;
//    this.dungeon = dungeon;
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

  private boolean parseMove(Player inputPlayer, String inputDirection) {

    try {


      if (Objects.equals(inputDirection, "")) {
        return false;
      }


      if (Objects.equals(inputDirection, "N")) {
        try {
          inputPlayer.moveNorth();

        } catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
          view.displayUserMessage("You cannot do that! There's a wall there.");
        }

        return true;
      } else if (Objects.equals(inputDirection, "S")) {
        try {
          inputPlayer.moveSouth();
        } catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
          view.displayUserMessage("You cannot do that! There's a wall there.");
        }

        return true;
      } else if (Objects.equals(inputDirection, "E")) {
        try {
          inputPlayer.moveEast();
          return true;
        } catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
          view.displayUserMessage("You cannot do that! There's a wall there.");
        }

        return true;
      } else if (Objects.equals(inputDirection, "W")) {
        try {
          inputPlayer.moveWest();
          return true;
        } catch (IllegalStateException stateException) {
          out.append("You cannot do that! There's a wall there.");
          view.displayUserMessage("You cannot do that! There's a wall there.");
        }

        return true;
      } else {
        out.append("Invalid move: ").append(inputDirection);
        return true;
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
        view.displayUserMessage("No treasure in the cave!");
        out.append("\nNo treasure in the cave");
        return false;
      } else {

        out.append("\nThere is treasure in the room!");
        StringBuilder treasureString = new StringBuilder("There is treasure in the cave!");
        int rubies = 0;
        int diamonds = 0;
        int sapphires = 0;

        for (Treasure treasure : caveTreasure) {
          if (Objects.equals(treasure.toString(), "Ruby")) {
            rubies++;
          }

          if (Objects.equals(treasure.toString(), "Diamond")) {
            diamonds++;
          }

          if (Objects.equals(treasure.toString(), "Sapphire")) {
            sapphires++;
          }
          out.append("\nA ").append(treasure.toString());
        }
        treasureString.append(" ").append(rubies).append(" rubie(s), ").append(diamonds).append(
            " " +
                "diamond(s), ")
            .append(sapphires).append(" ").append("sapphire(s)");

        view.displayUserMessage(treasureString.toString());
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
        view.displayUserMessage("No arrows in the cave.");
        out.append("\nNo arrows in the cave");
        return false;
      } else {
        StringBuilder arrowString = new StringBuilder();
        arrowString.append("There are arrows in the room! ").append(String.valueOf(caveWeapons.size()))
            .append(" arrow(s)");
        out.append("\nThere are arrows in the room! ")
            .append(String.valueOf(caveWeapons.size()))
            .append(" arrow(s)");
        view.displayUserMessage(arrowString.toString());
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
        view.displayUserMessage("Player just picks up the treasure!");
        inputPlayer.pickUpTreasure(caveTreasure);
      }

      out.append("\nCurrent player treasure: " + inputPlayer.getPlayerTreasure());
      view.displayUserMessage("\nCurrent player treasure: " + inputPlayer.getPlayerTreasure());
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void playerPickCrookedArrowsFromCave(Player inputPlayer,
                                               List<Weapon> crookedArrowsList) {

    try {

      inputPlayer.pickUpWeapons(crookedArrowsList);
      view.displayUserMessage("Player picks up arrows!");
      out.append("\nPlayer picks up arrows!");

      if (crookedArrowsList.size() > 0) {
        view.displayUserMessage("Player currently has arrows (number): " + inputPlayer.getPlayerWeapons().size());
        out.append("\nPlayer currently has arrows (number): " + inputPlayer.getPlayerWeapons().size());
      } else {
        view.displayUserMessage("Current player arrows: 0");
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
        view.displayUserMessage("There is a super bad smell nearby!");
        out.append("\nThere is a super bad smell nearby!");
      } else if (d.isMinorSmell(inputPoint)) {
        view.displayUserMessage("There is a bad smell nearby!");
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
        view.displayUserMessage("You have no arrows to shoot!");
        out.append("You have no arrows to shoot!");
        return;
      }

      catch (IllegalArgumentException e) {
        view.displayUserMessage("That is an invalid distance! Try again.");
        out.append("That is an invalid distance! Try again.");
      }





      if (shootingResult == 0) {
        view.displayUserMessage("You just wasted an arrow!");
        out.append("You just wasted an arrow!");
      } else if (shootingResult == 1) {
        view.displayUserMessage("Nice hit! You injured the monster.");
        out.append("Nice hit! You injured the monster.");
      } else if (shootingResult == 2) {
        view.displayUserMessage("Good job! You just killed the monster.");
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

  private String getUserDirection(ViewInterface inputView) {

    return inputView.getUserDirection().replaceAll("\\s+","");

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
        view.displayUserMessage("\nCongrats! You just finished the maze! Click on the top right " +
            "to start a new session!");
      } else if (d.gameFinished(inputPlayer.getPlayerLocation()) && !inputPlayer.isAlive()) {
        out.append("\nSo close yet so far. You reached the end, but you are dead!");
        view.displayUserMessage("\nSo close yet so far. You reached the end, " +
            "but you are dead! Click on the top right \" +\n" +
            "            \"to start a new session");
      } else if (!inputPlayer.isAlive()) {
        out.append("\n*Chomp, chomp, chomp*, you are eaten by an Otyugh!\n" +
            "Better luck next time! Click on the top right \" +\n" +
            "            \"to start a new session");
        view.displayUserMessage("\nChomp, chomp, chomp*, you are eaten by an Otyugh! " +
            "Click on the top right \" +\n" +
            "            \"to start a new session");
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


  public Dungeon buildDungeon(boolean wraps, int rows, int columns, int interconnect,
                              int treasurePercentage, int numberOfMonsters) {

    // Make sure input given is null safe
    checkDungeonInvariants(wraps, rows, columns, interconnect, treasurePercentage,
        numberOfMonsters);

    welcomeMessage();

    dungeonStats(wraps, rows, columns, interconnect, treasurePercentage,
        numberOfMonsters);

    this.dungeon = new DungeonImpl(wraps, rows, columns, interconnect, treasurePercentage,
        numberOfMonsters);

    return this.dungeon;

  }


  private void saveGameState(Dungeon d) {
    try {
      FileOutputStream fileOut =
          new FileOutputStream("src/dungeon.ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(d);
      out.close();
      fileOut.close();
    }

    catch (IOException i) {
      i.printStackTrace();
    }
  }

  private Dungeon restoreGameState() {
    Dungeon e = null;
    try {
      FileInputStream fileIn = new FileInputStream("src/dungeon.ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      e = (Dungeon) in.readObject();
      in.close();
      fileIn.close();
    }

    catch (IOException i) {
      i.printStackTrace();

    }

    catch (ClassNotFoundException c) {
      c.printStackTrace();

    }
    return e;
  }

  public void playGame(Dungeon d, Player player, ViewInterface view) {

    this.saveGameState(d);

    this.visited = new HashMap<>();

    if (d == null || player == null) {
      throw new IllegalArgumentException("Dungeon/Player cannot be null!");
    }

//    view.generateHUD(d, rows, cols, new HashMap<>(), (PlayerImpl) player);
//    Scanner scan = new Scanner(in);
    visited.put(player.getPlayerLocation().toString(), true);
    view.generateHUD(d, rows,
        cols, visited, (PlayerImpl) player);


    while (player.isAlive() && !d.gameFinished(player.getPlayerLocation())) {

//      playerStats(player);

//      ViewInterface view = new ViewImpl();
//      ArrayList<String> userParams = (ArrayList<String>) view.startNewGame();
//      boolean wrapped = Boolean.parseBoolean(userParams.get(5));
//      int rows = Integer.parseInt(userParams.get(0));
//      int cols = Integer.parseInt(userParams.get(1));
//      int degree = Integer.parseInt(userParams.get(2));
//      int numberOfMonsters=  Integer.parseInt(userParams.get(3));
//      int treasurePercentage =  Integer.parseInt(userParams.get(4));
//
//      Dungeon dungeon = new DungeonImpl(wrapped, rows, cols, degree, treasurePercentage,
//          numberOfMonsters);



      boolean treasureInCave = checkTreasure(d, player.getPlayerLocation());

      boolean arrowsInCave = checkArrows(d, player.getPlayerLocation());

      checkSmell(d, player.getPlayerLocation());

      checkMonsters(d, player);

      if (!player.isAlive()) {
        break;
      }


      // TODO: Change userMotive to get shit from the view

      String userMotive = view.getUserIntention();


      while (Objects.equals(userMotive, "")) {
        try {
          userMotive = view.getUserIntention();
          // Sleep till we get our game move
          Thread.sleep(200);
        }

        catch (InterruptedException e) {

        }
      }


      if (userMotive.equals("M")) {

        boolean userMoveMade = false;

        while (!userMoveMade) {
          try {
            userMoveMade = parseMove(player, getUserDirection(view));
            view.resetUserDirection();
            // Sleep till we get our game move
            Thread.sleep(200);
          }

          catch (InterruptedException e) {

          }
        }

        view.resetUserMove();
      }


      else if (userMotive.equals("P")) {

        if (treasureInCave) {
          playerPickTreasureFromCave(player, d.expungeCaveTreasure(player.getPlayerLocation()));
        }

        if (arrowsInCave) {
          playerPickCrookedArrowsFromCave(player,
              d.expungeCaveWeapons(player.getPlayerLocation()));
        }

        view.resetUserPickUp();
      }

      else if (userMotive.equals("S")) {
        ArrayList<String> shootingParams = (ArrayList<String>) view.getUserShootingParameters();

        try {
          String userDirection = shootingParams.get(0);
          int shootingDistance = Integer.parseInt(shootingParams.get(1));

          if (verifyDirection(userDirection)) {
            shoot(d, player, shootingDistance, userDirection);
          }

          else {
            view.displayUserMessage("Illegal shoot direction!");
          }

          view.resetUserShoot();
        }

        catch (NumberFormatException e) {
          view.displayUserMessage("Your shot seems off.");
          view.resetUserShoot();
          continue;
        }



      }

      else if (userMotive.equals("Reuse Game")) {
        this.visited = new HashMap<>();
        d = this.restoreGameState();
        player = new PlayerImpl(d.getStartPoint(), d);
        visited.put(player.getPlayerLocation().toString(), true);
        view.refreshHUD(d, rows,
            cols, visited, (PlayerImpl) player);

        view.resetUserChangeGame();
        continue;
      }

      else if (userMotive.equals("New Game")) {
//        view.closeProgram();

//        view = new ViewImpl();
        ArrayList<String> userParams = (ArrayList<String>) view.startNewGame();
        boolean wrapped = Boolean.parseBoolean(userParams.get(5));
        rows = Integer.parseInt(userParams.get(0));
        cols = Integer.parseInt(userParams.get(1));
        degree = Integer.parseInt(userParams.get(2));
        numberOfMonsters =  Integer.parseInt(userParams.get(3));
        treasurePercentage =  Integer.parseInt(userParams.get(4));

        d = this.buildDungeon(wrapped, rows, cols, degree,
            treasurePercentage, numberOfMonsters);

        player = new PlayerImpl(d.getStartPoint(), d);
        visited = new HashMap<>();
        visited.put(player.getPlayerLocation().toString(), true);
        this.saveGameState(d);
        view.resetUserChangeGame();
      }

      else if (userMotive.equals("Restart Game")) {


        d = this.buildDungeon(wrapped, rows, cols, degree,
            treasurePercentage, numberOfMonsters);

        player = new PlayerImpl(d.getStartPoint(), d);
        visited = new HashMap<>();
        visited.put(player.getPlayerLocation().toString(), true);
        this.saveGameState(d);
        view.resetUserChangeGame();

      }


      visited.put(player.getPlayerLocation().toString(), true);

      view.refreshHUD(d, rows,
          cols, visited, (PlayerImpl) player);

    }

    endgame(d, player);

  }

}
