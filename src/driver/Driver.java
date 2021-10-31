package driver;

import model.*;

import java.util.ArrayList;

public class Driver {

  public static void main(String[] args) {
    System.out.print("\nCreating a dungeon!");
    Dungeon test = new DungeonImpl(false, 5, 5, 0, 20);
    System.out.println("\nStart point: " + test.getStartPoint());
    System.out.println("\nEnd point: " + test.getEndPoint());
    System.out.println("\nAll caves: " + test.getAllCaves());

    // Create a player
    System.out.println("\nCreating a player!");
    Player testPlayer = new PlayerImpl(test.getStartPoint());
    System.out.println("Current player location: " + testPlayer.getPlayerLocation());
    System.out.println("Current player treasure: " + testPlayer.getPlayerTreasure());
    System.out.println("Current indices that the player can move to: "
        + test.getMovesAtCaveIndex(testPlayer.getPlayerLocation()));

    ArrayList<Treasure> caveTreasure = test.returnCaveTreasure(testPlayer.getPlayerLocation());
    if (caveTreasure == null) {
      System.out.println("No treasure in the cave");
    }
    else {
      testPlayer.pickUpTreasure(caveTreasure);
      System.out.println("Player just picks up: ");
      for (Treasure treasure : testPlayer.getPlayerTreasure()) {
        System.out.println(treasure.toString());
      }

    }
  }


}
