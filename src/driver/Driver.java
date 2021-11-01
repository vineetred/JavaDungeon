package driver;

import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import model.Treasure;

import java.util.List;
import java.util.Scanner;

public class Driver {

  public static void main(String[] args) {
    System.out.println("___________________________________  \n" +
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

    System.out.println("\nAuthor: Vineet Reddy");
    System.out.println("Version: 1.0.2");
    System.out.println("Year: 2021");
    System.out.println("GitHub: vineetred");
    System.out.print("\nWelcome to the Labyrinth!");

    simulateFromStartToEnd();
    simulateCreationOfNonWrappingDungeon();
    simulateCreationOfWrappingDungeon();
    simulateVisitingEveryNodeInDungeon();
    simulateUserInput();
  }

  private static void playerPickTreasureFromCave(Player testPlayer,
                                                 List<Treasure> caveTreasure) {
    if (caveTreasure == null || caveTreasure.size() == 0) {
      System.out.println("\nNo treasure in the cave");
    }
    else {
      testPlayer.pickUpTreasure( caveTreasure);
      System.out.println("There is treasure in the room!");
      System.out.println("\nPlayer just picks up: ");
      for (Treasure treasure : caveTreasure) {
        System.out.println("A " + treasure.toString());
      }

    }
    System.out.println("\nCurrent player treasure: " + testPlayer.getPlayerTreasure());
  }

  private static void simulateFromStartToEnd() {

    System.out.println("\n<-------- Simulating a run from start point to end point -------->");
    System.out.println("\n<-------- along with printing details statistics along every step " +
        "-------->");

    Dungeon test = new DungeonImpl(false, 0, 5, 6);
    System.out.println("\nDungeon Params: ");
    System.out.println("Wrapping ---> false");
    System.out.println("Rows ---> 5");
    System.out.println("Columns ---> 6");
    System.out.println("Interconnectedness ---> 0");
    System.out.println("Treasure ---> 20%");
    System.out.println("Start point ---> " + test.getStartPoint().getRow()
        + "," + test.getStartPoint().getColumn());
    System.out.println("End point ---> " + test.getEndPoint().getRow()
        + "," + test.getEndPoint().getColumn());

    // Create a player
    System.out.println("\nCreating a player!");
    Player testPlayer = new PlayerImpl(test.getStartPoint(), test);
    System.out.println("\nWelcome new Player!");
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());

    System.out.println("\nLet's pick up the treasure that might be available.");


    List<Treasure> caveTreasure = test.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);


    // <------> Move <------>
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    System.out.println("\nTrying to move South");
    try {
      testPlayer.moveSouth();
    }

    catch (IllegalStateException stateException) {
      System.out.println("You cannot do that! There's a wall there.");
    }

    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());

    caveTreasure = test.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);

    System.out.println("\nIs the game finished?");

    if (test.gameFinished(testPlayer.getPlayerLocation())) {
      System.out.println("\nCongratulations! You have successfully navigated the entire maze.");
    }
    else {
      System.out.println("\nKeep playing.");
    }


    // <------> Move <------>

    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    System.out.println("\nTrying to move West");
    try {
      testPlayer.moveEast();
    }

    catch (IllegalStateException stateException) {
      System.out.println("You cannot do that! There's a wall there.");
    }

    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    caveTreasure = test.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);

    System.out.println("\nIs the game finished?");
    if (test.gameFinished(testPlayer.getPlayerLocation())) {
      System.out.println("\nCongratulations! You have successfully navigated the entire maze.");
    }
    else {
      System.out.println("\nKeep playing.");
    }


    // <------> Move <------>
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    System.out.println("\nTrying to move South");
    try {
      testPlayer.moveSouth();
    }
    catch (IllegalStateException stateException) {
      System.out.println("You cannot do that! There's a wall there.");
    }

    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    caveTreasure = test.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);

    System.out.println("\nIs the game finished?");
    if (test.gameFinished(testPlayer.getPlayerLocation())) {
      System.out.println("\nCongratulations! You have successfully navigated the entire maze.");
    }
    else {
      System.out.println("\nKeep playing.");
    }


    // <------> Move <------>
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    System.out.println("\nTrying to move South");
    try {
      testPlayer.moveSouth();
    }
    catch (IllegalStateException stateException) {
      System.out.println("You cannot do that! There's a wall there.");
    }

    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    caveTreasure = test.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);

    System.out.println("\nIs the game finished?");
    if (test.gameFinished(testPlayer.getPlayerLocation())) {
      System.out.println("\nCongratulations! You have successfully navigated the entire maze.");
    }
    else {
      System.out.println("\nKeep playing.");
    }

    // <------> Move <------>
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    System.out.println("\nTrying to move East");
    try {
      testPlayer.moveWest();
    }
    catch (IllegalStateException stateException) {
      System.out.println("You cannot do that! There's a wall there.");
    }

    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    caveTreasure = test.expungeCaveTreasure(testPlayer.getPlayerLocation());
    playerPickTreasureFromCave(testPlayer, caveTreasure);

    System.out.println("\nIs the game finished?");
    if (test.gameFinished(testPlayer.getPlayerLocation())) {
      System.out.println("\nCongratulations! You have successfully navigated the entire maze.");
    }
    else {
      System.out.println("\nKeep playing.");
    }

    System.out.println("\n<---- Simulation Done ----->");

  }

  private static void simulateCreationOfNonWrappingDungeon() {

      System.out.println("\n<-------- Simulating creation of a non-wrapping dungeon -------->");
      Dungeon test = new DungeonImpl(false, 0, 5, 6);
      System.out.println("\nDungeon Params: ");
      System.out.println("Wrapping ---> false");
      System.out.println("Rows ---> 5");
      System.out.println("Columns ---> 6");
      System.out.println("Interconnectedness ---> 0");
      System.out.println("Treasure ---> 20%");
      System.out.println("Start point --->" + test.getStartPoint().getRow()
          + "," + test.getStartPoint().getColumn());
      System.out.println("End point --->" + test.getEndPoint().getRow()
          + "," + test.getEndPoint().getColumn());

      // Create a player
      System.out.println("\nCreating a player!");
      Player testPlayer = new PlayerImpl(test.getStartPoint(), test);
      System.out.println("\nWelcome new Player!");

      System.out.println("\nLet's get a description of the Player's location.");
      System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
          "," + testPlayer.getPlayerLocation().getColumn());

      List<Treasure> treasureInRoom = test.peekCaveTreasure(testPlayer.getPlayerLocation());

      if (treasureInRoom != null) {
        System.out.println("Treasure in the current room (in units): " + treasureInRoom.size());
        for (int i = 0; i < treasureInRoom.size(); i++) {
          System.out.println(treasureInRoom.get(i).toString());
        }
      }

      else {
        System.out.println("No treasure in the room.");
      }


      System.out.println("\nPossible moves from current cave: " +
          test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));


      System.out.println("\nLet's try to go up from the first row of a non-wrapping dungeon! This" +
          " will not be possible.");
      try {
        testPlayer.moveNorth();
      }
      catch (IllegalStateException stateException) {
        System.out.println("You cannot do that! There's a wall there!");
      }

    System.out.println("\n<---- Simulation Done ----->");
    }

  private static void simulateCreationOfWrappingDungeon() {

    System.out.println("\n<-------- Simulating creation of a wrapping dungeon -------->");
    Dungeon test = new DungeonImpl(true, 0, 5, 6);
    System.out.println("\nDungeon Params: ");
    System.out.println("Wrapping ---> true");
    System.out.println("Rows ---> 5");
    System.out.println("Columns ---> 6");
    System.out.println("Interconnectedness ---> 0");
    System.out.println("Treasure ---> 20%");
    System.out.println("Start point ---> " + test.getStartPoint().getRow()
        + "," + test.getStartPoint().getColumn());
    System.out.println("End point ---> " + test.getEndPoint().getRow()
        + "," + test.getEndPoint().getColumn());

    // Create a player
    System.out.println("\nCreating a player!");
    Player testPlayer = new PlayerImpl(test.getStartPoint(), test);
    System.out.println("\nWelcome new Player!");

    System.out.println("\nLet's get a description of the Player's location.");
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());

    List<Treasure> treasureInRoom = test.expungeCaveTreasure(testPlayer.getPlayerLocation());

    if (treasureInRoom != null) {
      System.out.println("Treasure in the current room (in units): " + treasureInRoom.size());
      for (int i = 0; i < treasureInRoom.size(); i++) {
        System.out.println(treasureInRoom.get(i).toString());
      }
    }

    else {
      System.out.println("No treasure in the room.");
    }


    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    System.out.println("\nSince the dungeon is wrapping, we can move from the top most row to go " +
        "further North (to the last row in reality).");

    testPlayer.moveNorth();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    System.out.println("\n<---- Simulation Done ----->");

  }

  private static void simulateVisitingEveryNodeInDungeon() {
    System.out.println("\n<-------- Visiting every single node -------->");

    Dungeon test = new DungeonImpl(false, 1, 4, 3);
    System.out.println("\nDungeon Params: ");
    System.out.println("Wrapping ---> false");
    System.out.println("Rows ---> 4");
    System.out.println("Columns ---> 3");
    System.out.println("Interconnectedness ---> 0");
    System.out.println("Treasure ---> 20%");
    System.out.println("Start point ---> " + test.getStartPoint().getRow()
        + "," + test.getStartPoint().getColumn());
    System.out.println("End point ---> " + test.getEndPoint().getRow()
        + "," + test.getEndPoint().getColumn());

    // Create a player
    System.out.println("\nCreating a player!");
    Player testPlayer = new PlayerImpl(test.getStartPoint(), test);
    System.out.println("\nWelcome new Player!");
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveWest();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));
    System.out.println("\nGoing back to the previous cave");

    testPlayer.moveEast();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveEast();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));
    System.out.println("\nGoing back to the previous cave");

    testPlayer.moveWest();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveSouth();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveEast();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveSouth();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveWest();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveWest();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));
    System.out.println("This cave is actually the end point, but we will continue exploring!");

    testPlayer.moveNorth();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));
    System.out.println("\nGoing back to the previous cave");

    testPlayer.moveSouth();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveSouth();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveEast();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    testPlayer.moveEast();
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    System.out.println("\nWe just visited every single location!");

    System.out.println("\n<---- Simulation Done ----->");
  }

  private static void simulateUserInput() {

    System.out.println("\n<-------- Simulating a real random game. User must give command line " +
        "inputs " +
        "-------->");

    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    System.out.println("Wrapping (Y/N)?");
    boolean wrap =  myObj.nextLine().equals("Y");

    System.out.println("Interconnectivity (numbers only): ? ");
    int degree =  Integer.parseInt(myObj.nextLine());

    System.out.println("Rows (numbers only): ? ");
    int rows =  Integer.parseInt(myObj.nextLine());

    System.out.println("Columns (numbers only): ? ");
    int columns =  Integer.parseInt(myObj.nextLine());

    System.out.println("Treasure in percentage (out of 100): ? ");
    int treasurePercentage =  Integer.parseInt(myObj.nextLine());

    Dungeon test = new DungeonImpl(wrap, rows, columns, degree, treasurePercentage);
    System.out.println("\nDungeon Params: ");
    System.out.println("Wrapping ---> " + wrap);
    System.out.println("Rows ---> " + rows);
    System.out.println("Columns ---> " + columns);
    System.out.println("Interconnectedness ---> " + degree);
    System.out.println("Treasure ---> " + treasurePercentage + "%");
    System.out.println("Start point --->" + test.getStartPoint().getRow()
        + "," + test.getStartPoint().getColumn());
    System.out.println("End point --->" + test.getEndPoint().getRow()
        + "," + test.getEndPoint().getColumn());

    // Create a player
    System.out.println("\nCreating a player!");
    Player testPlayer = new PlayerImpl(test.getStartPoint(), test);
    System.out.println("\nWelcome new Player!");

    System.out.println("\nLet's get a description of the Player's location.");
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());

    List<Treasure> treasureInRoom = test.peekCaveTreasure(testPlayer.getPlayerLocation());

    playerPickTreasureFromCave(testPlayer, treasureInRoom);


    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));


    System.out.println("\nLet's try to go North and see if it is possible!.");
    try {
      testPlayer.moveNorth();
      System.out.println("Nice, you just moved North.");
    }
    catch (IllegalStateException stateException) {
      System.out.println("You cannot do that! There's a wall there! Try some other direction");
    }

    System.out.println("\n<---- Simulation Done ----->");
  }


  }


