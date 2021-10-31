package driver;

import model.*;

import java.util.ArrayList;

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

    Dungeon test = new DungeonImpl(false, 5, 5, 0, 20);
    System.out.println("\nStart point: " + test.getStartPoint().getRow() + "," + test.getStartPoint().getColumn());
    System.out.println("\nEnd point: " + test.getEndPoint().getRow() + "," + test.getEndPoint().getColumn());
//    System.out.println("\nAll caves: " + test.getAllCaves());

    // Create a player
    System.out.println("\nCreating a player!");
    Player testPlayer = new PlayerImpl(test.getStartPoint(), test);
    System.out.println("\nWelcome new Player!");
    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());

    System.out.println("\nLet's pick up the treasure that might be available.");


    ArrayList<Treasure> caveTreasure = test.returnCaveTreasure(testPlayer.getPlayerLocation());
    if (caveTreasure == null) {
      System.out.println("\nNo treasure in the cave");
    }
    else {
      testPlayer.pickUpTreasure(caveTreasure);
      System.out.println("\nPlayer just picks up: ");
      for (Treasure treasure : testPlayer.getPlayerTreasure()) {
        System.out.println("A " + treasure.toString());
      }

    }

    System.out.println("\nCurrent player treasure: " + testPlayer.getPlayerTreasure());

    System.out.println("\nPossible moves from current cave: " +
        test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    System.out.println("\nTrying to move north");
    try {
      testPlayer.moveNorth();
    }

    catch (IllegalStateException stateException) {
      System.out.println("You cannot do that! There's a wall there.");
    }

    System.out.println("\nCurrent player location: " + testPlayer.getPlayerLocation().getRow() +
        "," + testPlayer.getPlayerLocation().getColumn());
  }


}
